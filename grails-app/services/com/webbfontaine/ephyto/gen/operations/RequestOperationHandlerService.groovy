package com.webbfontaine.ephyto.gen.operations

import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.serial.SequenceNumberService
import org.joda.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 21/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class RequestOperationHandlerService extends ClientOperationHandlerService<EphytoGen> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestOperationHandlerService.class);

    static transactional = false

    public static final String REQUEST_NUMBER_PREFIX = "PTD"
    SequenceNumberService sequenceNumberService


    @Override
    def beforePersist(EphytoGen domainInstance) {

        if (isFirstRequest(domainInstance)) {
            domainInstance.requestDate = LocalDate.now()
            def year = domainInstance.requestDate.getYear()
            //domainInstance.requestNumberSequence = sequenceGenerator.nextRequestNumber(year)
            sequenceNumberService.nextRequestNumber(domainInstance)

            LOGGER.info("Current Sequence Number of Year $year is " + domainInstance?.requestNumberSequence)
            domainInstance.requestYear = year
            if (!domainInstance.documentType) {
                initTypeDoc(domainInstance)
            }
        }

    }

    @Override
    def afterPersist(EphytoGen domainClass, EphytoGen result, boolean hasErrors) {
        super.afterPersist(domainClass, result, hasErrors)
        if (hasErrors) {
            if (isCreate()) {
                resetOwner(result)
            }
            resetFields(result)
        }
    }


    private static resetFields(EphytoGen ephytoGen) {
        ephytoGen.status = null
        ephytoGen.requestDate = null
        ephytoGen.requestNumber = null
        ephytoGen.requestNumberSequence = null
        ephytoGen.requestYear = null
    }

    private static resetOwner(EphytoGen result) {
        result?.ownerUser = null
        result?.ownerGroup = null
    }

    private static initTypeDoc(EphytoGen result) {
        result.documentType = EphytoConstants.TYPE_DOCSCERTIFICAT
    }

    private static boolean isFirstRequest(EphytoGen ephytoGen) {
        return !(ephytoGen.requestNumber)
    }
}
