package com.webbfontaine.ephyto
//import static com.webbfontaine.ephyto.gen.Util.*

/**
 * Created by sylla on 28/09/16.
 */

class WfTagLib {

    static namespace = "wf"

    /**
     * @attr name REQUIRED
     * @attr regexp* @attr regexpForRule* @attr value
     */
    Closure textInputUpperCase = { attrs, body ->
        addDefaultCssForInput(attrs)
        attrs.value = toUpperCase(attrs.value)
        attrs.oninput = "${attrs.oninput ?: ''} toUppercase(event)"
        out << textField(attrs, body)
    }

    private static String toUpperCase(String value) {
        if (value) {
            return value.toUpperCase()
        } else {
            return null;
        }
    }

    static def addDefaultCssForInput(Map attrs) {
        attrs.class = "${attrs.class ?: ''} wfinput "
    }
}