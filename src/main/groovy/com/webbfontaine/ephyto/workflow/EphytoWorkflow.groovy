package com.webbfontaine.ephyto.workflow

import com.webbfontaine.grails.plugins.workflow.Transition
import com.webbfontaine.grails.plugins.workflow.containers.OperationsContainer
import com.webbfontaine.grails.plugins.workflow.containers.TransitionsContainer
import com.webbfontaine.ephyto.constants.Statuses
import static com.webbfontaine.grails.plugins.workflow.containers.Buttons.BUTTON_DANGER
import static com.webbfontaine.grails.plugins.workflow.operations.OperationConstants.*
import static com.webbfontaine.ephyto.workflow.Operation.*
import static com.webbfontaine.ephyto.constants.Operations.*


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoWorkflow extends AbstractWorkflowDefinition {

    private static final CREATE_OPERATIONS = [STORE.humanName(), REQUEST.humanName()]

    private static final ALL_ROLES = [Role.DECLARANT.getAuthority(),Role.TRADER.getAuthority(),
                                      Role.GOV_OFFICER.getAuthority(),Role.GOV_SENIOR_OFFICER.getAuthority(),Role.ADMINISTRATOR.getAuthority(), Role.GOV_SUPERVISOR.getAuthority(),
                                      Role.SUPER_ADMINISTRATOR.getAuthority()]

    private static final VIEW_STORED_ROLES = [Role.DECLARANT.getAuthority(), Role.TRADER.getAuthority(),
                                              Role.GOV_OFFICER.getAuthority(),Role.GOV_SENIOR_OFFICER.getAuthority(),Role.ADMINISTRATOR.getAuthority(),Role.GOV_SUPERVISOR.getAuthority()]


    @Override
    def defineOperations(OperationsContainer operations) {
        createOperations(operations)
        editStoredOperations(operations)
        editRequestedOperations(operations)
        editSignedOperations(operations)
        editApprovedOperations(operations)
        editQueriedOperations(operations)
        directOperations(operations)
        viewOperation(operations)
        return operations
    }

    @Override
    def defineTransitions(TransitionsContainer transitions) {
        transitions.addTransition(new Transition(null, operationById(STORE.humanName()), Statuses.ST_STORED))
        transitions.addTransition(new Transition(null, operationById(REQUEST.humanName()), Statuses.ST_REQUESTED))
        transitions.addTransition(new Transition(Statuses.ST_STORED, operationById(UPDATE_STORED.humanName()), Statuses.ST_STORED))
        transitions.addTransition(new Transition(Statuses.ST_STORED, operationById(REQUEST_STORED.humanName()), Statuses.ST_REQUESTED))
        transitions.addTransition(new Transition(Statuses.ST_STORED, operationById(DELETE_STORED.humanName()), null))
        transitions.addTransition(new Transition(Statuses.ST_STORED, operationById(DIRECT_DELETE.humanName()), null))
        transitions.addTransition(new Transition(Statuses.ST_REQUESTED, operationById(APPROVE_REQUESTED.humanName()),Statuses.ST_APPROVED))
        transitions.addTransition(new Transition(Statuses.ST_REQUESTED, operationById(QUERY_REQUESTED.humanName()), Statuses.ST_QUERIED))
        transitions.addTransition(new Transition(Statuses.ST_REQUESTED, operationById(REJECT_REQUESTED.humanName()), Statuses.ST_REJECTED))
        transitions.addTransition(new Transition(Statuses.ST_QUERIED, operationById(UPDATE_QUERIED.humanName()), Statuses.ST_QUERIED))
        transitions.addTransition(new Transition(Statuses.ST_QUERIED, operationById(REQUEST_QUERIED.humanName()), Statuses.ST_REQUESTED))
        transitions.addTransition(new Transition(Statuses.ST_QUERIED, operationById(CANCEL_QUERIED.humanName()), Statuses.ST_CANCELED))
        transitions.addTransition(new Transition(Statuses.ST_APPROVED, operationById(CANCEL_APPROVED.humanName()), Statuses.ST_CANCELED))
        transitions.addTransition(new Transition(Statuses.ST_SIGNED, operationById(CANCEL_SIGNED.humanName()), Statuses.ST_CANCELED))
        transitions.addTransition(new Transition(null, operationById(REPLACE.humanName()), Statuses.ST_APPROVED))
        transitions.addTransition(new Transition(null, operationById(REPLACE.humanName()), Statuses.ST_SIGNED))
        transitions.addTransition(new Transition(Statuses.ST_APPROVED, operationById(SIGN_APPROVED.humanName()), Statuses.ST_SIGNED))
        Statuses.ALL_STATUSES_VIEWS.each { k, v ->
            transitions.addTransition(new Transition(v, operationById(Operation.valueOf("VIEW_${k}").humanName()), v))
        }
    }

    private static void createOperations(OperationsContainer operations) {
        [STORE, REQUEST,REPLACE].each {
            if (it == REPLACE) {
                addOperation(operations, it, govAndSeniorRoles)
            }else{
                addOperation(operations, it, traderDeclarantRoles)
            }
        }
    }

    private static void editStoredOperations(OperationsContainer operations) {
        def editStoredOperations = [] as LinkedHashSet
        [UPDATE_STORED, REQUEST_STORED,DELETE_STORED].each {
            addUpdateOperation(editStoredOperations, it, traderDeclarantRoles)
        }
        operations.addOperationClass(new com.webbfontaine.grails.plugins.workflow.operations.OperationClass(OC_EDIT_STORED, EDIT, editStoredOperations))
    }

    private static void editQueriedOperations(OperationsContainer operations) {
        def editQueriedOperations = [] as LinkedHashSet
        [UPDATE_QUERIED, REQUEST_QUERIED, CANCEL_QUERIED].each {
            addUpdateOperation(editQueriedOperations, it, traderDeclarantRoles)
        }
        operations.addOperationClass(new com.webbfontaine.grails.plugins.workflow.operations.OperationClass(OC_EDIT_QUERIED, EDIT, editQueriedOperations))
    }

    private static void editRequestedOperations(OperationsContainer operations) {
        def editRequestedOperations = [] as LinkedHashSet
        [QUERY_REQUESTED, APPROVE_REQUESTED, REJECT_REQUESTED].each {
            if (it == APPROVE_REQUESTED) {
                Map approveRequestedProps = new HashMap()
                addConfirmRequiredProp(APPROVE_REQUESTED, approveRequestedProps)
                operations.addOperation(createApproveOperation(APPROVE_REQUESTED.humanName(), OP_APPROVE, govAndSeniorRoles, approveRequestedProps))
            } else {
                addUpdateOperation(editRequestedOperations, it, govAndSeniorRoles)
            }
        }
        operations.addOperationClass(new com.webbfontaine.grails.plugins.workflow.operations.OperationClass(OC_EDIT_REQUESTED, EDIT, editRequestedOperations))
    }

    private static void editApprovedOperations(OperationsContainer operations) {
        def editProcessesOperations = [] as LinkedHashSet
        [CANCEL_APPROVED,MODIFY_APPROVED,SIGN_APPROVED].each {
            if(it == SIGN_APPROVED){
                addUpdateOperation(editProcessesOperations, it,onlySeniorRoles)
            }else{
                addUpdateOperation(editProcessesOperations, it, govAndSeniorRoles)
            }
        }
        operations.addOperationClass(new com.webbfontaine.grails.plugins.workflow.operations.OperationClass(OC_EDIT_APPROVED, EDIT, editProcessesOperations))
    }

    private static void editSignedOperations(OperationsContainer operations) {
        def editSignedOperations = [] as LinkedHashSet
        [CANCEL_SIGNED,MODIFY_SIGNED].each {
            addUpdateOperation(editSignedOperations, it, onlySeniorRoles)
        }
        operations.addOperationClass(new com.webbfontaine.grails.plugins.workflow.operations.OperationClass(OC_EDIT_SIGNED, EDIT, editSignedOperations))
    }

    private static void directOperations(OperationsContainer operations) {
        Map opDeleteProps = new HashMap()
        opDeleteProps.put(NO_VALIDATION, true)
        opDeleteProps.put(BUTTON_DANGER, true)
        addConfirmRequiredProp(DELETE, opDeleteProps)
        operations.addOperation(createDirectDeleteOperation(DIRECT_DELETE.humanName(), OP_SHOWDELETE, traderDeclarantRoles, opDeleteProps, OA_SHOW_DELETE))
    }

    private static void viewOperation(OperationsContainer operations) {
        def allViews = [VIEW_STORED, VIEW_REQUESTED, VIEW_REJECTED, VIEW_QUERIED, VIEW_CANCELED, VIEW_APPROVED,VIEW_SIGNED,VIEW_DELIVERED, VIEW_REPLACED]
        allViews.each {
            addOperation(operations, it, ALL_ROLES)
        }
    }

    static def getGovAndSeniorRoles() {
        return [Role.GOV_OFFICER.getAuthority(),Role.GOV_SENIOR_OFFICER.getAuthority()]
    }

    static def getOnlySeniorRoles() {
        return [Role.GOV_SENIOR_OFFICER.getAuthority()]
    }


    static def getTraderDeclarantRoles() {
        return [Role.DECLARANT.getAuthority(),Role.TRADER.getAuthority()]
    }

    static def isCreateOperation(String operation) {
        return (operation in CREATE_OPERATIONS)
    }
}