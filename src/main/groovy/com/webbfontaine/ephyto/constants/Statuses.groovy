/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webbfontaine.ephyto.constants

import static com.webbfontaine.ephyto.workflow.Status.*
import static com.webbfontaine.ephyto.workflow.Operation.*
import static com.webbfontaine.ephyto.constants.Operations.*

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Y. SYLLA.
 * Date: 15/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class Statuses {
    static final String ST_STORED = "Stored"
    static final String ST_REQUESTED = "Requested"
    static final String ST_REJECTED = "Rejected"
    static final String ST_QUERIED = "Queried"
    static final String ST_CANCELED = "Canceled"
    static final String ST_APPROVED = "Approved"
    static final String ST_DELIVERED = "Delivered"
    static final String ST_REPLACED = "Replaced"
    static final String ST_SIGNED = "Signed"
    static final String ST_VALID = "Valid"
    static final String ST_INVALID = "Invalid"



    public static final List<String> PERMITTED_STATUSES_FOR_PRINT = [ST_APPROVED,ST_SIGNED]

    public static final List<String> PERMITTED_STATUSES_FOR_DRAFT_PRINT = [ST_STORED,ST_REQUESTED]

    public static final List<String> PERMITTED_STATUSES_FOR_QUERY_MSG = [ST_REQUESTED, ST_QUERIED]


    public static
    final List<String> ALL_STATUSES = [ST_STORED, ST_REQUESTED, ST_REJECTED, ST_QUERIED, ST_CANCELED, ST_APPROVED, ST_DELIVERED,ST_SIGNED, ST_REPLACED]

    public static final ALL_STATUSES_VIEWS = [
            (STORED)            : ST_STORED,
            (REQUESTED)         : ST_REQUESTED,
            (REJECTED)          : ST_REJECTED,
            (QUERIED)           : ST_QUERIED,
            (CANCELED)          : ST_CANCELED,
            (APPROVED)          : ST_APPROVED,
            (REPLACED)          : ST_REPLACED,
            (DELIVERED)         : ST_DELIVERED,
            (SIGNED)            : ST_SIGNED
    ]

    public static final ALL_STATUSES_VIEWS_APPLICATOR = [
            (VALID)             : ST_VALID,
            (INVALID)           : ST_INVALID
    ]

    public static final ALL_OERATIONS_VIEWS = [
            (STORE)                                : OP_STORE,
            (REQUEST)                              : OP_REQUEST,
            (REPLACE)                              : OP_REPLACE,
            (UPDATE_STORED.humanName())            : OP_UPDATE,
            (REQUEST_STORED.humanName())           : OP_REQUEST,
            (QUERY_REQUESTED.humanName())          : OP_QUERY,
            (REJECT_REQUESTED.humanName())         : OP_REJECT,
            (UPDATE_QUERIED.humanName())           : OP_UPDATE,
            (CANCEL_QUERIED.humanName())           : OP_CANCEL,
            (REQUEST_QUERIED.humanName())          : OP_REQUEST,
            (CANCEL_APPROVED.humanName())          : OP_CANCEL,
            (REPLACE_APPROVED.humanName())         : OP_REPLACE,
            (UPDATE_APPROVED.humanName())          : OP_UPDATE,
            (MODIFY_APPROVED.humanName())          : OP_MODIFY,
            (MODIFY_SIGNED.humanName())            : OP_MODIFY,
            (APPROVE_REQUESTED.humanName())        : OP_APPROVE,
            (SIGN_APPROVED.humanName())            : OP_SIGN,
            (DELETE_STORED.humanName())            : OP_DELETE,
            (REPLACE_SIGNED.humanName())           : OP_REPLACE,
            (CANCEL_SIGNED.humanName())            : OP_CANCEL,
            (DELIVER_APPROVED.humanName())         : OP_DELIVER,
     ]

}
