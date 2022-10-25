package com.webbfontaine.ephyto

import grails.util.Holders

/**
 *
 * @author sylla
 */

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.ReadablePartial
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParsePosition

class TypeCastUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(TypeCastUtils.datePattern())
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern('yyyyMMMdd HH:mm:ss')
    private static volatile DateTimeFormatter DEFAULT_FORMATTER

    public static Integer toInteger(Object obj) {
        try {
            return Integer.valueOf(obj?.toString())
        } catch (NumberFormatException ignore) {
            return null
        }
    }

    public static LocalDate toLocalDate(Object obj, DateTimeFormatter formatter = null) {
        String str = getString(obj)

        try {
            if (str) {
                if (formatter) {
                    return formatter.parseLocalDate(str)
                } else {
                    return DATE_FORMATTER.parseLocalDate(str)
                }
            }
        } catch (e) {
            return null
        }
        return null
    }

    public static String fromDate(LocalDate localDate) {
        if (localDate) {
            return DATE_FORMATTER.print(localDate)
        }

        return null
    }

    public static Integer toLong(Object obj) {
        try {
            return Long.valueOf(obj?.toString())
        } catch (NumberFormatException ignore) {
            return null
        }
    }

    private static String fromLong(Long value) {
        if (value) {
            return value.toString();
        }
        return null;
    }

    private static String fromInteger(Integer integer) {
        if (integer) {
            return integer.toString();
        }
        return null;
    }

    public static String datePattern() {
        Holders.config.jodatime.format.org.joda.time.LocalDate
    }

    public static String getString(Object obj) {
        if (obj) {
            String str = obj.toString()

            if (!str.isEmpty()) {
                return str
            }
        }

        return null
    }

    public static BigDecimal toBigDecimal(Object obj) {
        String attemptText = getString(obj)

        if (attemptText && attemptText.isBigDecimal()) {
            return attemptText.toBigDecimal()
        } else {
            return null
        }
    }

    public static String formatDate(ReadablePartial date) {
        return date ? getDefaultFormatter().print(date) : ''
    }

    private static def getDefaultFormatter() {
        if (!DEFAULT_FORMATTER) {
            DEFAULT_FORMATTER = DateTimeFormat.forPattern(
                    Holders.config.jodatime.format.org.joda.time.LocalDate
            )
        }

        return DEFAULT_FORMATTER
    }

    public static parseLong(def target) {
        try {
            return Long.parseLong(target)
        } catch (NumberFormatException ex) {
            return null
        }
    }

    public static String toUTF8String(byte[] data) {
        return new String(data, StandardCharsets.UTF_8)
    }

    public static Boolean toBoolean(Object obj) {
        String str = getString(obj);
        try {
            if (str) {
                return Boolean.valueOf(str)
            }
        } catch (e) {
            return false
        }
        return false;
    }


    public static String parseCurrencyApplyPattern(Object obj) {
        String str = getString(obj);
        try {
            if (str) {
                if (!str.contains(",")) {
                    //String pattern =  "#,##0.000";
                    String pattern = Holders.grailsApplication.config.exchangeRateFormat;
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    DecimalFormat df = (DecimalFormat) decimalFormat.getInstance(Locale.ENGLISH)
                    df = (DecimalFormat) decimalFormat.getInstance(Locale.ENGLISH)
                    df.setParseBigDecimal(true);
                    return (BigDecimal) df.parse(str, new ParsePosition(0));
                } else {
                    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE)
                    return format.parse(str).toString()
                }
            }
        } catch (e) {
            return BigDecimal.ZERO
        }
    }

    public static String RoundBigdecimal(Object obj) {
        String str = getString(obj);
        if (str) {
            return new BigDecimal(str).setScale(0, BigDecimal.ROUND_HALF_UP)
        } else {
            return obj
        }
    }

    public static String getCurrentDateTime() {
        return DATE_TIME_FORMATTER.print(LocalDateTime.now())
    }

}
