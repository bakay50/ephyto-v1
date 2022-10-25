package com.webbfontaine.ephyto.erimm

class PackageCode {
    String id
    String code
    String description


    static constraints = {
        code maxSize: 10 , unique: true
        description maxSize: 20
    }

    static mapping = {
        table('RIMM_PCK_COD')
        id generator: 'assigned', column: 'ID'
        code column: 'COD'
        description column: 'DSC'
        cache usage: 'read-only'
        version(false)
    }


}
