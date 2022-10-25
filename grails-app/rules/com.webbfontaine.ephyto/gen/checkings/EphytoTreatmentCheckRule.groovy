package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Alvin Goya
 * Date: 8/12/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoTreatmentCheckRule implements Rule{
    @Override
    void apply(RuleContext ruleContext) {
        checkIfTreatmentExist(ruleContext)
    }

    private static void checkIfTreatmentExist(RuleContext ruleContext){
        EphytoGen ephytoGen = ruleContext?.getTargetAs(EphytoGen)
        if(ephytoGen && !(ephytoGen?.itemTreatments)){
                ruleContext.reject("ephytoGen.itemTreatments.inexistent",null, "There is no treatment in the list.")
        }
    }
}
