package com.webbfontaine.ephyto.gen.checkings


import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Sidoine BOSSO
 * Date: 07/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class GoodValidationRule implements Rule {

    @Override
    void apply(RuleContext ruleContext) {
        checkGoodValidationErrors(ruleContext)
    }

    static void checkGoodValidationErrors(RuleContext ruleContext){
        EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)
        if(!(ephytoGen?.totalItems>0)){
            ruleContext.rejectValue("totalItems","ephyto.goodValidationRuleMessage.label","No item filled.")
        }
    }
}
