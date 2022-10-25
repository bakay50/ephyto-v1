package com.webbfontaine.ephyto.applicator

import com.webbfontaine.ephyto.MessageSourceUtils
import com.webbfontaine.ephyto.WebRequestUtils
import com.webbfontaine.ephyto.command.ApplicatorSearchCommand
import com.webbfontaine.ephyto.erimm.Applicator
import groovy.util.logging.Slf4j
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.commitOperation
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.conversationId

@Slf4j("LOGGER")
class ApplicatorController {


    def conversationService
    def applicatorBusinessLogicService
    def applicatorService
    def docVerificationService
    def applicatorSearchService
    def referenceService
    def springSecurityService
    def objectStoreService

    def index() {
        redirect action: 'list'
    }

    def list(ApplicatorSearchCommand searchCommand) {
        def operationDoneMessage = flash.operationDoneMessage ?: null
        flash.searchResultMessage = null
        [searchCommand: searchCommand, operationDoneMessage: operationDoneMessage]
    }

    def edit() {
        def applicatorInstance = Applicator.get(params.id)

        if (!applicatorInstance) {
            setNotFound()
            return
        }
        applicatorBusinessLogicService.initDocumentForEdit(applicatorInstance, params)
        conversationService.addToConversation(applicatorInstance)
        render(view: 'edit', model: [applicatorInstance: applicatorInstance])
    }

    def showCancel() {
        def applicatorInstance = Applicator.get(params.id)

        if (!applicatorInstance) {
            setNotFound()
            return
        }
        applicatorBusinessLogicService.initDocumentForCancel(applicatorInstance)
        conversationService.addToConversation(applicatorInstance)
        render(view: 'edit', model: [applicatorInstance: applicatorInstance])
    }

    def search(ApplicatorSearchCommand searchCommand) {
        Map searchResultModel = applicatorSearchService.getSearchResults(searchCommand, params)

        searchResultModel.referenceService = referenceService
        searchResultModel.linkParams = searchCommand.searchParams

        flash.searchResultMessage = searchResultModel.searchResultMessage

        if (request.isXhr()) {
            render(plugin: 'wf-search', template: '/searchResult', model: searchResultModel)
        } else {
            render(view: 'list', model: searchResultModel)
        }
    }

    def setNotFound() {
        flash.errorMessage = message(code: 'default.not.found.message', args: ["Document", params.id])
        redirect(action: "edit")
    }

    def create() {
        LOGGER.debug("in create() of ${ApplicatorController}")
        Applicator applicatorInstance = new Applicator()

        if (chainModel?.applicatorInstance) {
            applicatorInstance = chainModel?.applicatorInstance
        } else {
            params.conversationId = applicatorService.addToSessionStore(applicatorInstance)
        }
        applicatorBusinessLogicService.initDocumentForCreate(applicatorInstance)
        conversationService.addToConversation(applicatorInstance)
        render(view: 'edit', model: [applicatorInstance: applicatorInstance])
    }

    def show() {
        LOGGER.debug("in show() of ${ApplicatorController}");
        Applicator applicatorInstance
        if (flash?.applicatorInstanceWithErrors) {
            applicatorInstance = flash?.applicatorInstanceWithErrors
        } else {
            if (flash.applicatorToShow) {
                applicatorInstance = flash.applicatorToShow
                applicatorInstance?.operations?.clear()
            } else {
                applicatorInstance = Applicator.get(params.id)
            }
        }

        if (!applicatorInstance) {
            setNotFound()
            return
        }

        def operationDoneMessage = flash.operationDoneMessage ?: null
        applicatorBusinessLogicService.initDocumentForView(applicatorInstance)
        conversationService.addToConversation(applicatorInstance)
        render(view: 'show', model: [applicatorInstance: applicatorInstance, operationDoneMessage: operationDoneMessage])
    }

    def save() {
        LOGGER.trace("in save() of ${ApplicatorController}");
        long startTime = System.currentTimeMillis()
        Applicator applicatorInstance = objectStoreService.get(params.conversationId) as Applicator
        bindDataToDomain(applicatorInstance, params)
        updateParamsOperation(params)
        applicatorBusinessLogicService.executeOperation(applicatorInstance)

        if (docVerificationService.documentHasErrors(applicatorInstance)) {
            flash.message = null
            flash.errorMessage = message(code: 'applicator.code.error')
            render(view: 'edit', model: [applicatorInstance: applicatorInstance], hasErrors: true)
            applicatorInstance.discard()
        } else {
            conversationService.remove(conversationId)
            flash.operationDoneMessage = MessageSourceUtils.createOperationDoneMessageModelApplicator(params.commitOperationName, applicatorInstance)
            flash.applicatorToShow = applicatorInstance
            redirect(action: 'list')
        }

        LOGGER.trace("id = {}, cid = {}. {} operation took {} ms", applicatorInstance.id, conversationId, commitOperation, System.currentTimeMillis() - startTime)
    }

    def update() {
        long startTime = System.currentTimeMillis()
        def applicatorInstance = objectStoreService.get(params.conversationId) as Applicator
        bindDataToDomain(applicatorInstance, params)
        applicatorBusinessLogicService.executeOperation(applicatorInstance)
        if (docVerificationService.documentHasErrors(applicatorInstance)) {
            flash.message = null
            flash.errorMessage = message(code: 'applicator.code.error')
            render(view: "edit", model: [applicatorInstance: applicatorInstance])
        } else {
            conversationService.remove(conversationId)
            flash.operationDoneMessage = MessageSourceUtils.createOperationDoneMessageModelApplicator(params.commitOperationName, applicatorInstance)
            flash.applicatorToShow = applicatorInstance
            redirect(action: 'list')
            LOGGER.info("id = {}, cid = {}. User {} has done operation '{}'", applicatorInstance.id, conversationId, springSecurityService?.getPrincipal()?.username, commitOperation)
        }
        LOGGER.trace("id = {}, user = {}. {} operation took {} ms", applicatorInstance.id, springSecurityService?.getPrincipal()?.username, commitOperation, System.currentTimeMillis() - startTime)
    }

    def updateParamsOperation(params) {
        LOGGER.info("in updateParamsOperation() params :  ${params}")
        if (!params.commitOperation && params.currentCommitOperation && params.currentCommitOperationValue) {
            params.put(params.currentCommitOperation, params.currentCommitOperationValue)
            def commitOperation = applicatorBusinessLogicService.getCommitOperation(params)
            LOGGER.info("in updateParamsOperation() commitOperation :  ${commitOperation}")
            WebRequestUtils.putParam("commitOperation", commitOperation?.id)
            WebRequestUtils.putParam("commitOperationName", applicatorBusinessLogicService.getCommitOperationName(commitOperation?.id))
            LOGGER.info("in updateParamsOperation() commitOperation :  ${params.commitOperation}")
            LOGGER.info("in updateParamsOperation() commitOperationName :  ${params.commitOperationName}")
        }
    }

    def cancel() { update() }

    static def bindDataToDomain(applicatorInstance, params) {
        applicatorInstance?.code = params?.code
        applicatorInstance?.agreement = params?.agreement
        applicatorInstance?.nameAddress = params?.nameAddress
        applicatorInstance
    }

}
