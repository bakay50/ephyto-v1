package com.webbfontaine.ephyto

import grails.util.Holders
import spock.lang.Specification

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 17/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class ConstraintUnitSpec extends Specification {

    String getCustomTextWithLength(Integer length) {
        'a' * length
    }


    void validateConstraints(obj, field, error) {
        def validated = obj.validate()
        if (error && error != 'valid') {
            assert !validated
            assert obj.errors[field]
            assert error == obj.errors[field]
        } else {
            assert !obj.errors[field]
        }
    }

    String getFileExtensions(boolean valid) {
        def fileExtensions = Holders.config.attachmentAcceptedFormats
        valid ? fileExtensions.get(new Random().nextInt(fileExtensions.size() - 1)) : "INVALID_EXTENSION"
    }
}
