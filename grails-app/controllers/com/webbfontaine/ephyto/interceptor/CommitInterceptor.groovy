package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.gen.EphytoGen
import groovy.util.logging.Slf4j

import static com.webbfontaine.ephyto.EphytoGenUtils.createModel


@Slf4j
class CommitInterceptor {

    def objectStoreService
    def ephytoSecurityService
    def ephytoGenBpmService
    def paginationService
    def conversationService
    def businessLogicService

    CommitInterceptor() {
        match(controller: 'ephytoGen', action: '(save|update)')
    }

    boolean before() {
        if (!session) {
            redirect(url: '/')
            return false
        }
        def ephytoGen = objectStoreService?.get(params.conversationId)
        if(ephytoGen instanceof EphytoGen){
            ephytoGen as EphytoGen
            def commitOperation = ephytoGenBpmService.getCommitOperation(params)
            params.commitOperation = commitOperation?.id
            params.commitOperationName = ephytoGenBpmService.getCommitOperationName(params.commitOperation)
            if (ephytoGenBpmService.validationNotRequired(params.commitOperation)) {
                params.validationNotRequired = true
            } else {
                params.validationRequired = true
            }
            if (!ephytoGen) {
                if (!docInObjectStore(params)) {
                    ephytoGen = conversationService.getEphytoGenInstanceFromParams()
                    businessLogicService.initDocumentForCreate(ephytoGen)
                    conversationService.addToConversation(ephytoGen)
                    render(view: 'edit', model: createModel(ephytoGen, paginationService))
                    return false
                } else {
                    return true
                }
            } else {
                return true
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
