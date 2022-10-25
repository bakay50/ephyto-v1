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

@TestFor(EphytoLogMessage)
class EphytoLogMessageSpec extends ConstraintUnitSpec {
    void setup() {

    }

    @Unroll("test ExempLogMessage all constraints #field is #error")
    def "test ConLogMessage all constraints"() {
        when:
        def obj = new EphytoLogMessage("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error               | field             || val
        'nullable'          | 'ephytoLog'       || null
        'maxSize.exceeded'  | 'code'            || getCustomTextWithLength(6)
        'maxSize.exceeded'  | 'message'         || getCustomTextWithLength(1025)
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
