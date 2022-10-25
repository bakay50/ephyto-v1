package com.webbfontaine.ephyto.conversation


import com.webbfontaine.ephyto.DataBindingHelper
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.gen.EphytoGen
import grails.databinding.SimpleMapDataBindingSource
import static com.webbfontaine.ephyto.WebRequestUtils.getParams
import static com.webbfontaine.ephyto.WebRequestUtils.getSessionScope
import static com.webbfontaine.ephyto.WebRequestUtils.getFlashScope
import static com.webbfontaine.ephyto.WebRequestUtils.getConversationId

class ConversationService {

    static transactional = false
    def sessionStoreService
    def grailsWebDataBinder
    def businessLogicService
    def initFieldsValueInUTF8Service

    public EphytoGen mergeEphytoGenConversationInstance() {
        EphytoGen ephytoGen = getEphytoGenFromConversation()
        if (ephytoGen) {
            bindData(ephytoGen, params, DataBindingHelper.getEphytoGenAcceptableFields(ephytoGen))
            businessLogicService.initTotalEphyto(ephytoGen)
            initFieldsValueInUTF8Service.initFieldsValueInUTF8(ephytoGen, params)
            return ephytoGen
        } else {
            return null
        }

    }

    public EphytoGen getEphytoGenFromConversation() {
        return sessionStoreService.get(conversationId) as EphytoGen
    }

    public Applicator getApplicatorFromConversation() {
        return sessionStoreService.get(conversationId) as Applicator
    }

    def EphytoGen getEphytoGenInstanceFromParams() {
        EphytoGen ephytoGen = new EphytoGen()
        bindData(ephytoGen, params, DataBindingHelper.getEphytoGenAcceptableFields(ephytoGen))
        return ephytoGen
    }

    def Applicator getApplicatorInstanceFromParams() {
        Applicator applicator = new Applicator()
        bindData(applicator, params, DataBindingHelper.getApplicatorAcceptableFields(applicator))
        return applicator
    }

    def addToConversation(def domainInstance) {
        if (!params.conversationId) {
            params.conversationId = (sessionScope.id + System.nanoTime()).hashCode()
            flashScope.message = null
        }
        sessionStoreService.put(params.conversationId, domainInstance)
    }

    def remove(def conversationId) {
        sessionStoreService.remove(conversationId)
    }

    def bindData(Object target, Map data, List<String> whiteList) {
        grailsWebDataBinder.bind(target, data as SimpleMapDataBindingSource, whiteList)
        target
    }

    def bindData(Object target, Map data, List<String> whiteList, List<String> blackList) {
        grailsWebDataBinder.bind(target, data as SimpleMapDataBindingSource, whiteList, blackList)
        target
    }


}
