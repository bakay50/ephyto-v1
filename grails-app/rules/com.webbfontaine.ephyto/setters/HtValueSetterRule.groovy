package com.webbfontaine.ephyto.setters
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 31/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class HtValueSetterRule extends ValueSetterRule {

    HtValueSetterRule() {
    }

    HtValueSetterRule(String codeField, String nameField, Class refDomainClass, boolean useQueryCache = true) {
        this(codeField, nameField, refDomainClass, 'name', useQueryCache)
    }

    HtValueSetterRule(String codeField, String nameField, Class domainClass, String htRefNameField, boolean useQueryCache = true) {
        this.codeField = codeField
        this.nameField = nameField
        this.domainClass = domainClass
        this.htRefNameField = htRefNameField
        this.useQueryCache = useQueryCache
    }

    @Override
    boolean isFieldEditable(Object domainInstance, String field) {
        return true // todo: implement this (aapor)
    }

    @Override
    boolean isHt() {
        return true
    }
}
