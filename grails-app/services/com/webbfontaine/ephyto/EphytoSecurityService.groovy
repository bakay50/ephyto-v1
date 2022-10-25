package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils
import com.webbfontaine.ephyto.gen.EphytoGen
import grails.orm.HibernateCriteriaBuilder




/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Carina Garcia
 * Date: 3/17/15
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class EphytoSecurityService {

    static transactional = false

    def springSecurityService

    def getUserName() {
        springSecurityService?.authentication?.principal?.username
    }

    def getCompanyName() {
        springSecurityService?.authentication?.principal?.getAt("company")
    }

    def getUserOwnerGroup() {
        springSecurityService?.authentication?.principal?.getAt("ownerGroup")
    }



    def getUserProperty(String userProp) {
        springSecurityService?.authentication?.principal?.getAt(userProp)
    }

    def boolean userHasRole(String role) {
        def userRoles = springSecurityService.principal.authorities
        return userRoles.any { role == it.toString() }
    }

    def boolean userHasRoles(List roles) {
        def userRoles = springSecurityService.principal.authorities
        return userRoles.any { roles.contains(it.toString()) }
    }

    def applyUserSpecificRestrictions(searchCommand, HibernateCriteriaBuilder criteriaBuilder) {
        EphytoSecurityServiceUtils.applyUserSpecificRestrictions(criteriaBuilder, EphytoGen.userPropertiesMapping, springSecurityService, EphytoGen, searchCommand)
    }


}
