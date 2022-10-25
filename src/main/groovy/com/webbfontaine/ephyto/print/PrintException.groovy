package com.webbfontaine.ephyto.print

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Faustine Valery Tamayo
 * Date: 6/15/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class PrintException {
    public PrintException() {
    }

    public PrintException(String message) {
        super(message);
    }

    public PrintException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrintException(Throwable cause) {
        super(cause);
    }

    public PrintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
