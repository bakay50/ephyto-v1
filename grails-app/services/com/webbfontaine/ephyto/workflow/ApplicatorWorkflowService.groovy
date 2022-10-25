package com.webbfontaine.ephyto.workflow

import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.grails.plugins.workflow.WorkflowDefinition
import com.webbfontaine.grails.plugins.workflow.WorkflowService
import com.webbfontaine.grails.plugins.workflow.containers.Buttons
import com.webbfontaine.grails.plugins.workflow.operations.CustomActionOperation
import com.webbfontaine.grails.plugins.workflow.operations.OperationConstants
import grails.gorm.transactions.Transactional
import static com.webbfontaine.ephyto.workflow.Operation.CANCEL_VALID

import static com.webbfontaine.ephyto.constants.Operations.OA_CANCEL
import static com.webbfontaine.ephyto.constants.Operations.OC_CANCEL_VALID
import static com.webbfontaine.ephyto.constants.Operations.OP_CANCEL
import static com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_SUPER_ADMINISTRATOR

@Transactional
class ApplicatorWorkflowService extends WorkflowService{
    ApplicatorWorkflow applicatorWorkflow

    WorkflowDefinition getWorkflow() {
        applicatorWorkflow
    }

    @Override
    WorkflowDefinition getWorkFlow(Object domainInstance) {
        getWorkflow()
    }

    def boolean validationNotRequired(String commitOperation) {
        getWorkflow().operationById(commitOperation)?.properties?.containsKey(OperationConstants.NO_VALIDATION)
    }

    def getCommitOperationName(String commitOperationId) {
        getWorkflow().operationById(commitOperationId)?.name
    }

    def initOperationsForCancel(Object domainInstance){
        LinkedHashSet<Operation> newOperations = new LinkedHashSet<Operation>()
        Map deleteStoredProps = new HashMap()
        deleteStoredProps.put(OperationConstants.NO_VALIDATION, false)
        deleteStoredProps.put(Buttons.BUTTON_DANGER, true)
        deleteStoredProps.put(OperationConstants.CONFIRMATION_REQUIRED, true)
        CustomActionOperation opDeleteStored = new CustomActionOperation(OC_CANCEL_VALID, OP_CANCEL, OA_CANCEL,[ROLE_EPHYTO_SUPER_ADMINISTRATOR], deleteStoredProps)
        newOperations.add(opDeleteStored)
        Applicator applicator = (Applicator) domainInstance
        applicator.startedOperation = CANCEL_VALID
        applicator.operations = newOperations
    }
}
