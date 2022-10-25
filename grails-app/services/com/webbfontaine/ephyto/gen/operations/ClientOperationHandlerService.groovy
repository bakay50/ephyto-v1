package com.webbfontaine.ephyto.gen.operations

import com.webbfontaine.ephyto.WebRequestUtils
import com.webbfontaine.ephyto.operations.OperationHandlerService

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 20/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class ClientOperationHandlerService<T> extends OperationHandlerService<T> {
    def ephytoWorkflow
    def ephytoGenBpmService
    def loggerService

    @Override
    protected String getCommitOperation() {
        return WebRequestUtils.getCommitOperation()
    }

    @Override
    protected boolean isCreate() {
        return ephytoWorkflow.isCreateOperation(getCommitOperation())
    }

    @Override
    protected boolean docHasStatus() {
        return true
    }

    @Override
    protected updateStatus(T domainInstance, String commitOperation) {
        domainInstance.status = ephytoGenBpmService.getEndStatus(getEphytoWorkflow(), domainInstance.status, commitOperation)
    }

    @Override
    protected addTransactionLog(T domainInstance) {
        loggerService.addTransactionLog(domainInstance)
    }
}
