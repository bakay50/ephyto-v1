package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.operations.OperationHandlerService
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j

import static com.webbfontaine.ephyto.constants.Operations.OP_REGISTER
import static com.webbfontaine.ephyto.constants.Operations.OP_CANCEL_VALID
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.commitOperation

@Slf4j("LOGGER")
@Transactional
class ApplicatorBusinessLogicService {

    def applicatorWorkflowService
    def registerOperationHandlerService
    def clientApplicatorOperationHandlerService
    def docVerificationService

    Applicator initDocumentForCreate(Applicator applicator) {
        applicator?.isDocumentEditable = true
        applicator?.startedOperation = EphytoConstants.CREATE
        applicatorWorkflowService.initOperations(applicator)
        return applicator
    }

    def OperationHandlerService resolveApplicatorOperationHandler(String commitOperation) {
        switch (commitOperation) {
            case OP_REGISTER:
                return registerOperationHandlerService
            default:
                return clientApplicatorOperationHandlerService
        }
    }

    Applicator initDocumentForView(Applicator applicator) {
        applicator.setIsDocumentEditable(false)
        applicator.startedOperation = EphytoConstants.SHOW
        return applicator
    }

    def initDocumentForEdit(Applicator applicator, Map params) {
        applicator.setIsDocumentEditable(true)
        applicatorWorkflowService.initOperationsForEdit(applicator, params)
        LOGGER.info("Operations in initDocumentForEdit:${applicator?.operations?.id}")
        return applicator
    }

    def initDocumentForCancel(Applicator applicator){
        applicator.setIsDocumentEditable(false)
        applicator.startedOperation = OP_CANCEL_VALID
        applicatorWorkflowService.initOperationsForCancel(applicator)
        return applicator
    }

    private boolean isValidInstance(Applicator applicatorInstance) {
        return docVerificationService.deepVerify(applicatorInstance)
    }

    public executeOperation(applicatorInstance) {
        if (isValidInstance(applicatorInstance)) {
            Applicator.withNewTransaction { localTransactionStatus ->
                def opHandler = resolveApplicatorOperationHandler(commitOperation as String)
                applicatorInstance = opHandler.execute(applicatorInstance, localTransactionStatus)
            }
        }
    }

}
