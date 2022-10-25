package com.webbfontaine.ephyto.gen

import org.joda.time.LocalDateTime

import java.nio.charset.StandardCharsets

import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getCommitOperation
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getParams

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 27/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class LoggerService {

    def ephytoSecurityService

    def addTransactionLog(EphytoGen ephytoGen) {
        initLog(ephytoGen)
        ephytoGen.logs?.add(ephytoGen.tempLog)
    }

    def initLog(EphytoGen ephytoGen) {
        if (!ephytoGen.tempLog) {
            initTempLog(ephytoGen)
        }
        ephytoGen.tempLog.date = LocalDateTime.now()
        ephytoGen.tempLog.operation = params.commitOperationName
        ephytoGen.tempLog.endStatus = ephytoGen.status
        ephytoGen.tempLog.operationId = commitOperation
        // improve that if we get time
        if (params.messageUTF) {
            ephytoGen.tempLog.message = URLDecoder.decode(params.messageUTF, StandardCharsets.UTF_8.name())
        } else {
            ephytoGen.tempLog.message = params.message
        }
        if (ephytoGen.tempLog.message) {
            ephytoGen.tempLog.message = ephytoGen.tempLog.message?.trim()?.replaceAll("(\\r\\n|\\n)", "\n"); ;
        }


    }

    def initTempLog(EphytoGen ephytoGen) {
        ephytoGen.tempLog = new EphytoLog()
        ephytoGen.tempLog.messages = new ArrayList<EphytoLogMessage>()
        ephytoGen.tempLog.ephytoGen = ephytoGen
        ephytoGen.tempLog.userLogin = ephytoSecurityService.getUserName()
        ephytoGen.tempLog.user = "${ephytoSecurityService.getUserName()} (${ephytoSecurityService.getCompanyName()})"
    }

    def removeLastLog(EphytoGen ephytoGen) {
        ephytoGen.logs.remove(ephytoGen.logs.last())
    }

    def addSystemLog(EphytoGen ephytoGen) {

    }

    def initSystemLog(EphytoGen ephytoGen) {

    }
}
