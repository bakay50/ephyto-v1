package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.utils.IntegrationTestUtils
import com.webbfontaine.grails.plugins.rimm.cmp.Company
import com.webbfontaine.grails.plugins.rimm.dec.Declarant
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import grails.testing.mixin.integration.Integration

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 07/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
@Integration
@Rollback
class NamesAndPartiesRuleIntegrationSpec extends Specification {

    def "Test exporter namd and address should be properly set"() {
        given:
        IntegrationTestUtils.createExporte('0000058J')
        Rule rule = new NamesAndPartiesRule('exporterCode', 'exporterName', 'exporterAddress', 'description', Company, null)
        rule.metaClass.conversationId = { "0001" }
        rule.metaClass.getValidationRequired = { true }
        EphytoGen ephytoGen = new EphytoGen(exporterCode: '0000058J')
        ephytoGen.setIsDocumentEditable(true)

        when: "apply method is executed"
        rule.apply(new RuleContext(ephytoGen, ephytoGen.errors))

        then: "exporter name and address should be set"
        ephytoGen.exporterCode != null
        ephytoGen.exporterAddress != null
    }

    def "Test exporter name and address should not be set"() {
        given:
        Rule rule = new NamesAndPartiesRule('exporterCode', 'exporterName', 'exporterAddress', 'description', Company, null)
        rule.metaClass.conversationId = { "0001" }
        rule.metaClass.getValidationRequired = { true }
        EphytoGen ephytoGen = new EphytoGen(exporterCode: '000005XX')
        ephytoGen.setIsDocumentEditable(true)

        when: "apply method is executed"
        rule.apply(new RuleContext(ephytoGen, ephytoGen.errors))

        then: "exporter name and address should be set"
        ephytoGen.exporterName == null
        ephytoGen.exporterAddress == null
    }

    def "Test declarant name and address should be properly set"() {
        given:
        IntegrationTestUtils.createDeclarant('00003Q')
        Rule rule = new NamesAndPartiesRule('declarantCode', 'declarantName', 'declarantAddress', 'description', Declarant, null)
        rule.metaClass.conversationId = { "0001" }
        rule.metaClass.getValidationRequired = { true }
        EphytoGen ephytoGen = new EphytoGen(declarantCode: '00003Q')
        ephytoGen.setIsDocumentEditable(true)

        when: "apply method is executed"
        rule.apply(new RuleContext(ephytoGen, ephytoGen.errors))

        then: "declarant name and address should be set"
        ephytoGen.declarantName != null
        ephytoGen.declarantAddress != null
    }

    def "Test declarant name and address should not be set"() {
        given:
        Rule rule = new NamesAndPartiesRule('declarantCode', 'declarantName', 'declarantAddress', 'description', Declarant, null)
        rule.metaClass.conversationId = { "0001" }
        rule.metaClass.getValidationRequired = { true }
        EphytoGen ephytoGen = new EphytoGen(declarantCode: '000XXX')
        ephytoGen.setIsDocumentEditable(true)

        when: "apply method is executed"
        rule.apply(new RuleContext(ephytoGen, ephytoGen.errors))

        then: "declaraant name and address should be set"
        ephytoGen.declarantName == null
        ephytoGen.declarantAddress == null
    }
}
