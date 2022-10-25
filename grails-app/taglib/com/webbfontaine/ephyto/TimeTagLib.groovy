package com.webbfontaine.ephyto

import com.webbfontaine.grails.plugins.taglibs.Utils
import grails.util.Holders

import java.time.temporal.Temporal

class TimeTagLib {
    static namespace = "wf"
    def timepicker = { attrs ->

        if (Utils.isEditMode()) {
            Utils.extendAttributeValue(attrs, 'class', 'generate-time')
        }

        if (attrs.value instanceof Temporal) {
            java.time.format.DateTimeFormatter dateTimeFormatter = dateFormatterPatternForTemporal()
            attrs.value = dateTimeFormatter?.format(attrs.value)
        }
        out << textInput(attrs)
    }

    public static java.time.format.DateTimeFormatter dateFormatterPatternForTemporal() {
        java.time.format.DateTimeFormatter.ofPattern(Holders.config.timeFormat)
    }

    Closure textInput = { attrs ->
        Utils.setDefaultClassForInput(attrs)

        boolean editable = Utils.isEditable(attrs)

        if (editable) {
            Utils.addRequiredAttributeIfSet(attrs)
        } else {
            Utils.setAttributesForReadOnly(attrs)
            if (!Utils.isEditMode()) {
                attrs.remove('pattern')
                attrs.remove('required')
                attrs.remove('maxlength')
                attrs.remove('regexp')
            }
        }

        if (attrs.remove('translateValue')) {
            attrs.value = g.message(code: attrs.value?.toString(), default: "")
        }

        Utils.makeStartClearable(out, attrs)
        out << textField(attrs)
        Utils.makeEndClearable(out, attrs)

    }

}
