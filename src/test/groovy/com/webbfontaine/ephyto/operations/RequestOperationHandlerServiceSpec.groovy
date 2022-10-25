package com.webbfontaine.ephyto.operations

import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.operations.RequestOperationHandlerService
import com.webbfontaine.ephyto.gen.serial.RequestSerial
import com.webbfontaine.ephyto.sequence.SequenceGenerator
import com.webbfontaine.ephyto.serial.SequenceNumberService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.LocalDate
import spock.lang.Specification

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 21/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@TestFor(RequestOperationHandlerService)
@Mock([EphytoGen,RequestSerial])
class RequestOperationHandlerServiceSpec extends Specification {

    SequenceNumberService sequenceNumberService = new SequenceNumberService()

    def setup() {


        service.metaClass.generateRequestNumber = { EphytoGen ephytoGen ->
            return "PTD2016000001"
        }

        SequenceGenerator.metaClass.nextRequestNumber = { int year ->
            return 0
        }

        service.sequenceNumberService = sequenceNumberService

        RequestSerial.withCriteria {
            new RequestSerial(requestNumberSequence:4,requestYear:2018).save()
            new RequestSerial(requestNumberSequence:5,requestYear:2018).save()
            new RequestSerial(requestNumberSequence:6,requestYear:2018).save()
        }

    }

    def setupData() {
        RequestSerial.withCriteria {
            new RequestSerial(requestNumberSequence:1,requestYear:2018).save()
            new RequestSerial(requestNumberSequence:2,requestYear:2018).save()
            new RequestSerial(requestNumberSequence:3,requestYear:2018).save()
        }
    }



    def "Test beforePersist should set requestDate to current date"() {
        given:
        EphytoGen ephytoGen = new EphytoGen()

        when: "beforePersist is executed"

        service.beforePersist(ephytoGen)

        then:
        ephytoGen.requestDate == LocalDate.now()
    }

    def "Test afterPersist should clear requestDate if exemption instance has errors during save"() {
        given:
        EphytoGen ephytoGen = new EphytoGen()

        // set request date

        service.beforePersist(ephytoGen)

        // mock an error
        ephytoGen.errors.rejectValue('id', 'Some error')

        // mock isCreate()
        service.metaClass.isCreate = {
            true
        }

        when: "afterPersist is executed"
        service.afterPersist(ephytoGen, ephytoGen, ephytoGen.hasErrors())

        then: "requestDate should be reset to null"
        ephytoGen.requestDate == null
    }


}
