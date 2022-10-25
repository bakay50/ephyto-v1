package com.webbfontaine.ephyto.gen.operations

import com.webbfontaine.ephyto.gen.EphytoGen
import grails.gorm.transactions.Transactional
import org.joda.time.LocalDate

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Yacouba SYLLA
 * Date: 20/11/2017
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@Transactional
class SignOperationHandlerService extends ClientOperationHandlerService<EphytoGen> {

    @Override
    def beforePersist(EphytoGen domainInstance) {
        domainInstance.signatureDate = LocalDate.now()

    }

    @Override
    def afterPersist(EphytoGen domainClass, EphytoGen result, boolean hasErrors) {
        super.afterPersist(domainClass, result, hasErrors)
    }


}
