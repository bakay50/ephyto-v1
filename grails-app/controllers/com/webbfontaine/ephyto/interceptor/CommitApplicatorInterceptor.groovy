package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.erimm.Applicator
import groovy.util.logging.Slf4j

import static com.webbfontaine.ephyto.EphytoGenUtils.createModel


@Slf4j
class CommitApplicatorInterceptor {

    def objectStoreService
    def ephytoSecurityService
    def conversationService
    def applicatorWorkflowService
    def applicatorBusinessLogicService

    CommitApplicatorInterceptor() {
        match(controller: 'applicator', action: '(save|update)')
    }

    boolean before() {
        if (!session) {
            redirect(url: '/')
            return false
        }
        def domainInstance = objectStoreService?.get(params.conversationId)
         if(domainInstance instanceof Applicator){
            domainInstance as Applicator
            def commitOperation = applicatorWorkflowService.getCommitOperation(params)
            params.commitOperation = commitOperation?.id
            params.commitOperationName = applicatorWorkflowService.getCommitOperationName(params.commitOperation)
            if (applicatorWorkflowService.validationNotRequired(params.commitOperation)) {
                params.validationNotRequired = true
            } else {
                params.validationRequired = true
            }

            if (domainInstance) {
                return true
            } else {
                if (docInObjectStore(params)) {
                    return true
                } else {
                    domainInstance = conversationService.getApplicatorInstanceFromParams()
                    applicatorBusinessLogicService.initDocumentForCreate(domainInstance)
                    conversationService.addToConversation(domainInstance)
                    render(view: 'edit', model: createModel(domainInstance))
                    return false
                }
            }
        }else{
             return false
         }
    }

    boolean after() {
        true
    }

    void afterView() {
        true
    }


    private boolean docInObjectStore(def params) {
        def docInObjectStore = objectStoreService.get(params.conversationId) as boolean
        return docInObjectStore
    }


}
