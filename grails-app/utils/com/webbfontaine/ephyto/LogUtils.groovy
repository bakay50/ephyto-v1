package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.gen.EphytoLog
import com.webbfontaine.ephyto.gen.EphytoGen

class LogUtils {

    static boolean dateTitleRequired(EphytoLog log) {
        SortedSet<EphytoLog> logs = log.ephytoGen.logs
        def previous = null
        boolean result = true
        if (logs.isEmpty() || logs.contains(log)) {
            logs.each {
                if (log.date.equals(it.date) && previous != null) {
                    result = (log.date.toLocalDate() != previous.date.toLocalDate())
                    return
                }
                previous = it
            }
        } else {
            result = (log.date.toLocalDate() != logs.last().date.toLocalDate())
        }

        return result
    }

    static EphytoLog getLastOperationLog(EphytoGen ephytoGen) {
        def operations = ephytoGen.logs.findAll { it.endStatus }
        def lastOperationLog = null
        if (operations) {
            lastOperationLog = operations.last()
        }
        return lastOperationLog
    }
}
