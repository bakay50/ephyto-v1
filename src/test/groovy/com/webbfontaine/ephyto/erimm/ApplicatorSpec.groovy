package com.webbfontaine.ephyto.erimm


import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Applicator)
class ApplicatorSpec extends Specification {

    def setup() {

    }

    @Unroll("test Applicator all constraints #field is #error")
    def "test ExempLog all constraints"() {
        when:
        def obj = new Applicator("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error               | field         || val
        'nullable'          | 'code'        || null
        'maxSize.exceeded'  | 'code'        || getCustomTextWithLength(25)
        'nullable'          | 'agreement'   || null
        'maxSize.exceeded'  | 'agreement'   || getCustomTextWithLength(80)
    }

    String getCustomTextWithLength(Integer length) {
        'a' * length
    }

    void validateConstraints(domain, field, error) {
        def validated = domain.validate()
        if (error && error != 'valid') {
            assert !validated
            assert domain.errors[field]
            assert domain.errors[field].code == error
        } else {
            assert !domain.errors[field]
        }
    }
}
