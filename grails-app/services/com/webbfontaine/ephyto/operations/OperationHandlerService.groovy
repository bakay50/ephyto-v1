package com.webbfontaine.ephyto.operations

import com.webbfontaine.ephyto.gen.EphytoGen
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.TransactionStatus
import java.lang.reflect.Field

/**
 * Copyrights 2002-2016 Webb Fontaine

 * Developer: Bakayoko Abdoulaye
 * Date: 20/03/2016

 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
abstract class OperationHandlerService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationHandlerService.class);

    def docVerificationService
    def mailNotificationService

    T execute(T domainInstance, TransactionStatus transactionStatus) {
        T result = null
        boolean hasErrors = true
        def initialStatus = getInitialStatus(domainInstance)
        def commitOperation = getCommitOperation()
        try {
            beforePersist(domainInstance)
            hasErrors = docVerificationService.documentHasErrors(domainInstance)
            if(hasErrors){
                result = domainInstance
            } else {
                updateStatus(domainInstance, commitOperation)
                addTransactionLog(domainInstance)
                result = doPersist(domainInstance)
                if (!result) {
                    result = domainInstance
                    result.errors.rejectValue("id", "Internal Error. Please ask for support.")
                }
                hasErrors = docVerificationService.documentHasErrors(domainInstance)
                if (hasErrors) {
                    transactionStatus.setRollbackOnly()
                }
            }

        } catch (Exception exception) {
            hasErrors = true
            transactionStatus.setRollbackOnly()
            domainInstance?.discard()
            result = domainInstance
            handleException(result, exception)
            LOGGER.error("", exception)
        } finally {
            if (hasErrors) {
                if(docHasStatus()) {
                    result?.status = initialStatus
                }
            }else if(result instanceof EphytoGen){
                mailNotificationService.notifyByMail(result, getCommitOperation())
            }
            afterPersist(domainInstance, result, hasErrors)
        }
        return result
    }

    private T doPersist(T domainInstance) {
        if (isCreate()) {
            return domainInstance.save(flush: true, validate: false)
        } else {
            return domainInstance.merge(flush: true, validate: false)
        }
    }

    private void touchLazyCollections( result) {
        if (result.hasProperty('skipValidation')) {
            result.skipValidation.each { skipValidationCollection ->
                result[skipValidationCollection].each {
                    touchLazyCollections(it)
                }
            }
        }
    }

    protected void handleException(domainInstance, Exception exception) {
        domainInstance.errors.rejectValue("id", "Error. ${exception?.getMessage() ?: ""}. Please try again.")
    }

    protected getInitialStatus(T domainInstance) {
        def declaredFields = domainInstance?.getClass()?.getDeclaredFields()
        def statusField = declaredFields?.find { Field f -> f.getName() == 'status'}
        if (statusField) {
            return domainInstance['status']
        }
        return null
    }



    protected abstract String getCommitOperation()

    protected abstract boolean isCreate()

    protected abstract boolean docHasStatus()

    protected updateStatus(T domainInstance, String commitOperation) {}

    public beforePersist(T domainInstance) {}

    public afterPersist(T domainClass, T result, boolean hasErrors) {}

    protected addTransactionLog(T domainClass) {}

}
