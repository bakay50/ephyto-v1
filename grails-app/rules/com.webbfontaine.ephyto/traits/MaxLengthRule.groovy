package com.webbfontaine.ephyto.traits


import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import static grails.util.GrailsNameUtils.getPropertyNameRepresentation


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Carina Garcia.
 * Date: 08/01/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
trait MaxLengthRule<T> implements Rule {

    abstract Map<String, Integer> defineMaxFieldsLength()

    abstract Map<String, String> defineHumanNames()

    abstract Class<T> getType()

    abstract String getRankName()

    public String getHumanName(String key) {
        if (defineHumanNames().containsKey(key)) {
            return defineHumanNames()[key]
        }
        return key
    }


    @Override
    void apply(RuleContext ruleContext) {
        def domainInstance = ruleContext.getTargetAs(getType())
        checkMaxFieldsLength(ruleContext, domainInstance)
    }

    private void checkMaxFieldsLength(ruleContext, T domainInstance) {
        def maxlengthMap = defineMaxFieldsLength()
        maxlengthMap.each { key, value ->
            String valueToCheck = domainInstance[key] instanceof BigDecimal ? domainInstance[key].toString() : domainInstance[key]
            if (valueToCheck?.length() > value) {
                ruleContext.errors.rejectValue(key as String, "error.list.${key}.exceeds.maxlength",[domainInstance[getRankName()]] as Object[], "${getHumanName(getPropertyNameRepresentation(type))} ${domainInstance[getRankName()]} : ${getHumanName(key)} exceeds the maxlength")
            }
        }
    }
}
