package com.webbfontaine.ephyto.gen.operations

import com.webbfontaine.ephyto.gen.EphytoGen
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 21/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class ApproveOperationHandlerService extends ClientOperationHandlerService<EphytoGen> {

    def initFieldsValueInUTF8Service

    @Override
    def beforePersist(EphytoGen domainInstance) {
        checkMandatoryFieldsForApproval(domainInstance)
    }

    def updateFieldsForUTF8(EphytoGen domainInstance, params) {
        initFieldsValueInUTF8Service.initFieldsValueInUTF8(domainInstance, params)
    }

    // improve that if we get time
    def checkMandatoryFieldsForApproval(EphytoGen domainInstance) {
        if (domainInstance?.filingDate && domainInstance?.phytosanitaryCertificateDate && domainInstance?.phytosanitaryCertificateRef) {
            updateFieldsForUTF8(domainInstance, ((GrailsWebRequest) RequestContextHolder.currentRequestAttributes()).getParams())
        } else {
            if (!domainInstance.filingDate) {
                domainInstance.errors.rejectValue("filingDate", "ephytoGen.errors.fields.filingDatemandatory")
            }
            if (!domainInstance.phytosanitaryCertificateDate) {
                domainInstance.errors.rejectValue("phytosanitaryCertificateDate", "ephytoGen.errors.fields.phytosanitaryDatemandatory")
            }
            if (!domainInstance.phytosanitaryCertificateRef) {
                domainInstance.errors.rejectValue("phytosanitaryCertificateRef", "ephytoGen.errors.fields.phytosanitaryRefmandatory")
            }
        }
    }

    @Override
    def afterPersist(EphytoGen domainClass, EphytoGen result, boolean hasErrors) {
        super.afterPersist(domainClass, result, hasErrors)
    }


}
