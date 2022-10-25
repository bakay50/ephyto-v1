package com.webbfontaine.ephyto.gen.operations

import com.webbfontaine.ephyto.gen.EphytoGen

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 21/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class CancelOperationHandlerService extends ClientOperationHandlerService<EphytoGen> {

    @Override
    def afterPersist(EphytoGen domainClass, EphytoGen result, boolean hasErrors) {
        super.afterPersist(domainClass, result, hasErrors)
    }

}
