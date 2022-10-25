package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.workflow.EphytoWorkflow
import com.webbfontaine.grails.plugins.workflow.WorkflowService
import com.webbfontaine.grails.plugins.workflow.WorkflowDefinition
import com.webbfontaine.grails.plugins.workflow.containers.Buttons
import com.webbfontaine.grails.plugins.workflow.operations.*
import groovy.util.logging.Slf4j
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.springframework.transaction.annotation.Transactional

import static com.webbfontaine.ephyto.constants.Operations.OI_DELETE_STORED
import static com.webbfontaine.ephyto.constants.Operations.OP_DELETE
import static com.webbfontaine.ephyto.constants.Operations.OA_DELETE
import static com.webbfontaine.ephyto.constants.Roles.*

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@Transactional
@Slf4j
class EphytoGenBpmService extends WorkflowService {


    EphytoWorkflow ephytoWorkflow

    def messageSource

    WorkflowDefinition getWorkflow() {
        ephytoWorkflow
    }

    @Override
    WorkflowDefinition getWorkFlow(Object domainInstance) {
        getWorkflow()
    }

    def getCommitOperationName(String commitOperationId) {
        getWorkflow().operationById(commitOperationId)?.name
    }

    def boolean validationNotRequired(String commitOperation) {
        getWorkflow().operationById(commitOperation)?.properties?.containsKey(OperationConstants.NO_VALIDATION)
    }



    @Override
    def initOperationsForEdit(Object domainInstance, Map params) {
        super.initOperationsForEdit(domainInstance, params)
        accessOperations(domainInstance)
    }


    def initOperationsForDelete(Object domainInstance){
        LinkedHashSet<Operation> newOperations = new LinkedHashSet<Operation>()
        Map deleteStoredProps = new HashMap()
        deleteStoredProps.put(OperationConstants.NO_VALIDATION, false)
        deleteStoredProps.put(Buttons.BUTTON_DANGER, true)
        deleteStoredProps.put(OperationConstants.CONFIRMATION_REQUIRED, true)
        CustomActionOperation opDeleteStored = new CustomActionOperation(OI_DELETE_STORED, OP_DELETE, OA_DELETE,[ROLE_EPHYTO_DECLARANT, ROLE_EPHYTO_TRADER], deleteStoredProps)
        newOperations.add(opDeleteStored)
        EphytoGen ephytoGen = (EphytoGen) domainInstance
        ephytoGen.startedOperation = EphytoConstants.DELETE
        ephytoGen.operations = newOperations
    }

    def initOperationsForApprove(Object domainInstance, Map params){
        super.initOperations(domainInstance)
        convertDirectToUpdateOperation(domainInstance)
    }

    def initOperationsForReplace(Object domainInstance){
        super.initOperations(domainInstance)
        convertDirectToUpdateOperation(domainInstance)
    }

    def convertDirectToUpdateOperation(Object domainInstance){
        EphytoGen ephytoGen = (EphytoGen) domainInstance
        LinkedHashSet<Operation> newOperations = new LinkedHashSet<Operation>()
        Map operationProps = new HashMap()
        operationProps.put(OperationConstants.CONFIRMATION_REQUIRED,true)
        ephytoGen.operations.each{operation ->
            if(operation instanceof DirectOperation || operation instanceof DirectSubmitOperation){
                def updateOperation = new UpdateOperation(operation.id,operation.name,operation.roles,operationProps)
                newOperations.add(updateOperation)
            }
        }
        ephytoGen.operations = newOperations
        if(ephytoGen.operations.size() == 1){
            ephytoGen.startedOperation = ephytoGen.operations.toArray()[0].id
        }
    }

    def initFieldsResult(ephytoGen){
        if(ephytoGen?.status){
            try{
                ephytoGen.status =  messageSource.getMessage('ephytoGen.status.' + ephytoGen?.status,null,LCH.getLocale())
            }catch(NoSuchMessageException no){
               no.printStackTrace()
            }
        }


    }
}
