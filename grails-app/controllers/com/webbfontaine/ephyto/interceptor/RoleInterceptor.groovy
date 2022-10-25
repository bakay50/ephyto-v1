package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils

class RoleInterceptor {

    RoleInterceptor() {
        match(controller: 'ephytoGen|applicator', action: '*')
    }

    boolean before() {
        if (!EphytoSecurityServiceUtils.roleHasAccess()){
            redirect(uri: '/access-denied')
            return false
        }
        return true
    }
}
