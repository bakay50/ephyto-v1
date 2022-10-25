package com.webbfontaine.ephyto.workflow

import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.grails.plugins.workflow.Transition
import com.webbfontaine.grails.plugins.workflow.containers.OperationsContainer
import com.webbfontaine.grails.plugins.workflow.containers.TransitionsContainer
import static com.webbfontaine.ephyto.constants.Operations.OC_EDIT_INVALID
import static com.webbfontaine.ephyto.constants.Operations.OC_EDIT_VALID
import static com.webbfontaine.ephyto.constants.Operations.EDIT
import static com.webbfontaine.ephyto.constants.Operations.OA_SHOW_CANCEL
import static com.webbfontaine.ephyto.constants.Operations.OP_CANCEL_VALID
import static com.webbfontaine.ephyto.workflow.Operation.*
import static com.webbfontaine.grails.plugins.workflow.containers.Buttons.BUTTON_DANGER
import static com.webbfontaine.grails.plugins.workflow.operations.OperationConstants.NO_VALIDATION

class ApplicatorWorkflow extends AbstractWorkflowDefinition{

    private static allOpViews = [VIEW_INVALID, VIEW_VALID]
    private static final CREATE_OPERATIONS = [REGISTER.humanName()]

    @Override
    def defineOperations(OperationsContainer operations) {
        createOperations(operations)
        editValidOperations(operations)
        editInvalidOperations(operations)
        viewOperation(operations)
        directOperations(operations)
        return operations
    }

    @Override
    def defineTransitions(TransitionsContainer transitions) {
        transitions.addTransition(new Transition(null, operationById(REGISTER.humanName()), Statuses.ST_VALID))
        transitions.addTransition(new Transition(Statuses.ST_VALID, operationById(CANCEL_VALID.humanName()), Statuses.ST_INVALID))
        transitions.addTransition(new Transition(Statuses.ST_INVALID, operationById(ACTIVATE.humanName()), Statuses.ST_VALID))
        transitions.addTransition(new Transition(Statuses.ST_VALID, operationById(UPDATE_VALID.humanName()), Statuses.ST_VALID))
        transitions.addTransition(new Transition(Statuses.ST_INVALID, operationById(UPDATE_INVALID.humanName()), Statuses.ST_INVALID))
        Statuses.ALL_STATUSES_VIEWS_APPLICATOR.each { k, v ->
            transitions.addTransition(new Transition(v, operationById(Operation.valueOf("VIEW_${k}").humanName()), v))
        }
    }

    private static void createOperations(OperationsContainer operations) {
        [REGISTER].each {
            addOperation(operations, it, superAdministratorRoles)
        }
    }

    private static void editValidOperations(OperationsContainer operations) {
        def editValidOperations = [] as LinkedHashSet
        [UPDATE_VALID].each {
            addUpdateOperation(editValidOperations, it, superAdministratorRoles)
        }
        operations.addOperationClass(new com.webbfontaine.grails.plugins.workflow.operations.OperationClass(OC_EDIT_VALID, EDIT, editValidOperations))
    }

    private static void editInvalidOperations(OperationsContainer operations) {
        def editInvalidOperations = [] as LinkedHashSet
        [UPDATE_INVALID,ACTIVATE].each {
            addUpdateOperation(editInvalidOperations, it, superAdministratorRoles)
        }
        operations.addOperationClass(new com.webbfontaine.grails.plugins.workflow.operations.OperationClass(OC_EDIT_INVALID, EDIT, editInvalidOperations))
    }

    private static void directOperations(OperationsContainer operations) {
        Map opDeleteProps = new HashMap()
        opDeleteProps.put(NO_VALIDATION, true)
        opDeleteProps.put(BUTTON_DANGER, true)
        addConfirmRequiredProp(CANCEL_VALID, opDeleteProps)
        operations.addOperation(createDirectDeleteOperation(CANCEL_VALID.humanName(), OP_CANCEL_VALID, superAdministratorRoles, opDeleteProps, OA_SHOW_CANCEL))
    }

    private static void viewOperation(OperationsContainer operations) {
        allOpViews.each {
            addOperation(operations, it, superAdministratorRoles)
        }
    }

    static def getSuperAdministratorRoles() {
        return [Role.SUPER_ADMINISTRATOR.getAuthority()]
    }


    static def isCreateOperation(String operation) {
        return (operation in CREATE_OPERATIONS)
    }

}
