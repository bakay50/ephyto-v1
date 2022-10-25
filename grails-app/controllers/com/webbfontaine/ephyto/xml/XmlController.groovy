package com.webbfontaine.ephyto.xml

import com.webbfontaine.ephyto.TypeCastUtils
import com.webbfontaine.ephyto.gen.EphytoGen
import grails.plugin.springsecurity.annotation.Secured
import groovy.util.slurpersupport.GPathResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper
import org.springframework.web.multipart.MultipartFile
import org.xml.sax.SAXException

@Secured(['IS_AUTHENTICATED_FULLY'])
class XmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlController.class)

    def xmlFileUploadBase
    def fileValidationService
    def conversationService
    def docVerificationService
    def xmlService
    def springSecurityService
    def businessLogicService
    def paginationService

    def importXML() {
        LOGGER.debug("Trying to import xml file.")

        if (request.multipartFileUploadException) {
            LOGGER.warn("User's ${springSecurityService?.principal?.username} xml file upload finished with - ${request.multipartFileUploadException}")
            render ""
            return
        }

        if (request instanceof SecurityContextHolderAwareRequestWrapper) {
            render ""
            return
        }

        MultipartFile file = request.getFile('xmlFile')

        if (file == null) {
            LOGGER.debug("User ${springSecurityService?.principal?.username} tried upload xml document file: Invalid Content in Request")
            render wf.alert([class: "alert-error errorContainer xmlError"], message(code: 'invalid.request'))
            return
        }


        try {
            GPathResult importedXml = new XmlSlurper().parseText(TypeCastUtils.toUTF8String(file.bytes))
            String docType = params?.domainName

            def errorMessage = fileValidationService.xmlDeepValidate(importedXml, docType)
            if (errorMessage != null) {
                render wf.alert([class: "alert-error errorContainer xmlError"], message(code: errorMessage))
                return
            } else {
                def domainInstance = xmlService.buildObjectInstance(importedXml, docType)
                businessLogicService.initDocumentForCreate(domainInstance)
                conversationService.addToConversation(domainInstance)
                docVerificationService.deepVerify(domainInstance)
                domainInstance.validate()
                def model = getModel(domainInstance, docType)
                //render(g.render(template: ("/${docType}/create/createFormContent"), model: model))
                render(g.render(template: ("/${docType}/edit"), model: model))
            }
        } catch (RuntimeException | SAXException | IOException ex) {
            LOGGER.warn("Invalid data in xml content", ex)
            render wf.alert([class: "alert-error errorContainer xmlError"], message(code: 'default.xml.wrongFile.message'))
            return
        }
    }

    def exportXML() {
        String domainName = params?.domainName
        Integer id = TypeCastUtils.toInteger(params?.id)
        def domainInstance = getDomain(domainName, id)

        if (domainInstance) {
            def outputStream = null
            try {
                def xml = getXml(domainInstance, domainName)
                def fileName = domainName.toUpperCase()
                outputStream = response.outputStream
                response.contentType = 'application/xml'
                response.setHeader 'Content-disposition', "attachment; filename=\"${fileName}-${id}.xml\""
                response.outputStream << xml
            } catch (Exception ex) {
                LOGGER.warn('', ex)
            } finally {
                try {
                    outputStream.flush()
                } catch (Exception ex) {
                }
                try {
                    outputStream.close()
                } catch (Exception ex) {
                }
            }
            response.outputStream.flush()
        } else {
            flash.message = message(code: 'default.xml.export.problem.message', args: [params.id])
        }
    }

    def getDomain(domainName, id) {
        def domainInstance
        if (domainName) {
            domainInstance = conversationService.mergeEphytoGenConversationInstance()
            if (!domainInstance) {
                domainInstance = EphytoGen.get(id)
            }
        } else {
            return null
        }
        domainInstance
    }

    def getXml(domainInstance, domainName) {
        xmlService.objectToXml(domainInstance, domainName)
    }

    def getModel(domainInstance, docType) {
        if (docType == "ephytoGen") {
            def items = domainInstance?.itemGoods
            def attachments = domainInstance?.attachments
            def itemTreatments = domainInstance?.itemTreatments
            def model = [ephytoGenInstance: domainInstance, items: items,
                         attachments      : attachments, itemTreatments: itemTreatments, hasErrors: domainInstance?.hasErrors()]
            return model
        } else {
            return null
        }

    }
}
