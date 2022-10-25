package com.webbfontaine.ephyto.traits

import com.webbfontaine.ephyto.traits.MandatoryFieldsRule
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.validation.Validateable
import org.springframework.context.i18n.LocaleContextHolder
import spock.lang.Specification

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 24/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class MandatoryFieldsRuleSpec extends Specification {

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

        when:
        rule.apply(new RuleContext(dummy, dummy.errors))

        then:
        dummy.hasErrors()
    }

    class DummyRule implements MandatoryFieldsRule<DummyDomain> {
        @Override
        Set<String> defineMandatoryFields() {
            return ['field']
        }

        @Override
        Map<String, String> defineHumanNames() {
            return ['field': 'Default field']
        }


        @Override
        Class<DummyDomain> getType() {
            return DummyDomain.class
        }
    }


    class DummyDomain implements  Validateable{
        Long id
        String field

        static constraints = {
            field nullable: false
        }
    }
}
