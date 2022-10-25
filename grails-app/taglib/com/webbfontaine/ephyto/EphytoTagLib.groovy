package com.webbfontaine.ephyto

import grails.converters.JSON
import org.slf4j.Logger
import org.slf4j.LoggerFactory
/**
 *
 * @author sylla
 */
class EphytoTagLib {
 private static final Logger LOGGER = LoggerFactory.getLogger(EphytoTagLib.class);
    static namespace = "wf"

    /**
     * @attr bean REQUIRED - instance of bean
     * @attr refName REQUIRED - instance of bean
     */
    Closure loadMandatoryFields = { attrs, body ->
        long startTime = System.currentTimeMillis()
        def bean = attrs.bean
        String refName= attrs.refName

        def acceptableFields = DataBindingHelper."get${refName?.capitalize()}AcceptableFields"(bean)
        def mandatoryFields = acceptableFields.findAll { bean."is${refName?.capitalize()}Mandatory"(it) } as JSON

        out << g.javascript(attrs, "\n//generated code by EphytoTagLib tag \nvar ${refName?.toUpperCase()}_MANDATORY_FIELDS = " + mandatoryFields.toString() + ";\n")

        LOGGER.debug "mandatory fields, load,render time: ${System.currentTimeMillis() - startTime}ms."
    }


}
