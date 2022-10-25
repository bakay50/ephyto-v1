package com.webbfontaine.ephyto.applicator.operations

import com.webbfontaine.ephyto.WebRequestUtils
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.operations.OperationHandlerService
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import org.springframework.transaction.TransactionStatus

@Slf4j("LOGGER")
@Transactional
class ClientApplicatorOperationHandlerService<T> extends OperationHandlerService<Applicator>{

    def applicatorWorkflow
    def applicatorWorkflowService
    def docVerificationService

    @Override
    protected String getCommitOperation() {
        return WebRequestUtils.getCommitOperation()
    }

    @Override
    protected boolean isCreate() {
        return applicatorWorkflow.isCreateOperation(getCommitOperation())
    }

    @Override
    protected boolean docHasStatus() {
        return true
    }

    @Override
    protected updateStatus(T domainInstance, String commitOperation) {
        domainInstance.status = applicatorWorkflowService.getEndStatus(getApplicatorWorkflow(), domainInstance.status, commitOperation)
    }

    @Override
    Applicator execute(Applicator applicatorInstance, TransactionStatus t) {
        Applicator result = null
        boolean hasErrors = true
        def initialStatus = getInitialStatus(applicatorInstance)
        def commitOperation = getCommitOperation()
        try {
            beforePersist(applicatorInstance)
            hasErrors = docVerificationService.documentHasErrors(applicatorInstance)
            if(hasErrors){
                result = applicatorInstance
            } else {
                updateStatus(applicatorInstance, commitOperation)
                result = doPersist(applicatorInstance)
                if (!result) {
                    result = applicatorInstance
                    result.errors.rejectValue("id", "Internal Error. Please ask for support.")
                }
                hasErrors = docVerificationService.documentHasErrors(applicatorInstance)
                if (hasErrors) {
                    t.setRollbackOnly()
                }
            }
        } catch (IllegalArgumentException exception) {
            hasErrors = true
            t.setRollbackOnly()
            applicatorInstance?.discard()
            result = applicatorInstance
            handleException(result, exception)
            LOGGER.error("", exception)
        } finally {
            if (hasErrors) {
                if(docHasStatus()) {
                    result?.status = initialStatus
                }
            }
            afterPersist(applicatorInstance, result, hasErrors)
        }
        return result
    }
}
