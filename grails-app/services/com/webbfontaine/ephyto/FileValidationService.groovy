package com.webbfontaine.ephyto

import groovy.util.slurpersupport.GPathResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FileValidationService {

    static transactional = false

    private static final Logger LOGGER = LoggerFactory.getLogger(FileValidationService.class)

    def xmlDeepValidate(GPathResult xml, String docType) {
        def error = null

        xml.children().find { element ->
            if (!DataBindingHelper.isXmlAcceptableField(element.name(), docType)) {
                LOGGER.debug("Imported xml file contains invalid node! " + element.name())
                error = "default.xml.invalidContent.message"
            }
        }


        return error
    }
}
