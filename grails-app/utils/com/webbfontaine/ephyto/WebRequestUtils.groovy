package com.webbfontaine.ephyto

import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpServletRequest

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 3/17/15
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class WebRequestUtils extends com.webbfontaine.grails.plugins.utils.WebRequestUtils {

    public static getSessionScope() {
        RequestContextHolder.currentRequestAttributes().getSession()
    }

    public static getFlashScope() {
        def grailsWebRequest = WebUtils.retrieveGrailsWebRequest()
        return grailsWebRequest.attributes.getFlashScope(grailsWebRequest.request)
    }

    public static getValidateOperation() {
        params.get("validateOperationAvailable")
    }

    static HttpServletRequest currentRequest() {
        GrailsWebRequest.lookup().getCurrentRequest()
    }

}
