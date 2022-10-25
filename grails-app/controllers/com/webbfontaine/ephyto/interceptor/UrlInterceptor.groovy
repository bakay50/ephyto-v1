package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.MessageSourceUtils


class UrlInterceptor {

    int order = HIGHEST_PRECEDENCE

    UrlInterceptor() {
        match controller: 'ephytoGen', action: 'show|edit'
    }

    boolean before() {
        try {
            if (params.id) {
                Long.parseLong(params.id)
            }
        } catch (NumberFormatException nfe) {
            flash.errorMessage = MessageSourceUtils.getMessage('default.invalid.id.message')
            redirect(action: 'list')
            return false
        }
        true
    }

}