package com.webbfontaine.ephyto.applicator

import com.webbfontaine.ephyto.config.FieldsConfiguration
import com.webbfontaine.ephyto.conversation.ConversationService
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.traits.MandatoryFieldsRule
import grails.util.Holders

import static com.webbfontaine.ephyto.config.Config.MANDATORY

class ApplicatorMandatoryFieldRule implements MandatoryFieldsRule<Applicator> {
    def conversationService

    def displayName = ["code": "code", "agreement": "agreement", "nameAddress": "nameAddress"]

    @Override
    Map<String, String> defineHumanNames() {
        return displayName
    }

    @Override
    Class<Applicator> getType() {
        return Applicator.class
    }

    @Override
    Set<String> defineMandatoryFields() {
        conversationService = Holders.grailsApplication.mainContext.getBean(ConversationService)
        Applicator applicator = conversationService.getApplicatorFromConversation()
        def map = FieldsConfiguration.applicatorConfig
        def list = []
        map.each {
            String fieldName = it.key
            def configVal = FieldsConfiguration.getFieldConfigForApplicator(fieldName, applicator)
            if (MANDATORY.isConform(configVal)) {
                list.add(fieldName)
            }
        }
        return list
    }
}