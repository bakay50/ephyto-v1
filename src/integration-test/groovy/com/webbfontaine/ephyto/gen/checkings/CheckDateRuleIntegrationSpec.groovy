package com.webbfontaine.ephyto.gen.checkings


import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import com.webbfontaine.ephyto.gen.treatment.checkings.CheckTreatmentDateRule
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import grails.testing.mixin.integration.Integration
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 27/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
@Integration
@Rollback
class CheckDateRuleIntegrationSpec extends Specification {

    def "Test treatmentSartDate is superior than treatmentEndDate and treatmentSartTime is different to treatmentEndTime"() {
        given: "EphytoTreatment instance with valid treatmentSartDate,treatmentEndTime and isDocumentEditable == true"
        Rule rule = new CheckTreatmentDateRule()
        EphytoTreatment ephytoTreatment = new EphytoTreatment('treatmentSartDate': '27/09/2016','treatmentSartTime':'14:25:36','treatmentEndDate':'10/09/2016','treatmentEndTime':'12:20:56')

        when: "appy method is executed"
        rule.apply(new RuleContext(ephytoTreatment, ephytoTreatment.errors))


        then:
        ephytoTreatment.errors.errorCount > 0
    }

    def "Test treatmentSartDate is equal to treatmentEndDate and treatmentSartTime is superior than treatmentEndTime"() {
        given: "EphytoTreatment instance with valid treatmentSartDate,treatmentEndTime and isDocumentEditable == true"
        Rule rule = new CheckTreatmentDateRule()
        EphytoTreatment ephytoTreatment = new EphytoTreatment('treatmentSartDate': '27/09/2016','treatmentSartTime':'14:25:36','treatmentEndDate':'27/09/2016','treatmentEndTime':'12:20:56')

        when: "appy method is executed"
        rule.apply(new RuleContext(ephytoTreatment, ephytoTreatment.errors))


        then:
        ephytoTreatment.errors.errorCount > 0
    }

    def "Test treatmentSartDate is equal to treatmentEndDate and treatmentSartTime is equal than treatmentEndTime"() {
        given: "EphytoTreatment instance with valid treatmentSartDate,treatmentEndTime and isDocumentEditable == true"
        Rule rule = new CheckTreatmentDateRule()
        EphytoTreatment ephytoTreatment = new EphytoTreatment('treatmentSartDate': '27/09/2016','treatmentSartTime':'14:25:36','treatmentEndDate':'27/09/2016','treatmentEndTime':'14:25:36')

        when: "appy method is executed"
        rule.apply(new RuleContext(ephytoTreatment, ephytoTreatment.errors))


        then:
        ephytoTreatment.errors.errorCount > 0
    }

    def "Test treatmentSartDate is inferior to treatmentEndDate"() {
        given: "EphytoTreatment instance with valid treatmentSartDate,treatmentEndTime and isDocumentEditable == true"
        Rule rule = new CheckTreatmentDateRule()
        EphytoTreatment ephytoTreatment = new EphytoTreatment('treatmentSartDate': '27/09/2016','treatmentSartTime':'14:25:36','treatmentEndDate':'29/09/2016','treatmentEndTime':'14:25:36')

        when: "appy method is executed"
        rule.apply(new RuleContext(ephytoTreatment, ephytoTreatment.errors))


        then:
        ephytoTreatment.errors.errorCount == 0
    }
}
