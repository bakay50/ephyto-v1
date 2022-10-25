package com.webbfontaine.ephyto

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 17/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class IdGenerator {


    Long ephytoGen
    Long itemGood
    Long ephytoAtt
    Long ephytoTreatment

    static mapping = {
        ephytoGen column: 'CERTIFICATE_GENERAL'
        itemGood column: 'CERTIFICATE_GOODS'
        ephytoAtt column: 'CERTIFICATE_ATTACHMENTS'
        ephytoTreatment column: 'CERTIFICATE_TREATMENT'
        table 'EPHYTO_ID_GENERATOR'
        version false
    }
}
