package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.conversation.ConversationService
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.traits.MandatoryFieldsRule
import com.webbfontaine.ephyto.config.FieldsConfiguration

import grails.util.Holders

import static com.webbfontaine.ephyto.config.Config.MANDATORY

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 4/22/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class GenMandatoryFieldRule implements MandatoryFieldsRule<EphytoGen> {

    def conversationService

    // add All fields of General segement of ephyto with default translation in English
    def displayName = ["requestNumber": "Request Number", "requestDate": "Request Date","exporterCode":"Exporter Code","exporterName":"Exporter Name","declarantCode":"declarant Code","declarantName":"declarant Name","commodityCode":"commodity Code"]

    @Override
    Map<String, String> defineHumanNames() {
        return displayName
    }

    @Override
    Class<EphytoGen> getType() {
        return EphytoGen.class
    }



    @Override
    Set<String> defineMandatoryFields() {
        conversationService = Holders.grailsApplication.mainContext.getBean(ConversationService)
        EphytoGen ephytoGen = conversationService.getEphytoGenFromConversation()
        def map = FieldsConfiguration.ephytoGenConfig
        def list = []

        map.each {
            String fieldName = it.key
            def configVal = FieldsConfiguration.getFieldConfigForGenDoc(fieldName, ephytoGen)
            if (MANDATORY.isConform(configVal)) {
                list.add(fieldName)
            }
        }
        return list
    }
}
