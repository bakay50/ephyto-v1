package com.webbfontaine.ephyto.traits

import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext

import static grails.util.GrailsNameUtils.getPropertyNameRepresentation

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 4/27/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

trait DataExistenceRule<T> implements Rule {


    abstract List<T> getList(T)

    abstract String getFieldName()

    abstract Class<T> getType()

    abstract String getRankName()

    @Override
    void apply(RuleContext ruleContext) {
        def domainInstance = ruleContext.getTargetAs(getType())
        checkIfCodeExist(domainInstance, getList(domainInstance))
    }

    private void checkIfCodeExist(T domainInstance, list) {

        def duplicateEntry = list?.find {
            it?."${getFieldName()}" && domainInstance?."${getFieldName()}" && it?."${getFieldName()}" == domainInstance?."${getFieldName()}" && it?."${getRankName()}" != domainInstance?."${getRankName()}"
        }

        if (duplicateEntry) {
            def code = duplicateEntry?."${getFieldName()}"
            def errorList = getList(domainInstance).errors?.allErrors.rejectedValue?.flatten()
            if (!errorList.contains(code)) {
                String fieldName = getFieldName()
                String errorMessage = "${getPropertyNameRepresentation(type)}.errors.${fieldName}.duplicate"
                domainInstance.errors.rejectValue("${fieldName}", errorMessage, [code] as Object[], "${code} already exist.")
            }
        }
    }
}