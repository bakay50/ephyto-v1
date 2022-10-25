package com.webbfontaine.ephyto.workflow

import groovy.transform.CompileStatic

/**
 * Copyrights 2002-2018 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 3/14/2018
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
@CompileStatic
interface GlobalOperation extends Serializable{
    String getAction()
}