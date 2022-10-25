package com.webbfontaine.ephyto.gen

import grails.converters.JSON
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import static com.webbfontaine.ephyto.TypeCastUtils.toInteger

//@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class ItemGoodController {

    def conversationService
    def itemGoodService
    def paginationService


    private static final Logger LOGGER = LoggerFactory.getLogger(ItemGoodController.class)

    def index() {}

    def uploadItemGood() {
        render "OK"
    }


    def openItemGood() {
        def ephytoGen = conversationService.getEphytoGenFromConversation()
        ItemGood itemGood = new ItemGood();
        def itemNumber = (ephytoGen?.itemGoods?.size() ? ephytoGen?.itemGoods?.size() : 0) + 1
        itemGood?.itemRank = itemNumber
        ephytoGen?.addItemGoods = true
        ephytoGen?.isDocumentEditable = true
        renderInnerForModal(ephytoGen, itemGood, actionName)
    }

    def saveItem() {
        LOGGER.debug("In SaveItem with conversationId = ${params?.conversationId}")
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            synchronized (ephytoGenInstance) {
                LOGGER.debug("Document is found")
                def result = itemGoodService.addItem(params, ephytoGenInstance)
                if (result?.added) {
                    renderSuccessTemplate(ephytoGenInstance, paginationService, params)
                } else {
                    renderErrorTemplate(ephytoGenInstance, result?.itemGood)
                }
            }
        } else {
            LOGGER.debug("Document not found!")
            renderTimeoutResponse()
        }
    }


    def showItem() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        ephytoGenInstance.isItemsEditable = false
        ephytoGenInstance?.addItemGoods = false
        ephytoGenInstance?.editItemGoods = false
        ephytoGenInstance?.isDocumentEditable = false

        def itemGood = ephytoGenInstance.getItem(toInteger(params.itemRank))
        renderInnerForModal(ephytoGenInstance, itemGood, actionName)
    }

    def showEditItem() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        ephytoGenInstance.isItemsEditable = true
        ephytoGenInstance?.addItemGoods = false
        ephytoGenInstance?.editItemGoods = true
        ephytoGenInstance?.isDocumentEditable = true

        def itemGood = ephytoGenInstance.getItem(toInteger(params.itemRank))
        renderInnerForModal(ephytoGenInstance, itemGood, actionName)
    }

    def deleteItem() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            synchronized (ephytoGenInstance) {
                def result = itemGoodService.removeItem(ephytoGenInstance)
                if (result.removed) {
                    renderSuccessTemplate(ephytoGenInstance, paginationService, params)
                } else {
                    renderErrorTemplate(ephytoGenInstance, result.itemGood)
                }
            }
        } else {
            renderTimeoutResponse()
        }
    }

    def renderInnerForm(EphytoGen ephytoGenInstance, ItemGood itemGood, actionName) {
        def responseModel = [ephytoGenInstance: ephytoGenInstance, instance: ephytoGenInstance, itemGoodInstance: itemGood]
        responseModel[actionName] = true
        render(template: '/itemGoods/itemGoodList', model: responseModel)
    }

    def renderInnerForModal(EphytoGen ephytoGenInstance, ItemGood itemGood, actionName) {
        def response = [
                headermodal: g.render(template: "/itemGoods/utils/itemGoodHeader", model: [ephytoGenInstance: ephytoGenInstance]),
                bodymodal  : g.render(template: "/itemGoods/utils/itemGoodForm", model: [ephytoGenInstance: ephytoGenInstance, itemGoodInstance: itemGood]),
                footermodal: g.render(template: "/itemGoods/utils/itemGoodFooter", model: [ephytoGenInstance: ephytoGenInstance])
        ]
        render response as JSON
    }

    private void renderTimeoutResponse() {
        LOGGER.debug("in renderTimeoutResponse")
        render(view: grailsApplication.config.appListUrl)
    }

    def updateItem() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenFromConversation()
        if (ephytoGenInstance) {
            synchronized (ephytoGenInstance) {
                def result = itemGoodService.updateItem(params, ephytoGenInstance)
                if (result.updated) {
                    renderSuccessTemplate(ephytoGenInstance, paginationService, params)
                } else {
                    renderErrorTemplate(ephytoGenInstance, result.tempItemGood)
                }
            }
        } else {
            renderTimeoutResponse()
        }
    }

    def renderSuccessTemplate(EphytoGen ephytoGenInstance, paginationService, params) {
        LOGGER.debug("In RenderSuccessTemplate : " + ephytoGenInstance)
        try {
            params.itemRank = params?.itemRank as String
            params.setProperty("itemNumber", params?.itemRank)
            def range = paginationService.getSubListRangeAfterItemUpdate(ephytoGenInstance?.itemGoods?.size(), params)
            def totalItems = ephytoGenInstance?.itemGoods?.size() == null ? 0 : ephytoGenInstance?.itemGoods?.size()
            def totalGrossWeight = ephytoGenInstance.sumGrossWeightGoods()
            def totalNetWeight = ephytoGenInstance.sumNetWeightGoods()
            def totalQuantity = ephytoGenInstance.sumQuantityGoods()
            def responseModel = [ephytoGenInstance: ephytoGenInstance, itemRange: range, itemGoods: ephytoGenInstance?.itemGoods, totalItems: totalItems, totalGrossWeight: totalGrossWeight, totalNetWeight: totalNetWeight, totalQuantity: totalQuantity]
            render(template: '/itemGoods/itemGoodList', model: responseModel)
        } catch (Exception e) {
            LOGGER.error("Error in RenderSuccessTemplate : " + e)
            render(template: '/itemGoods/itemGoodList')
        }
    }

    def renderErrorTemplate(EphytoGen ephytoGenInstance, ItemGood itemGood) {
        LOGGER.debug("In RenderErrorTemplate : " + ephytoGenInstance)

        def responseModel = [ephytoGenInstance: ephytoGenInstance, itemGood: itemGood, hasErrors: true]
        def responsePayload = [
                error  : 'error',
                payload: g.render(template: '/itemGoods/itemGoodErrorCtr', model: responseModel),
        ]
        render responsePayload as JSON
    }

}
