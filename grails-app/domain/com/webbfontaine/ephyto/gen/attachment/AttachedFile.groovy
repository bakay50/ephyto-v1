package com.webbfontaine.ephyto.gen.attachment

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 23/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class AttachedFile {
    byte[] data

    static constraints = {
        data nullable: true
        attachment nullable: true
    }

    static belongsTo = [attachment: Attachment]

    static mapping = {
        version false
        data column: 'ATT_FIL', sqlType: 'BLOB'
    }
}
