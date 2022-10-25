package com.webbfontaine.ephyto.workflow

import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.grails.plugins.workflow.WorkflowDefinition
import com.webbfontaine.grails.plugins.workflow.containers.OperationsContainer
import com.webbfontaine.grails.plugins.workflow.operations.DirectOperation
import com.webbfontaine.grails.plugins.workflow.operations.DirectSubmitOperation
import com.webbfontaine.grails.plugins.workflow.operations.UpdateOperation
import com.webbfontaine.grails.plugins.workflow.operations.ViewOperation

import static com.webbfontaine.ephyto.constants.Operations.OA_DELETE
import static com.webbfontaine.ephyto.workflow.Operation.*
import static com.webbfontaine.grails.plugins.workflow.containers.Buttons.*
import static com.webbfontaine.grails.plugins.workflow.operations.OperationConstants.*

/**
 * Copyrights 2002-2018 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 3/14/2018
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
abstract class AbstractWorkflowDefinition extends WorkflowDefinition {

    private static final APPROVE_ICON = "glyphicon glyphicon-ok"
    private static final DELETE_ICON = "glyphicon glyphicon-trash"
    private static final APPROVE_ACTION = "approve"

    protected static addOperation(OperationsContainer operations, Operation op, Collection roles) {
        def out = operations.operationById(op.name())
        if (out) {
            out.roles.addAll(roles)
        } else {
            def props = [:]
            def operation
            addConfirmRequiredProp(op, props)
            addHiddenProp(op, props)
            if (op.view) {
                operation = new ViewOperation(op.humanName(), OP_VIEW, roles)
            } else if (op.direct) {
                operation = new DirectOperation(op.name(), roles, props)
            } else {
                operation = createUpdateOperation(op, roles, props)
            }
            operations.addOperation(operation)
        }
    }

    static def addConfirmRequiredProp(Operation operation, Map operationProps) {
        if (operation.confirm) {
            operationProps.put(CONFIRMATION_REQUIRED, true)
        }
    }

    static def addHiddenProp(Operation operation, Map operationProperties) {
        if (operation.hidden) {
            operationProperties.put(HIDDEN_OPERATION, true)
        }
    }

    protected static UpdateOperation createUpdateOperation(Operation op, roles, props) {
        UpdateOperation operation
        def operations = [STORE.name(), REQUEST.name(), REPLACE.name(), REGISTER.name(), UPDATE_VALID.name(), UPDATE_INVALID.name(), ACTIVATE.name()]
        if (operations.contains(op.name())) {
            if (op.name() == REPLACE.name()) {
                props.put(BUTTON_SUCCESS, true)
            }
            operation = new UpdateOperation(op.humanName(), roles, props)
        } else {
            if (op.name() in [DELETE_STORED.name()]) {
                operation = new UpdateOperation(OA_DELETE, Statuses.ALL_OERATIONS_VIEWS[op.humanName()], roles, props)
            } else {
                operation = new UpdateOperation(op.humanName(), Statuses.ALL_OERATIONS_VIEWS[op.humanName()], roles, props)
            }
        }
        return operation
    }


    protected final operationById(Operation op) {
        return super.operationById(op.name())
    }

    protected static addUpdateOperation(operations, Operation op, roles, Map params = Collections.emptyMap()) {
        Map props = new HashMap()
        props.putAll(params)
        def deleteOperation = [DELETE_STORED.name()]
        def cancelOperation = [CANCEL_QUERIED.name()]
        def rejectOperation = [REJECT_REQUESTED.name(), CANCEL_APPROVED.name(), CANCEL_SIGNED.name()]
        if (cancelOperation.contains(op.name()) || deleteOperation.contains(op.name())) {
            props.put(NO_VALIDATION, true)
            props.put(BUTTON_DANGER, true)
        } else if (rejectOperation.contains(op.name())) {
            props.put(BUTTON_DANGER, true)
        } else if ([MODIFY_SIGNED.name(), MODIFY_APPROVED.name(), QUERY_REQUESTED.name(), UPDATE_QUERIED.name(), UPDATE_STORED.name(), UPDATE_VALID.name(), UPDATE_INVALID.name()].contains(op.name())) {
            props.put(BUTTON_WARNING, true)
        }
        addConfirmRequiredProp(op, props)
        def oper = createUpdateOperation(op, roles, props)
        operations.add(oper)
    }

    protected
    static DirectSubmitOperation createApproveOperation(String id, String name, Collection<String> roles, Map properties) {
        DirectSubmitOperation operation = new DirectSubmitOperation(id, name, roles)
        operation.action = APPROVE_ACTION
        operation.icon = APPROVE_ICON
        return operation
    }

    static DirectSubmitOperation createDirectDeleteOperation(String id, String name, Collection<String> roles, Map properties, String executeoperation) {
        DirectSubmitOperation operation = new DirectSubmitOperation(id, name, roles)
        operation.action = executeoperation
        operation.icon = DELETE_ICON
        return operation
    }
}
