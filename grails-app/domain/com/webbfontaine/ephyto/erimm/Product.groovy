package com.webbfontaine.ephyto.erimm

class Product {
    String id
    String code
    String name
    String botanicalName
    Integer defaultQuantity
    Integer defaultNetWeight
    Integer defaultGrossWeight

    static constraints = {
        code maxSize: 30 , unique: true
        name maxSize: 100
        botanicalName maxSize: 100
    }

    static mapping = {
        table('RIMM_PDT')
        id generator: 'assigned', column: 'ID'
        code column: 'CODE'
        name column: 'NAME'
        botanicalName column: 'BOT_NAM'
        defaultQuantity column: 'DEF_QTY'
        defaultNetWeight column: 'DEF_NET_WGT'
        defaultGrossWeight column: 'DEF_GRS_WGT'
        version(false)
        cache usage: 'read-only'
    }
}
