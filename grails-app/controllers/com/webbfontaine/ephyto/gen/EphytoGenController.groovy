package com.webbfontaine.ephyto.gen

import com.webbfontaine.ephyto.MessageSourceUtils
import com.webbfontaine.ephyto.WebRequestUtils
import com.webbfontaine.ephyto.command.SearchCommand
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.Operations
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import java.util.concurrent.locks.Lock

import static com.webbfontaine.ephyto.EphytoGenUtils.createModel
import static com.webbfontaine.ephyto.WebRequestUtils.commitOperation
import static com.webbfontaine.ephyto.WebRequestUtils.getConversationId

@Transactional(readOnly = true)
class EphytoGenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EphytoGenController.class);
    //static allowedMethods = [save: "POST", update: "POST"]

    def conversationService
    def paginationService
    def businessLogicService
    def synchronizationService
    def docVerificationService
    def springSecurityService
    def initFieldsValueInUTF8Service
    def ephytoGenBpmService


    def ephytoGenSearchService

    def index() {
        redirect action: 'list'
    }

    def list(SearchCommand searchCommand) {
        flash.searchResultMessage = null
        [searchCommand: searchCommand]
    }


    def search(SearchCommand searchCommand) {
        long startTime = System.currentTimeMillis()
        initFieldsValueInUTF8Service.updateSearchFields(searchCommand, params)
        Map searchResultModel = ephytoGenSearchService.getSearchResults(searchCommand, params)
        LOGGER.debug("in search() of params end  :${params} searchcommand: ${searchCommand}");
        def searchLimit = grailsApplication.config.searchConfig.limit
        flash.searchResultMessage = searchResultModel.totalCount > searchLimit ? message(code: 'search.result_limit.label', args: [searchLimit, searchResultModel.totalCount]) : searchResultModel.searchResultMessage
        LOGGER.trace("user = {}. {} operation took {} ms", springSecurityService?.getPrincipal()?.username, 'Search', System.currentTimeMillis() - startTime)
        if (request.isXhr()) {

            render(plugin: 'wf-search', template: '/grails-app/views/searchResult', model: searchResultModel)
        } else {

            render(view: 'list', model: searchResultModel)
        }
    }


    def create() {
        EphytoGen ephytoGenInstance = conversationService.getEphytoGenInstanceFromParams()
        businessLogicService.initDocumentForCreate(ephytoGenInstance)
        conversationService.addToConversation(ephytoGenInstance)
        render(view: 'edit', model: createModel(ephytoGenInstance, paginationService))
    }

    def save() {
        LOGGER.trace("in save() of ${EphytoGenController}");
        long startTime = System.currentTimeMillis()

        Lock lock = synchronizationService.lock(EphytoGen, conversationId)
        lock.lock()
        try {
            EphytoGen ephytoGenInstance = conversationService.mergeEphytoGenConversationInstance()
            updateParamsOperation(params)
            businessLogicService.initEphytoGenOwner(ephytoGenInstance, params.commitOperation)
            if (commitOperation?.equals(Operations.OP_REPLACE)) {
                ephytoGenInstance = executeReplaceOperation(ephytoGenInstance)
            } else {
                executeOperation(ephytoGenInstance)
            }
            if (docVerificationService.documentHasErrors(ephytoGenInstance)) {
                flash.message = null
                if (commitOperation?.equals(Operations.OI_REPLACE_APPROVED)) {
                    ephytoGenInstance?.status = null
                }
                ephytoGenInstance?.startedOperation = EphytoConstants.CREATE
                render(view: 'edit', model: createModel(ephytoGenInstance, paginationService, true))
                ephytoGenInstance.discard()
            } else {
                conversationService.remove(conversationId)
                flash.operationDoneMessage = MessageSourceUtils.createOperationDoneMessageModel(params.commitOperationName, ephytoGenInstance)
                flash.ephtytoToShow = ephytoGenInstance
                redirect(action: 'show', id: ephytoGenInstance.id)
            }

            LOGGER.trace("id = {}, cid = {}. {} operation took {} ms", ephytoGenInstance.id, conversationId, commitOperation, System.currentTimeMillis() - startTime)
        } finally {
            lock.unlock()
        }
    }

    def show() {
        LOGGER.debug("in show() of ${EphytoGenController}");
        EphytoGen ephytoGenInstance
        if (flash?.ephytoGenInstanceWithErrors) {
            ephytoGenInstance = flash?.ephytoGenInstanceWithErrors
        } else {
            if (flash.ephtytoToShow) {
                ephytoGenInstance = flash.ephtytoToShow
                ephytoGenInstance?.operations?.clear()
            } else {
                ephytoGenInstance = ephytoGenById(params.id)
            }
        }

        if (!ephytoGenInstance) {
            setNotFound()
            return
        }

        def operationDoneMessage = flash.operationDoneMessage ?: null
        businessLogicService.initDocumentForView(ephytoGenInstance)
        conversationService.addToConversation(ephytoGenInstance)
        render(view: 'show', model: [ephytoGenInstance: ephytoGenInstance, operationDoneMessage: operationDoneMessage])
    }

    def showDelete() {
        LOGGER.debug("in showDelete() of ${EphytoGenController}");
        EphytoGen ephytoGenInstance = ephytoGenById(params.id)
        if (!ephytoGenInstance) {
            setNotFound()
            return
        }
        businessLogicService.initDocumentForDeleteEphyto(ephytoGenInstance)
        conversationService.addToConversation(ephytoGenInstance)
        ephytoGenInstance.startedOperation = EphytoConstants.DELETE
        LOGGER.debug("in showDelete() of operations :  ${ephytoGenInstance.operations}");
        LOGGER.debug("in showDelete() of params id :  ${params.id}");
        render(view: 'show', model: [ephytoGenInstance: ephytoGenInstance, id: ephytoGenInstance?.id])

    }


    def edit() {
        def ephytoGenInstance = ephytoGenById(params.id)

        if (!ephytoGenInstance) {
            setNotFound()
            return
        }
        businessLogicService.initDocumentForEdit(ephytoGenInstance, params)
        conversationService.addToConversation(ephytoGenInstance)
        createModel(ephytoGenInstance, paginationService)
    }

    def approve() {
        def ephytoGenInstance = ephytoGenById(params.id)
        if (!ephytoGenInstance) {
            setNotFound()
            return
        }
        businessLogicService.initDocumentForApprove(ephytoGenInstance, params)
        conversationService.addToConversation(ephytoGenInstance)
        def model = createModel(ephytoGenInstance, paginationService)
        render(view: "edit", model: model)
    }

    def update() {
        long startTime = System.currentTimeMillis()
        def ephytoGenInstance = conversationService.mergeEphytoGenConversationInstance()
        Lock lock = synchronizationService.lock(EphytoGen.class, ephytoGenInstance.id)
        lock.lock()
        try {
            if (params.commitOperation in [Operations.OI_MODIFY_APPROVED, Operations.OI_MODIFY_SIGNED]) {
                openDocForReplace(ephytoGenInstance, params)
            } else if (params.commitOperation in [Operations.OA_DELETE]) {
                delete()
            } else {
                executeOperation(ephytoGenInstance)
                if (docVerificationService.documentHasErrors(ephytoGenInstance)) {
                    flash.message = null
                    render(view: "edit", model: createModel(ephytoGenInstance, paginationService, true))
                } else {
                    conversationService.remove(conversationId)
                    flash.operationDoneMessage = MessageSourceUtils.createOperationDoneMessageModel(params.commitOperationName, ephytoGenInstance)
                    flash.ephtytoToShow = ephytoGenInstance
                    redirect(action: "show", id: params.id, method: 'GET')
                    LOGGER.info("id = {}, cid = {}. User {} has done operation '{}'", ephytoGenInstance.id, conversationId, springSecurityService?.getPrincipal()?.username, commitOperation)
                }
            }
            LOGGER.trace("id = {}, user = {}. {} operation took {} ms", ephytoGenInstance.id, springSecurityService?.getPrincipal()?.username, commitOperation, System.currentTimeMillis() - startTime)
        } finally {
            lock.unlock()
        }
    }


    def delete() {
        LOGGER.info("id = {}", params.id)
        EphytoGen ephytoGenInstance = ephytoGenById(params.id)
        if (!ephytoGenInstance) {
            setNotFound()
            return
        }
        try {
            ephytoGenInstance.delete(flush: true)
            SearchCommand searchCommand
            params.commitOperationName = "Delete"
            if (conversationId) {
                flash.message = MessageSourceUtils.createOperationDoneMessage(params.commitOperationName)
                conversationService.remove(conversationId)
                searchCommand = new SearchCommand()
                params.clear()
            } else {
                searchCommand = new SearchCommand(params)
                params.fromDirectOperation = true
            }
            chain(action: 'search', model: [searchCommand: searchCommand], params: params)
        } catch (Exception exception) {
            LOGGER.error("", exception)
            flash.errorMesssage = message(code: 'default.not.deleted.message', args: [message(code: 'app.ui.name', default: 'Document'), params?.id])
            redirect(action: 'show', id: ephytoGenInstance?.id)
        }
    }

    private executeOperation(ephytoGenInstance) {
        if (isValidInstance(ephytoGenInstance)) {
            EphytoGen.withNewTransaction { localTransactionStatus ->
                def opHandler = businessLogicService.resolveEphytoGenOperationHandler(commitOperation as String)
                ephytoGenInstance = opHandler.execute(ephytoGenInstance, localTransactionStatus)
            }
        }
    }


    private boolean isValidInstance(EphytoGen ephytoGenInstance) {
        return docVerificationService.deepVerify(ephytoGenInstance) && ephytoGenInstance.validate()
    }

    private def getOperationDoneMessage(String commitOperationName, EphytoGen ephytoGen) {
        if ([Operations.OP_REQUEST].contains(commitOperationName)) {
            return MessageSourceUtils.createOperationDoneMessage(commitOperationName, ephytoGen)
        } else {
            return MessageSourceUtils.createOperationDoneMessage(commitOperationName)
        }
    }

    @Transactional(readOnly = true)
    private static def ephytoGenById(def id) {
        try {
            EphytoGen.withTransaction {
                Long docId = Long.parseLong(id)
                return EphytoGen.get(docId)
            }
        } catch (NumberFormatException ignored) {
        }
    }

    def setNotFound() {
        flash.errorMessage = message(code: 'default.not.found.message', args: ["Document", params.id])
        redirect(action: "list")
    }

    def openDocForReplace(ephytoGenInstance, params) {
        def newEphytoGenInstance
        def model
        ephytoGenInstance.notProperlyInitialized = false
        newEphytoGenInstance = businessLogicService.newInstanceOnReplace(ephytoGenInstance, params)
        conversationService.addToConversation(newEphytoGenInstance)
        model = createModel(newEphytoGenInstance, paginationService)
        render(view: "edit", model: model)
    }


    def executeReplaceOperation(ephytoGenInstance) {
        LOGGER.debug("In executeReplaceOperation method ")

        if (isValidInstance(ephytoGenInstance)) {
            def oldInstance = EphytoGen.get(ephytoGenInstance?.tempId as Long)
            ephytoGenInstance?.previousDocRef = oldInstance?.requestNumber
            ephytoGenInstance = businessLogicService.applyApproveStatus(ephytoGenInstance)
            if (!ephytoGenInstance.hasErrors()) {
                LOGGER.debug("Executing Replace Operation for doc :  ${oldInstance?.id}")

                oldInstance?.nextDocRef = ephytoGenInstance?.requestNumber
                EphytoGen.withNewTransaction { localTransactionStatus ->
                    def opHandler = businessLogicService.resolveEphytoGenOperationHandler(commitOperation as String)
                    opHandler.execute(oldInstance, localTransactionStatus)
                }
            }
        }
        ephytoGenInstance
    }

    def updateParamsOperation(params) {
        LOGGER.info("in updateParamsOperation() params :  ${params}")
        if (!params.commitOperation && params.currentCommitOperation && params.currentCommitOperationValue) {
            params.put(params.currentCommitOperation, params.currentCommitOperationValue)
            def commitOperation = ephytoGenBpmService.getCommitOperation(params)
            LOGGER.info("in updateParamsOperation() commitOperation :  ${commitOperation}")
            WebRequestUtils.putParam("commitOperation", commitOperation?.id)
            WebRequestUtils.putParam("commitOperationName", ephytoGenBpmService.getCommitOperationName(commitOperation?.id))
            LOGGER.info("in updateParamsOperation() commitOperation :  ${params.commitOperation}")
            LOGGER.info("in updateParamsOperation() commitOperationName :  ${params.commitOperationName}")
        }
    }

}
