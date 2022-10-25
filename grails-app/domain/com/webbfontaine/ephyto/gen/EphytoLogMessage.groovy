package com.webbfontaine.ephyto.gen

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Yacouba SYLLA
 * Date: 14/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoLogMessage {

    static belongsTo = [ephytoLog: EphytoLog]

    String code
    String message
    EphytoLog ephytoLog

    static constraints = {
        ephytoLog(nullable: false)
        code maxSize: 5
        message maxSize: 1024
    }
    static mapping = {
        ephytoLog column: 'LOG'
        table 'EPHYTO_LOG_MSG'
        code column: 'COD', length: 5
        message column: 'MSG', length: 1024
        version false
    }
}
