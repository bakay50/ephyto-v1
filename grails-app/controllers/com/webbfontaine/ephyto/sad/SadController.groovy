package com.webbfontaine.ephyto.sad

import grails.plugin.springsecurity.annotation.Secured


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Y. SYLLA
 * Date: 10/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@Secured(['IS_AUTHENTICATED_FULLY'])
class SadController {

    def sadService

    def checkIfSadExists(){
        def result = sadService.checkIfSadExists(params?.customsClearanceOfficeCode?.toString(), params?.declarationSerial?.toString(), params?.declarationNumber?.toString(), params?.declarationDate?.toString(), params?.exporterCode?.toString(), request)
        render result
    }
}
