package com.webbfontaine.ephyto.transactions

import com.webbfontaine.ephyto.gen.EphytoLog
import com.webbfontaine.grails.plugins.workflow.TransactionRecord

class TransactionRecordCreator {


    public static TransactionRecord create(EphytoLog ephytoLog) {
        new TransactionRecord(documentId: ephytoLog.ephytoGen.id,
                initialStatus: ephytoLog.ephytoGen.initialStatus,
                operation: ephytoLog.operationId ?: ephytoLog.operation,
                endStatus: ephytoLog.ephytoGen.status,
                operationDate: ephytoLog.date,
                userLogin: ephytoLog.userLogin
        )
    }
}
