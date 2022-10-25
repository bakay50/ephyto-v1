package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.testing.gorm.DataTest
import org.joda.time.LocalDate
import spock.lang.Specification

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: DIARRA Fady
 * Date: 09/11/2020
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class SadRuleSpec extends Specification implements DataTest {

    void "test checkSadErrors"() {
        given:
        mockDomain(EphytoGen)
        EphytoGen ephytoGen = new EphytoGen(requestNumber: "2345", requestDate: LocalDate.now(), customsClearanceOfficeCode: customsClearanceOfficeCode, modeOfTransportCode: modeOfTransport,
                exporterCode: "000121", declarationSerial: "E", declarationNumber: 25, requestYear: LocalDate.now().getYear(), requestNumberSequence: 12131, productTypeCode: EphytoConstants.MANGUE)
        when:
        SadRule rule = new SadRule()
        RuleContext ruleContext = new RuleContext(ephytoGen, ephytoGen.errors)
        SadRule.metaClass.static.isCommitOperationValid = {
            return true
        }
        SadRule.metaClass.static.isSadCheckEnabled = {
            return true
        }
        rule.apply(ruleContext)
        then:
        ruleContext.hasErrors() == expected
        where:
        modeOfTransport                       | customsClearanceOfficeCode | declarationSerial | expected
        EphytoConstants.MODE_OF_TRANSPORT_AIR | null                       | null              | true
        EphytoConstants.MODE_OF_TRANSPORT_AIR | "CIAB3"                    | null              | false
    }

    void "test isDeclarationFieldsRequired"() {
        given:
        EphytoGen ephytoGen = new EphytoGen(requestNumber: "2345", requestDate: LocalDate.now(), customsClearanceOfficeCode: "CIAB3", modeOfTransportCode: modeOfTransport,declarationDate: declarationDate,
                exporterCode: "000121", declarationSerial: "E", declarationNumber: declarationNumber, requestYear: LocalDate.now().getYear(), requestNumberSequence: 12131, productTypeCode: EphytoConstants.MANGUE)
        when:
        boolean result = SadRule.isDeclarationFieldsRequired(ephytoGen)
        then:
        result == expected
        where:
        modeOfTransport                       | declarationNumber | declarationSerial | declarationDate | expected
        EphytoConstants.MODE_OF_TRANSPORT_AIR | null              | null              | LocalDate.now() | false
        EphytoConstants.MODE_OF_TRANSPORT_AIR | 25                | null              | null            | false
        "1"                                   | null              | "E"               | LocalDate.now() | true
        "1"                                   | 25                | "E"               | LocalDate.now() | false
    }

    void "test getErrorCode"() {

        when:
        String result = SadRule.getErrorCode(modeOfTransport)
        then:
        result == expected
        where:
        modeOfTransport                       | expected
        EphytoConstants.MODE_OF_TRANSPORT_AIR | "ephytoGen.input.clearanceOfficeCode.required"
        "1"                                   | "ephytoGen.sad.input.required"
        "2"                                   | "ephytoGen.sad.input.required"
    }

}
