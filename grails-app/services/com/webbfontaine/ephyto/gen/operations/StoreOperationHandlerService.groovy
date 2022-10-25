package com.webbfontaine.ephyto.gen.operations

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.constants.EphytoConstants
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 20/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class StoreOperationHandlerService extends ClientOperationHandlerService<EphytoGen> {

    @Override
    def beforePersist(EphytoGen domainInstance) {
        initTypeDoc(domainInstance)
    }

    @Override
    def afterPersist(EphytoGen domainClass, EphytoGen result, boolean hasErrors) {
        super.afterPersist(domainClass, result, hasErrors)
        if (hasErrors) {
            resetOwner(result)
        }
    }


    private static initTypeDoc(EphytoGen result) {
        result.documentType = EphytoConstants.TYPE_DOCSCERTIFICAT
    }

    private static resetOwner(EphytoGen result) {
        result.ownerUser = null
        result.ownerGroup = null
    }
}
