package com.webbfontaine.ephyto.gen.attachment

import com.webbfontaine.ephyto.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Unroll
import grails.util.Holders
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
@TestFor(Attachment)
class AttachmentSpec extends ConstraintUnitSpec {

    void setupSpec() {
        config.attachmentAcceptedFormats = ['pdf', 'jpg', 'jpeg', 'tiff', 'tif', 'gif', 'png']
    }

    void setup() {

    }

    @Unroll("test Attachment all constraints #field is #error")
    def "test Attachment all constraints"() {
        when:
        def obj = new Attachment("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error               | field           || val
        'nullable'          | 'docType'       || null
        'nullable'          | 'docRef'        || null
        'maxSize.exceeded'  | 'docRef'        || getCustomTextWithLength(150)
        'nullable'          | 'docDate'       || null
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
