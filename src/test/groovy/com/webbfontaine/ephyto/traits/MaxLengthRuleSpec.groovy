package com.webbfontaine.ephyto.traits

import com.webbfontaine.ephyto.traits.MaxLengthRule
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.validation.Validateable
import org.springframework.context.i18n.LocaleContextHolder
import spock.lang.Specification

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Carina Garcia
 * Date: 02/08/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class MaxLengthRuleSpec extends Specification {

    def Rule rule = new DummyRule()

    void setup() {
        rule.metaClass.conversationId = '-123456'
        LocaleContextHolder.metaClass.'static'.getLocale = { ->
            new Locale('en', 'EN')
        }
    }

    def "test apply method"() {
        given:
        def dummy = new DummyDomain()
        dummy.exemptionTypeCode = "1234567890"

        when:
        rule.apply(new RuleContext(dummy, dummy.errors))

        then:
        dummy.hasErrors()
    }

    class DummyRule implements MaxLengthRule<DummyDomain> {
        @Override
        Map<String, Integer> defineMaxFieldsLength() {
            return ["exemptionTypeCode"  : 5]
        }

        @Override
        Map<String, String> defineHumanNames() {
            return ["exemptionTypeCode"  : "Exemption Type"]
        }


        @Override
        Class<DummyDomain> getType() {
            return DummyDomain.class
        }

        @Override
        String getRankName(){
            return "rank"
        }
    }


    class DummyDomain implements Validateable{
        Long id
        String rank
        String exemptionTypeCode

        static constraints = {
            field nullable: false
        }
    }

}
