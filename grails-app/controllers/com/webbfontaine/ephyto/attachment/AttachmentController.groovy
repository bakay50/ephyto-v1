package com.webbfontaine.ephyto.attachment

import com.webbfontaine.ephyto.AttachedDocUtils
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.attachment.AttachedFile
import com.webbfontaine.ephyto.gen.attachment.Attachment
import com.webbfontaine.grails.plugins.rimm.att.AttachedDocument
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonOutput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.i18n.LocaleContextHolder

import javax.servlet.http.HttpServletResponse
import java.text.DateFormat
import java.text.SimpleDateFormat

//@Secured(['IS_AUTHENTICATED_FULLY'])
class AttachmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class)


    def conversationService
    def attachmentFileUploadBase
    def springSecurityService
    def docVerificationService
    def messageSource
    static attDocFile = []

    def addAttachedDoc() {
        def ephytoGen = conversationService.getEphytoGenFromConversation()
        if (!ephytoGen) {
            renderTimeoutResponse()
            return
        }

        synchronized (ephytoGen) {
            def att = initAtt(ephytoGen)
            att.attNumber = ephytoGen?.attachments ? ephytoGen?.attachments?.size() + 1 : 1
            if (session.uploadedFile) {
                att.attDoc = new AttachedFile(data: session.uploadedFile, attachment: att)
                att.hasAttachedFile = true
            }
            params.checkExtension = true

            if (validate(att)) {
                ephytoGen.addToAttachments(att)
                session.uploadedFile = null
                renderAttachmentsTab(ephytoGen)
            } else {
                render(template: '/attachments/utils/attachedDocsErrors', model: [attachedDoc: att])
            }
        }
    }

    def uploadFile() {
        if (request.multipartFileUploadException) {
            LOGGER.warn("User's ${springSecurityService?.principal?.username} file upload finished with - ${request.multipartFileUploadException}")
            render ""
            return
        }

        byte[] attDocBytes

        attDocBytes = attachmentFileUploadBase.getFile(request, 'attDoc')

        if (attDocBytes == null) {
            LOGGER.debug("User ${springSecurityService?.principal?.username} tried upload attached document file: Invalid Content in Request")
            render wf.alert([class: "alert-error errorContainer"], message(code: 'invalid.request'))
            return
        }

        if (attachmentFileUploadBase.getValidationResult().hasErrors()) {
            render wf.alert([class: "alert-error errorContainer"], attachmentFileUploadBase.getValidationResult().getErrors()[0].message)
            return
        }

        session.uploadedFile = attDocBytes

        LOGGER.debug("User ${springSecurityService?.principal?.username} uploaded attached file, size = ${(attDocBytes?.size() ? attDocBytes?.size() / 1024 : 0).intValue()} kB.")

        render "OK"
    }

    def uploadAttDoc(EphytoGen ephytoGen) {
        def f = request.getFile('attDocFile')
        if (f.empty) {
            attDocFile[0] = null
            session.uploadedFile = null
            attDocFile[1] = 0
        } else {
            attDocFile[0] = f.getBytes()
            session.uploadedFile = f.getBytes()
            attDocFile[1] = f.size
        }
        def result
        if(!f.empty && attDocFile[1] > 2 * 1024 * 1024){
            attDocFile[0] = null
            session.uploadedFile = null
            attDocFile[1] = 0
            result = [
                    resultat:"Error",
                    MessageError:messageSource.getMessage("error.scanned.maxsize",null, null, LocaleContextHolder.getLocale())
            ]
        }else{
            result = [
                    resultat:"OK"
            ]
        }
        render JsonOutput.prettyPrint(JsonOutput.toJson([result]))
    }

    def downloadFile() {
        Integer attNumber = getItemNumber(params)

        if (!attNumber) {
            return
        }

        def attToDownload = getAtt(attNumber)

        if (attToDownload) {
            if (attToDownload?.attDoc?.data) {
                LOGGER.info("User ${springSecurityService?.principal?.username} downloaded attached file, size = ${(attToDownload?.attDoc?.data?.size() ? attToDownload?.attDoc?.data?.size() / 1024 : 0).intValue()} kB.")

                String fileName = "${attToDownload.docType}-${currentTime()}"

                def outputStream = null
                try {
                    outputStream = response.outputStream
                    response.setHeader("Content-Type", AttachedDocUtils.setFileContentType(params.fileExtension))
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "." + params.fileExtension + "\"")
                    outputStream << attToDownload?.attDoc?.data
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
            }
        }
    }

    def removeAttachment() {
        def domainInstance = conversationService.getEphytoGenFromConversation()
        if (!domainInstance) {
            renderTimeoutResponse()
            return
        }

        Integer attNumber = getItemNumber(params)

        if (!attNumber) {
            renderAjaxError(HttpServletResponse.SC_BAD_REQUEST, 'Bad Request.')
            return
        }

        synchronized (domainInstance) {
            def attToRemove = getAtt(attNumber)
            if (attToRemove) {
                domainInstance.removeFromAttachments(attToRemove)
                shiftNumbers(domainInstance, attNumber)
            } else {
                LOGGER.warn("User ${springSecurityService?.principal?.username} request removeAtt #${attNumber} but not found. Attachments ${domainInstance.attachments*.attNumber}")
            }

            renderAttachmentsTab(domainInstance)
        }
    }

    def updateAtt() {
        def domainInstance = conversationService.getEphytoGenFromConversation()
        if (!domainInstance) {
            renderTimeoutResponse()
            return
        }

        Integer attNumber = getItemNumber(params)

        if (!attNumber) {
            renderAjaxError(HttpServletResponse.SC_BAD_REQUEST, 'Bad Request.')
            return
        }

        synchronized (domainInstance) {
            def attToUpdate = getAtt(attNumber)

            def fileExtension = attToUpdate.fileExtension

            if (attToUpdate) {
                def tmpAtt = initAtt(domainInstance)
                if (!tmpAtt.fileExtension) {
                    tmpAtt.fileExtension = fileExtension
                }

                if (session.uploadedFile) {
                    AttachedFile attFile = new AttachedFile(
                            data: session.uploadedFile
                    )
                    tmpAtt.attDoc = attFile
                    session.uploadedFile = null
                } else {
                    tmpAtt.attDoc = attToUpdate.attDoc
                }

                params.checkExtension = true

                if (validate(tmpAtt)) {
                    (domainInstance.getAttachments() as List)[attNumber - 1].properties = tmpAtt.properties
                } else {
                    render(template: '/attachments/utils/attachedDocsErrors', model: [attachedDoc: tmpAtt])
                    return
                }
            } else {
                LOGGER.warn("User ${springSecurityService?.principal?.username} request updateAtt #${attNumber} but not found. Attachments ${domainInstance.attachments*.attNumber}")
            }

            renderAttachmentsTab(domainInstance)
        }
    }

    def cancelEdit() {
        def domainInstance = conversationService.getEphytoGenFromConversation()
        if (!domainInstance) {
            renderTimeoutResponse()
            return
        }
        session.uploadedFile = null
        renderAttachmentsTab(domainInstance)
    }

    private String currentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss")
        Calendar cal = Calendar.getInstance()
        return dateFormat.format(cal.getTime())
    }

    private getAtt(Integer attNumber) {
        def domainInstance = conversationService.getEphytoGenFromConversation()
        for (def att : domainInstance?.attachments) {
            if (att.attNumber == attNumber) {
                return att
            }
        }

        return null
    }


    private static void shiftNumbers(domainInstance, int attNumberToRemove) {
        for (def att : domainInstance.attachments) {
            if (att.attNumber > attNumberToRemove) {
                --att.attNumber
            }
        }
    }


    private static Integer getItemNumber(params) {
        try {
            return Integer.parseInt(params.attNumber as String)
        } catch (Exception ignore) {
            return null
        }
    }

    def initAtt(ephytoGen) {
       // def attInstance = applicationContext.getBean(grailsApplication.config.attachmentDomainClassName as String).getMetaClass().invokeConstructor(params)
        Attachment attInstance = new Attachment(params)
        attInstance.docCode = params?.docCode
        def att
        AttachedDocument.withTransaction {
            att =  AttachedDocument.findByDescription(params.docType)
        }
        if (att) {
            attInstance.docCode = att?.code
        } else {
            attInstance.errors.rejectValue("docType", "attachedDocs.invalid.type", [params.attNumber] as Object[],
                    "Attachment ${params.attNumber} : Invalid Document Type '${params.attNumber} ${params.docType}'")
        }
        attInstance.parent = ephytoGen
        return attInstance
    }

    private renderAttachmentsTab(ephytoGen) {
        ephytoGen?.addAttachedDocs = false
        ephytoGen?.editAttachedDocs = true
        def response = [
                responseData: g.render(template: '/attachedDoc/attachedDocList', model: [
                        attachments    : ephytoGen?.attachments,
                        instance       : ephytoGen,
                        isDocEditable  : ephytoGen?.isDocumentEditable,
                        addAttachedDocs: ephytoGen?.addAttachedDocs,
                        editAttachedDocs: ephytoGen?.editAttachedDocs
                ]),
                actionAtt: g.render(template: "/attachedDoc/attachedDocAction", model: [
                        attachments    : ephytoGen?.attachments,
                        instance       : ephytoGen,
                        isDocEditable  : ephytoGen?.isDocumentEditable,
                        addAttachedDocs: ephytoGen?.addAttachedDocs,
                        editAttachedDocs: ephytoGen?.editAttachedDocs]),
                numberAttDoc : ephytoGen?.attachments?.size() == null ? 0 : ephytoGen?.attachments?.size()
        ]
        render response as JSON

    }

    private void renderAjaxError(code, message) {
        response?.status = code

        render(contentType: "text/json") {
            [message: message]
        }
    }

    private void renderTimeoutResponse() {
        render(view: grailsApplication.config.appListUrl)
    }

    private boolean validate(domainInstance) {
        !docVerificationService.documentHasErrors(domainInstance) && docVerificationService.deepVerify(domainInstance)
    }

    def openAttDoc() {
        def ephytoGen = conversationService.getEphytoGenFromConversation()
        if(ephytoGen){
            synchronized (ephytoGen) {
                Attachment att = null
                ephytoGen?.addAttachedDocs = true
                ephytoGen?.editAttachedDocs = false
                renderAttachments(ephytoGen,att)
            }
        }else{
            renderTimeoutResponse()
        }

    }

    private renderAttachments(EphytoGen ephytoGen,Attachment att) {
        def response = null
        response = [
                isDocEditable  : ephytoGen.isDocumentEditable,
                addAttachedDocs: ephytoGen.addAttachedDocs,
                editAttachedDocs: ephytoGen.editAttachedDocs,
                headermodal: g.render(template: "/attachedDoc/attachedDocHeader", model: [ephytoGenInstance:ephytoGen]),
                bodymodal: g.render(template: "/attachedDoc/attachedDocForm", model: [ephytoGenInstance: ephytoGen, attdocInstance: att]),
                footermodal: g.render(template: "/attachedDoc/attachedDocFooter", model: [ephytoGenInstance:ephytoGen])
        ]
        render response as JSON
    }

    def openAttDocForEdit() {
        EphytoGen domainInstance = conversationService.getEphytoGenFromConversation()
        if (!domainInstance) {
            renderTimeoutResponse()
            return
        }

        Integer attNumber = getItemNumber(params)


        if (!attNumber) {
            return
        }

        domainInstance?.addAttachedDocs = false
        domainInstance?.editAttachedDocs = true
        if (!domainInstance) {
            renderTimeoutResponse()
            return
        }

        synchronized (domainInstance) {
            def attToDownload = getAtt(attNumber)
            if (attToDownload) {
                renderAttachments(domainInstance,attToDownload)
            }else{
                renderTimeoutResponse()
            }
        }

    }
}
