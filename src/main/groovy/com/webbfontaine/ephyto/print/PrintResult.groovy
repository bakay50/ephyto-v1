package com.webbfontaine.ephyto.print

import grails.plugins.jasper.JasperExportFormat

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Faustine Valery Tamayo
 * Date: 6/15/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class PrintResult {
    String name
    JasperExportFormat fileFormat
    byte[] content
}
