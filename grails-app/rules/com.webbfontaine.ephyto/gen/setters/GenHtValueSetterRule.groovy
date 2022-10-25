package com.webbfontaine.ephyto.gen.setters

import com.webbfontaine.ephyto.setters.HtValueSetterRule

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 07/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class GenHtValueSetterRule extends HtValueSetterRule {

    GenHtValueSetterRule() {
    }

    GenHtValueSetterRule(String codeField, String nameField, Class refDomainClass, boolean useQueryCache = true) {
        super(codeField, nameField, refDomainClass, 'name', useQueryCache)
    }

    @Override
    boolean isFieldEditable(Object domainInstance, String field) {
        return domainInstance.isFieldEditable(field)
    }
}
