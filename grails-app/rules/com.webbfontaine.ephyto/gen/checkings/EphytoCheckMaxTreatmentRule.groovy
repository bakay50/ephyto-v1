package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.util.Holders
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 8/12/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoCheckMaxTreatmentRule implements Rule{
    @Override
    void apply(RuleContext ruleContext) {
        checkMaxTreatment(ruleContext)
    }

    private static void checkMaxTreatment(RuleContext ruleContext){
        EphytoGen ephytoGen = ruleContext?.getTargetAs(EphytoGen)
        if(ephytoGen && ephytoGen?.itemTreatments?.size() > Holders.config.treatment.maxTreatmentConfig){
            ruleContext.rejectValue("itemTreatments", "ephytoGen.errors.treatment.maxconfig", [Holders.config.treatment.maxTreatmentConfig] as Object[], "Vous avez atteint le nombre maximun de traitement(${Holders.config.treatment.maxTreatmentConfig})")
        }
    }
}
