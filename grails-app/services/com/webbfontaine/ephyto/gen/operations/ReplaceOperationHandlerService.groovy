package com.webbfontaine.ephyto.gen.operations

import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.gen.EphytoGen
import grails.gorm.transactions.Transactional

@Transactional
class ReplaceOperationHandlerService extends ClientOperationHandlerService<EphytoGen> {

    @Override
    protected updateStatus(EphytoGen domainInstance, String commitOperation) {
        domainInstance.status  = Statuses.ST_REPLACED
    }
}
