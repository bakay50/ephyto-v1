package com.webbfontaine.ephyto.serial

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.serial.RequestSerial
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.gorm.Domain
import grails.test.mixin.hibernate.HibernateTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.joda.time.LocalDate
import spock.lang.Specification
import org.grails.datastore.mapping.simple.SimpleMapDatastore
import static com.webbfontaine.ephyto.gen.serial.RequestSerial.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */

@TestMixin(GrailsUnitTestMixin)
@TestFor(SequenceNumberService)
@Mock([EphytoGen,RequestSerial])
class SequenceNumberServiceSpec extends Specification {

    def setup() {
        service.metaClass.execQueryOnRequestSerial {y ->
            return 219
        }
    }

    def cleanup() {
    }

    def "Test SequenceNumber Service"() {
        given:
        EphytoGen ephytoGen = new EphytoGen()
        ephytoGen.requestNumberSequence=101
        ephytoGen.save(flush: true)
        def year = LocalDate.now().getYear()

        when: "NextRequestNumber is generated"
        service.nextRequestNumber(ephytoGen)

        then:
        ephytoGen.requestNumberSequence == 219
        ephytoGen.requestNumber == "PTD${year}000219"
    }

}
