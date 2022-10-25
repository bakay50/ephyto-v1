package com.webbfontaine.ephyto.gen.treatment.checkings

import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.ephyto.traits.MaxLengthRule

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 08/01/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoTreatmentMaxLengthRule implements MaxLengthRule<EphytoTreatment> {

    def conversationService


    def maxlengthMap = ["warehouse"                   :35,  "applicatorCode"            :10,   "applicatorAgreement"           :20,
                        "disinfectionCertificateRef"              : 1000,  "treatmentType"                     :35,     "usedProducts"         :35,
                        "concentration"                    :35
    ]


    def displayName = ["warehouse" : "warehouse", "applicatorCode": "applicator Code",
                       "applicatorAgreement" : "applicator Agreement", "applicatorNameAddress" : "applicator Name and Address",
                       "disinfectionCertificateRef" : "disinfection Certificate Reference","disinfectionCertificateDate" : "disinfection Certificate Date", "treatmentType" : "treatment Type", "treatmentSartDate" : "treatment Sart Date",
                       "treatmentSartTime": "treatment Sart Time", "treatmentEndDate" : "treatment End Date","treatmentEndTime":"treatment End Time","usedProducts":"used Products","concentration":"concentration","treatmentDuration":"treatmentDuration"
    ]


    @Override
    Map<String, String> defineHumanNames() {
        return displayName
    }

    @Override
    Map<String, Integer> defineMaxFieldsLength() {
        return maxlengthMap
    }

    @Override
    Class<EphytoTreatment> getType() {
        return EphytoTreatment.class
    }

    @Override
    String getRankName(){
        return "itemNumber"
    }



}
