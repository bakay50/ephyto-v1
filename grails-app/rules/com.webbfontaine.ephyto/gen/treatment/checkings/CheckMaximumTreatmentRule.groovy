package com.webbfontaine.ephyto.gen.treatment.checkings

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.util.Holders
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder

import static com.webbfontaine.ephyto.TypeCastUtils.toInteger;

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 23/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class CheckMaximumTreatmentRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckMaximumTreatmentRule.class);

    @Override
    void apply(RuleContext ruleContext) {
        checkMaxTreatment(ruleContext)
    }

    static void checkMaxTreatment(RuleContext ruleContext) {
        EphytoTreatment ephytoTreatment = ruleContext.getTargetAs(EphytoTreatment)
        def params = ((GrailsWebRequest) RequestContextHolder.currentRequestAttributes()).getParams()
        LOGGER.info("params CheckMaximumTreatmentRule:${params}")
        EphytoGen ephytoGenInstance = ephytoTreatment?.ephytoGen
        LOGGER.info("Size of  treatment :${ephytoGenInstance?.itemTreatments?.size()}")
        LOGGER.info("ItemNumber :${params.itemNumber}")
        if (Holders.config.treatment.enabled == true) {
            if(params.action == "saveTreatment"){
                if (params?.itemNumber && toInteger(params?.itemNumber) > Holders.config.treatment.maxTreatmentConfig) {
                    ephytoTreatment.errors.rejectValue("applicatorCode", "ephytoGen.errors.treatment.maxconfig", [Holders.config.treatment.maxTreatmentConfig] as Object[], "Vous avez atteint le nombre maximun de traitement(${Holders.config.treatment.maxTreatmentConfig})")
                }
            }
        }

    }
}
