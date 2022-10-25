package com.webbfontaine.ephyto


import org.springframework.context.i18n.LocaleContextHolder

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 19/07/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class RuleUtils {

    private static final String Format_French = "fr"
    private static final String Format_FrenchUpper = "fr_FR"

    public static boolean isFrenchVersion() {
        String locale = LocaleContextHolder.getLocale().toString();
        boolean isFrenchversion = false
        if (locale.toString().trim() == Format_French || locale.toString().trim() == Format_FrenchUpper) {
            isFrenchversion = true
        }
        return isFrenchversion
    }
}
