package com.webbfontaine.ephyto.gen

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static com.webbfontaine.ephyto.TypeCastUtils.*

//@Secured(['IS_AUTHENTICATED_FULLY'])
class EphytoTreatmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EphytoTreatmentController.class);

    def conversationService
    def paginationService
    def treatmentService

    def addTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            def itemNumber = (ephytoGenInstance?.itemTreatments?.size() ? ephytoGenInstance?.itemTreatments?.size() : 0) + 1
            def treatment = new EphytoTreatment()
            treatment.itemNumber = itemNumber
            renderInnerForm(ephytoGenInstance, treatment, "addTreatment")
        } else {
            renderTimeoutResponse()
        }
    }

    def closeTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            renderSuccessTemplate(ephytoGenInstance, paginationService, params)
        } else {
            renderTimeoutResponse()
        }
    }


    def showTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        ephytoGenInstance.isTreatmentEditable = false
        def ephytoTreatment = ephytoGenInstance.getTreatement(toInteger(params.itemNumber))
        renderInnerForm(ephytoGenInstance, ephytoTreatment, actionName)
    }

    def deleteTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        ephytoGenInstance?.isItemsEditable = false
        def ephytoTreatment = ephytoGenInstance.getTreatement(toInteger(params.itemNumber))
        renderInnerForm(ephytoGenInstance, ephytoTreatment, actionName)
    }

    def editTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            ephytoGenInstance?.isItemsEditable = true
            def ephytoTreatment = ephytoGenInstance.getTreatement(toInteger(params.itemNumber))
            if (ephytoTreatment) {
                renderInnerForm(ephytoGenInstance, ephytoTreatment, "editTreatment")
            } else {
                renderTimeoutResponse()
            }
        } else {
            renderTimeoutResponse()
        }
    }

    def saveTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            ephytoGenInstance?.clearErrors()
            synchronized (ephytoGenInstance) {
                def result = treatmentService.addTreatment(params, ephytoGenInstance)
                if (result.added) {
                    renderSuccessTemplate(ephytoGenInstance, paginationService, params)
                } else {
                    renderErrorTemplate(ephytoGenInstance, result.ephytoTreatment)
                }
            }
        } else {
            LOGGER.debug("Document not found")
            renderTimeoutResponse()
        }
    }

    def updateTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            ephytoGenInstance?.clearErrors()
            synchronized (ephytoGenInstance) {
                def result = treatmentService.updateTreatment(params, ephytoGenInstance)
                if (result.updated) {
                    renderSuccessTemplate(ephytoGenInstance, paginationService, params)
                } else {
                    renderErrorTemplate(ephytoGenInstance, result.tempEphytoTreatment)
                }
            }
        } else {
            renderTimeoutResponse()
        }
    }

    def removeTreatment() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            synchronized (ephytoGenInstance) {
                def result = treatmentService.removeTreatment(ephytoGenInstance)
                if (result.removed) {
                    renderSuccessTemplate(ephytoGenInstance, paginationService, params)
                } else {
                    renderErrorTemplate(ephytoGenInstance, result.ephytoTreatment)
                }
            }
        } else {
            renderTimeoutResponse()
        }
    }

    def renderSuccessTemplate(EphytoGen ephytoGenInstance, paginationService, params) {
        params.setProperty("itemNumber", params.itemNumber as String)
        def range = paginationService.getSubListRangeAfterItemUpdate(ephytoGenInstance?.itemTreatments?.size()?ephytoGenInstance?.itemTreatments?.size():0, params)
        def responseModel = [ephytoGenInstance: ephytoGenInstance, itemRange: range, items: ephytoGenInstance?.itemTreatments]
        render(template: '/ephytoGen/tabs/treatments', model: responseModel)
    }

    def renderErrorTemplate(EphytoGen ephytoGenInstance, EphytoTreatment ephytoTreatment) {
        def responseModel = [ephytoGenInstance: ephytoGenInstance, treatment: ephytoTreatment, hasErrors: true]
        def responsePayload = [
                error  : 'error',
                payload: g.render(template: '/ephytoGen/treatment/treatmentErrorCtr', model: responseModel),
        ]
        render responsePayload as JSON
    }

    private void renderTimeoutResponse() {
        render(view: grailsApplication.config.appListUrl)
    }

    def renderInnerForm(EphytoGen ephytoGenInstance, EphytoTreatment ephytoTreatment, actionName) {
        def responseModel = [ephytoGenInstance: ephytoGenInstance, treatmentInstance: ephytoTreatment]
        responseModel[actionName] = true
        render(template: '/ephytoGen/treatment/treatmentForm', model: responseModel)
    }

}
