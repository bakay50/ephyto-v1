package com.webbfontaine.ephyto.gen.treatment.checkings

import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import java.sql.Timestamp;

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 23/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class CheckTreatmentDateRule implements Rule {

    @Override
    void apply(RuleContext ruleContext) {
        checkDate(ruleContext)
    }

    static void checkDate(RuleContext ruleContext) {
        EphytoTreatment ephytoTreatment = ruleContext.getTargetAs(EphytoTreatment)
        // get start et time and convert to date
        if (ephytoTreatment?.treatmentSartDate && ephytoTreatment?.treatmentEndDate) {
            String strStart = concatenateFields(ephytoTreatment?.treatmentSartDate,ephytoTreatment?.treatmentSartTime)
            String strEnd = concatenateFields(ephytoTreatment?.treatmentEndDate,ephytoTreatment?.treatmentEndTime)
            if (!isLessThan(convertFieldsToTimeStamp(strStart),convertFieldsToTimeStamp(strEnd))) {
                ephytoTreatment.errors.rejectValue("treatmentSartDate", "ephytoGen.errors.treatmentSartDate", [ephytoTreatment?.itemNumber] as Object[], "Traitement ${ephytoTreatment?.itemNumber} : The end of the treatment can not be prior to the beginning of treatment")
            }
        }

    }


    private static def concatenateFields(def dateTo, def timeTo) {
        return "${dateTo} ${timeTo == null ?  "00:00:00" : timeTo}"
    }

    private static Timestamp convertFieldsToTimeStamp(def stringDate){
        return Timestamp.valueOf(stringDate)
    }

    private static boolean isLessThan(Timestamp startDate, Timestamp endDate) {
        startDate?.before(endDate)
    }
}
