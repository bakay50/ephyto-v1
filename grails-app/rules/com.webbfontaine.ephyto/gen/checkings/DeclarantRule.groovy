package com.webbfontaine.ephyto.gen.checkings


import com.webbfontaine.ephyto.BusinessLogicUtils
import com.webbfontaine.ephyto.constants.UserPropertyConstants
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Sidoine BOSSO
 * Date: 22/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class DeclarantRule implements Rule {

    @Override
    void apply(RuleContext ruleContext) {
        checkDeclarantErrors(ruleContext)
    }

    static void checkDeclarantErrors(RuleContext ruleContext){
        List userPropertyValues = BusinessLogicUtils.getUserPropertyValueByBeanAlias('DEC')
        EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)
        if(!(UserPropertyConstants.ALL in userPropertyValues || ephytoGen.declarantCode in userPropertyValues)){
            ruleContext.rejectValue("exporterCode","ephyto.declarantRuleMessage.label","You can not submit an certificate request with the mentioned declarant code.")
        }
    }
}
