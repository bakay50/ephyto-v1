package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.gen.EphytoGen
import grails.util.Holders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.i18n.LocaleContextHolder
import static com.webbfontaine.ephyto.constants.Statuses.ST_REQUESTED

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Carina Garcia
 * Date: 3/17/15
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class MessageSourceUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSourceUtils.class);

    public static String getMessage(String messageCode) {
        getMessageValue(messageCode, null)
    }

    public static String getMessage(String messageCode, Object[] args) {
        getMessageValue(messageCode, args)
    }

    public static createOperationDoneMessage(operationName) {
        try {
            def operationDone = getMessage("${operationName}.operation.done", null)
            return getMessageValue("default.operation.done.message", [operationDone].toArray())
        } catch (Exception ex) {
            LOGGER.error("", ex)
            return "${operationName} operation is done"
        }
    }

    public static createOperationDoneMessage(operationName, EphytoGen ephytoGen) {
        try {
            if(ephytoGen?.status == ST_REQUESTED){
                getMessage("${operationName}.operation.done", [ephytoGen.requestNumber, TypeCastUtils.fromDate(ephytoGen.requestDate)] as Object[])
            }else{
                def operationDone = getMessage("${operationName}.operation.done", null)
                return getMessageValue("default.operation.done.message", [operationDone].toArray())
            }

        } catch (Exception ex) {
            LOGGER.error("", ex)
            return "${operationName} operation is done"
        }
    }

    public static createOperationDoneMessageApplicator(operationName, Applicator applicator) {
        try {
            getMessage("${operationName}.operation.done", [applicator.id])
        } catch (IllegalArgumentException ex) {
            LOGGER.error("", ex)
            return "${operationName} operation is done"
        }
    }

    public static OperationDoneMessage(operationName) {
        try {
            def operationDone = getMessage("${operationName}.operations.done", null)
            return getMessageValue("default.operation.done.message", [operationDone].toArray())
        } catch (Exception ex) {
            LOGGER.error("", ex)
            return "${operationName} operation is done"
        }
    }


    public static createOperationDoneMessageModel(operationName) {
        return [title: getMessage("exemptionGen.operation.${operationName}.done"),
                message: createOperationDoneMessage(operationName)]
    }


    public static createOperationDoneMessageModel(operationName, EphytoGen ephytoGen) {
        return [title  : getMessage("ephytoGen.operation.done"),
                message: createOperationDoneMessage(operationName, ephytoGen)]
    }

    public static createOperationDoneMessageModelApplicator(operationName, Applicator applicator) {
        return [title  : getMessage("ephytoGen.operation.done"),
                message: createOperationDoneMessageApplicator(operationName, applicator)]
    }

    public static createOperationsDoneMessageModel(operation) {
        return [title: getMessage("ephytoGen.operation.done"),
                message: OperationDoneMessage(operation)]

    }

    private static getMessageSource() {
        Holders.applicationContext.messageSource
    }

    private static getMessageValue(String code, Object[] args) {
        messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
    }
}
