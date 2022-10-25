package com.webbfontaine.ephyto.workflow

import com.webbfontaine.ephyto.constants.Operations
import groovy.transform.CompileStatic
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Copyrights 2002-2018 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 3/14/2018
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
@CompileStatic
enum Operation implements GlobalOperation {

    STORE{
        @Override
        boolean isCreate() {
            return true
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_STORE
        }
    },
    REQUEST{
        @Override
        boolean isCreate() {
            return true
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_REQUEST
        }
    },
    REPLACE{
        @Override
        boolean isCreate() {
            return true
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_REPLACE
        }
    },
    UPDATE_STORED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_UPDATE_STORED
        }
    },
    REQUEST_STORED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_REQUEST_STORED
        }
     },
    UPDATE_QUERIED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_UPDATE_QUERIED
        }
    },
    UPDATE_APPROVED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_UPDATE_APPROVED
        }
    },
    MODIFY_APPROVED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_MODIFY_APPROVED
        }
    },
    MODIFY_SIGNED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_MODIFY_SIGNED
        }
    },
    REQUEST_QUERIED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_REQUEST_QUERIED
        }
    },
    CANCEL_QUERIED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_CANCEL_QUERIED
        }
     },
    QUERY_REQUESTED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_QUERY_REQUESTED
        }
    },
    APPROVE_REQUESTED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_APPROVE_REQUESTED
        }
    },
    SIGN_APPROVED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_SIGN_APPROVED
        }
    },
    REJECT_REQUESTED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_REJECT_REQUESTED
        }
    },
    DIRECT_DELETE{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_DIRECT_DELETE
        }
    },
    DELETE{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_DELETE
        }
    },
    DELETE_STORED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OA_DELETE
        }
    },
    REPLACE_APPROVED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_REPLACE_APPROVED
        }
    },
    REPLACE_SIGNED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_REPLACE_SIGNED
        }
    },
    CANCEL_SIGNED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_CANCEL_SIGNED
        }
    },
    DELIVER_APPROVED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_DELIVER_APPROVED
        }
    },
    CANCEL_APPROVED{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OI_CANCEL_APPROVED
        }
    },
    REGISTER{
        @Override
        boolean isCreate() {
            return true
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_REGISTER
        }
    },
    CANCEL_VALID{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_CANCEL_VALID
        }
    },
    ACTIVATE{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_ACTIVATE
        }
    },
    UPDATE_VALID{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_UPDATE_VALID
        }
    },
    UPDATE_INVALID{
        @Override
        boolean isCreate() {
            return false
        }
        @Override
        boolean isConfirm() {
            return true
        }
        @Override
        String humanName() {
            return Operations.OP_UPDATE_INVALID
        }
    },
    VIEW_STORED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_STORED
        }
    },
    VIEW_REQUESTED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_REQUESTED
        }
    },
    VIEW_REJECTED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_REJECTED
        }
    },
    VIEW_QUERIED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_QUERIED
        }
    },
    VIEW_CANCELED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_CANCELED
        }
    },
    VIEW_APPROVED{
        @Override
        String humanName() {
         return Operations.OI_VIEW_APPROVED
        }
    },
    VIEW_REPLACED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_REPLACED
        }
    },
    VIEW_SIGNED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_SIGNED
        }
    },
    VIEW_DELIVERED{
        @Override
        String humanName() {
            return Operations.OI_VIEW_DELIVERED
        }
    },
    VIEW_VALID{
        @Override
        String humanName() {
            return Operations.OP_VIEW_VALID
        }
    },
    VIEW_INVALID{
        @Override
        String humanName() {
            return Operations.OP_VIEW_INVALID
        }
    }

    @Override
    String getAction() {
        Matcher m = Pattern.compile("^([^_]+)_?").matcher(name())
        if (m.find()) {
            return m.group(1).toLowerCase()
        } else {
            return name().toLowerCase()
        }
    }

    boolean isCreate() {
        return false
    }

    boolean isConfirm() {
        return false
    }

    String humanName() {
        return name()
    }

    boolean isView() {
        return name().startsWith('VIEW')
    }

    boolean isDirect() {
        name().startsWith("DIRECT")
    }

    boolean isHidden(){
        return [].contains(this)
    }

    boolean isAdmin() {
        return false
    }

    static Operation getEnum(String name) {
        for (Operation o : Operation.values()) {
            if (o.name().equals(name))
                return o
        }
        return null
    }
}
