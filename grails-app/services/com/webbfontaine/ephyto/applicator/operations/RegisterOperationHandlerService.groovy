package com.webbfontaine.ephyto.applicator.operations

import com.webbfontaine.ephyto.WebRequestUtils
import com.webbfontaine.ephyto.erimm.Applicator
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j

@Transactional
@Slf4j("LOGGER")
class RegisterOperationHandlerService extends ClientApplicatorOperationHandlerService<Applicator> {
    def applicatorWorkflow

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
}