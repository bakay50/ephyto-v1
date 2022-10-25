package com.webbfontaine.ephyto.gen.checkings


import com.webbfontaine.ephyto.BusinessLogicUtils
import com.webbfontaine.ephyto.constants.UserPropertyConstants
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
class ExporterRule implements Rule {

    @Override
    void apply(RuleContext ruleContext) {
        checkExporterErrors(ruleContext)
    }

    static void checkExporterErrors(RuleContext ruleContext){
        List userPropertyValues = BusinessLogicUtils.getUserPropertyValueByBeanAlias('TIN')
        EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)
        if(!(UserPropertyConstants.ALL in userPropertyValues || ephytoGen.exporterCode in userPropertyValues)){
            ruleContext.rejectValue("exporterCode","ephyto.exporterRuleMessage.label","You can not submit an certificate request for the mentioned exporter.")
        }
    }
}
