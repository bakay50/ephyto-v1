/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webbfontaine.ephyto.gen

import com.webbfontaine.ephyto.DataBindingHelper
import com.webbfontaine.ephyto.TypeCastUtils
import grails.gorm.transactions.Transactional


/**
 *
 * @author sylla
 */

import org.grails.web.servlet.mvc.GrailsWebRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static com.webbfontaine.ephyto.TypeCastUtils.toInteger

@Transactional
class ItemGoodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemGoodService.class)
    static transactional = false
    def conversationService
    def docVerificationService
    def initFieldsValueInUTF8Service

    def isItemInstanceValid(ItemGood itemGood) {
        return docVerificationService.deepVerify(itemGood) && itemGood.validate()
    }

    def updateItem(Map params, EphytoGen ephytoGenInstance) {
        def rank = TypeCastUtils.toInteger(params.itemRank)
        ItemGood tempItemGood = new ItemGood(ephytoGenInstance.getItem(rank).properties)
        initFieldsValueInUTF8Service.initGoodFieldsValueInUTF8(ephytoGenInstance,tempItemGood, params)
        conversationService.bindData(tempItemGood, params, DataBindingHelper.getItemGoodAcceptableFields(ephytoGenInstance))
        boolean updated = false
        ephytoGenInstance?.clearErrors()
        if (isItemInstanceValid(tempItemGood)) {
            ephytoGenInstance.getItem(rank).properties = tempItemGood.properties
            updated = true
        }
        return [updated: updated, tempItemGood: tempItemGood]
    }

    def addItem(Map params, EphytoGen ephytoGenInstance) {
        LOGGER.debug("In addItem ")
        boolean added = false
        ItemGood item = new ItemGood()
        try{
            initFieldsValueInUTF8Service.initGoodFieldsValueInUTF8(ephytoGenInstance,item, params)
            conversationService.bindData(item, params, DataBindingHelper.getItemGoodAcceptableFields(ephytoGenInstance))
            item?.ephytoGen = ephytoGenInstance
            item.itemRank = ephytoGenInstance.getItemSequenceNumber()
            ephytoGenInstance?.clearErrors()
            if (isItemInstanceValid(item)) {
                LOGGER.debug("Item is Valid ")
                ephytoGenInstance.addItem(item)
                added = true
            }
        }catch (Exception e){
            LOGGER.error("Error in addItem() :${e} ")

        }
        return [added: added, itemGood: item]
    }

    def removeItem(EphytoGen ephytoGenInstance) {
        def rank = currentRequestParams().itemRank?toInteger(currentRequestParams().itemRank): 0
        ItemGood itemGood = ephytoGenInstance.getItem(rank)
        boolean removed
        if (itemGood) {
            ephytoGenInstance.removeItem(rank)
            removed = true
        } else {
            removed = false
            itemGood = ItemGood.newInstance()
            itemGood.errors.rejectValue('rank', 'goodItem.errors.notFound', 'Cannot find Item from the list')
        }
        return [removed: removed, itemGood: itemGood]
    }

    private static Map currentRequestParams() {
        GrailsWebRequest.lookup().params
    }

}

