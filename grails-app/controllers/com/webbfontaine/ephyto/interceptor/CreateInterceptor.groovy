package com.webbfontaine.ephyto.interceptor


import com.webbfontaine.ephyto.MessageSourceUtils
import com.webbfontaine.ephyto.constants.EphytoConstants
import grails.web.mvc.FlashScope
import groovy.util.logging.Slf4j
import org.grails.web.servlet.mvc.GrailsWebRequest


@Slf4j
class CreateInterceptor {

    def objectsStoreService
    def springSecurityService

    CreateInterceptor() {
        match controller: 'ephytoGen', action: 'create'
        match controller: 'xml', action: 'importXML'
        match controller: 'attachment', action: '*'
        match controller: 'print', action: 'print'
        match controller: 'print', action: 'printDraft'
    }

    boolean before() {
        GrailsWebRequest webRequest = GrailsWebRequest.lookup()
        if (webRequest.controllerName == EphytoConstants.EPHYTO_DOMAIN && webRequest.actionName == EphytoConstants.EPHYTO_CREATE) {
            return true
        } else if (webRequest.controllerName == EphytoConstants.EPHYTO_PRINT && webRequest.actionName == EphytoConstants.EPHYTO_PRINT) {
            return true
        } else if (webRequest.controllerName == EphytoConstants.EPHYTO_PRINT && webRequest.actionName == EphytoConstants.EPHYTO_PRINT_DRAFT) {
            return true
        } else {
            return true
        }
    }

    boolean after() {
        true
    }

    void afterView() {
        true
    }


    private boolean docInObjectStore(def params, FlashScope flash) {
        def docInObjectStore = objectsStoreService?.get(params.conversationId) as boolean
        if (!docInObjectStore) {
            flash.errorMessage = MessageSourceUtils.getMessage('default.expired.message')
        }
        return docInObjectStore
    }


}
