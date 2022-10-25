package com.webbfontaine.ephyto.gen

import org.joda.time.LocalDateTime
import grails.util.Holders
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 23/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoLog implements Comparable {


    String userLogin
    String user
    LocalDateTime date
    String message
    String operation
    String operationId
    String endStatus
    List<EphytoLogMessage> messages

    static belongsTo = [ephytoGen: EphytoGen]

    static hasMany = [messages: EphytoLogMessage]

    static transients = ['operationId']

    static skipValidation = ['messages']

    static constraints = {
        ephytoGen(nullable: false)
        userLogin(nullable: false, maxSize: 128)
        user(nullable: false, maxSize: 128)
        date(nullable: false)
        message(nullable: true, maxSize: 1024)
        operation(nullable: true, maxSize: 128)
        endStatus(nullable: true, maxSize: 32)

    }

    static mapping = {
        table 'EPHYTO_GEN_LOG'
        messages cascade: 'all-delete-orphan', lazy: false

        ephytoGen column: 'INSTANCE_ID'
        userLogin column: 'USR_LOGIN', length: 128
        user column: 'USR', length: 128
        date column: 'DAT'
        message column: 'MSG', length: 1024
        operation column: 'OPR', length: 128
        endStatus column: 'END_STA', length: 32

        version false

    }

    @Override
    int compareTo(Object o) {
        if (date.isAfter(o.date)) {
            return 1
        }
        else if ((date.isBefore(o.date))) {
            return -1
        }
        else {
            return 0
        }
    }

    def String operationHumanName() {
        operation ? ephytoGenBpmService.getOperationName(ephytoGen, operation) ?: operation : null
    }

    static def getEphytoGenBpmService(){
        Holders.applicationContext.getBean("ephytoGenBpmService")
    }

    @Override
    public String toString() {
        return "EphytoLog{" +
                "EphytoGen=" + ephytoGen +
                ", userLogin='" + userLogin + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", operation='" + operation + '\'' +
                ", endStatus='" + endStatus + '\'' +
                '}';
    }
}
