package com.webbfontaine.ephyto.gen

import com.webbfontaine.ephyto.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Unroll

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@TestFor(EphytoLog)
class EphytoLogSpec extends ConstraintUnitSpec {

    void setup() {

    }

    @Unroll("test EphytoLog all constraints #field is #error")
    def "test EphytoLog all constraints"() {
        when:
        def obj = new EphytoLog("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                   | field         || val
        'nullable'              | 'ephytoGen'   || null
        'nullable'              | 'userLogin'   || null
        'maxSize.exceeded'      | 'userLogin'   || getCustomTextWithLength(129)
        'nullable'              | 'userLogin'   || null
        'maxSize.exceeded'      | 'userLogin'   || getCustomTextWithLength(129)
        'nullable'              | 'date'        || null
        'maxSize.exceeded'      | 'message'     || getCustomTextWithLength(1025)
        'maxSize.exceeded'      | 'operation'   || getCustomTextWithLength(129)
        'maxSize.exceeded'      | 'endStatus'   || getCustomTextWithLength(33)
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
