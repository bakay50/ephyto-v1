package com.webbfontaine.ephyto.gen.treatment.checkings

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 23/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class CheckTreatmentApplicatorRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTreatmentApplicatorRule.class);

    @Override
    void apply(RuleContext ruleContext) {
        checkApplicatorAndDate(ruleContext)
    }

    static void checkApplicatorAndDate(RuleContext ruleContext) {

        EphytoTreatment ephytoTreatment = ruleContext.getTargetAs(EphytoTreatment)
        EphytoGen ephytoGenInstance = ephytoTreatment?.ephytoGen
        def params = ((GrailsWebRequest) RequestContextHolder.currentRequestAttributes()).getParams()
        def hasError
        LOGGER.info("params checkApplicatorAndDate :${params}")
        if (ephytoGenInstance?.itemTreatments?.size() > 0) {
            if (params.action == "updateTreatment") {
                def duplicateEntry = ephytoGenInstance?.itemTreatments?.find {
                    it.applicatorCode == ephytoTreatment.applicatorCode && it?.disinfectionCertificateRef == ephytoTreatment?.disinfectionCertificateRef && it?.itemNumber != ephytoTreatment?.itemNumber && it?.disinfectionCertificateRef
                }
                if (duplicateEntry) {
                    hasError = true
                }
            } else {
                for(EphytoTreatment treat : ephytoGenInstance.getItemTreatments()){
                    if(treat?.applicatorCode && treat?.treatmentSartDate && treat?.treatmentEndDate && ephytoTreatment?.applicatorCode && ephytoTreatment?.treatmentSartDate && ephytoTreatment?.treatmentEndDate){
                      if(treat?.disinfectionCertificateRef && treat?.applicatorCode == ephytoTreatment?.applicatorCode && treat?.disinfectionCertificateRef == ephytoTreatment?.disinfectionCertificateRef && ephytoTreatment?.itemNumber > treat?.itemNumber ){
                          hasError = true
                      }
                    }
                }
            }
            if (hasError) {
                ephytoTreatment.errors.rejectValue("applicatorCode", "ephytoGen.errors.applicatorCode.existe", [ephytoTreatment?.disinfectionCertificateRef] as Object[], "this Disinfection Certificate Reference : ${ephytoTreatment?.disinfectionCertificateRef} already exist")
            }
        }

    }
}