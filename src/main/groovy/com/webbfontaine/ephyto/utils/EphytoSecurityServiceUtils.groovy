/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webbfontaine.ephyto.utils

import com.webbfontaine.ephyto.TypeCastUtils

/**
 *
 * @author sylla
 */

import com.webbfontaine.ephyto.command.SearchCommand
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.workflow.Role
import com.webbfontaine.grails.plugins.security.DocAccessConstants
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.util.Holders
import org.grails.datastore.mapping.query.api.Criteria

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Alvin Goya
 * Date: 5/12/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
final class EphytoSecurityServiceUtils {

    public static applyUserSpecificRestrictions(Criteria criteria, Map userPropertiesMapping, SpringSecurityService springSecurityService, Class domainClass, def searchCommand) {
        userPropertiesMapping.each { String userPropName, def fieldsNames ->
            def userProperty = springSecurityService?.authentication?.principal?.getAt(userPropName)
            if (userProperty != null) {
                if (fieldsNames instanceof String) {
                    applyRestriction(criteria, fieldsNames, userProperty, domainClass, searchCommand)
                } else {
                    applyRestrictionList(criteria, fieldsNames, userProperty, domainClass)
                }
            }
//            else {
//                criteria.and {
//                    eq('id', -1L)
//                }
//            }


        }
    }

    private static applyRestriction(Criteria criteria, String fieldName, String userProp, Class domainClass, SearchCommand searchCommand) {
        def allowedValues = userProp.split(DocAccessConstants.SEPARATOR)
        if (!allowedValues.contains(DocAccessConstants.ALL) && !(allowedValues.size() == 1 && allowedValues.contains("")) && allowedValues != null) {
            criteria.or {
                allowedValues.each {
                    def fieldType = domainClass?.getDeclaredField(fieldName)?.type
                    if (fieldType == Integer) {
                        eq(fieldName, TypeCastUtils.toInteger(it))
                    } else if (fieldType == String) {
                        eq(fieldName, it)
                    }
                }
                if (fieldName == EphytoConstants.LEVEL_USER_PROPERTY && (!searchCommand?.status || searchCommand?.status == Statuses.ST_STORED)) {
                    eq(EphytoConstants.STATUS_SEARCH_FIELD, Statuses.ST_STORED)
                }
            }
        }
    }

    private static applyRestrictionList(Criteria criteria, def fieldsNames, String userProp, Class domainClass) {
        def allowedValues = userProp.split(DocAccessConstants.SEPARATOR)
        if (!allowedValues.contains(DocAccessConstants.ALL) && !(allowedValues.size() == 1 && allowedValues.contains("")) && allowedValues != null) {
            criteria.or {
                allowedValues.each { def allowedValue ->
                    or {
                        fieldsNames.each { def fieldName ->
                            def fieldType = domainClass?.getDeclaredField(fieldName)?.type
                            if (fieldType == Integer) {
                                eq(fieldName, TypeCastUtils.toInteger(allowedValue))
                            } else if (fieldType == String) {
                                eq(fieldName, allowedValue)
                            }
                        }
                    }
                }
            }
        }
    }

    static boolean isDeclarant() {
        SpringSecurityUtils.ifAnyGranted(Role.DECLARANT.getAuthority())
    }

    static boolean isSuperAdmin() {
        SpringSecurityUtils.ifAnyGranted(Role.GOV_SUPERVISOR.getAuthority())
    }

    static boolean isAdmin() {
        SpringSecurityUtils.ifAnyGranted(Role.ADMINISTRATOR.getAuthority())
    }

    static boolean isTrader() {
        SpringSecurityUtils.ifAnyGranted(Role.TRADER.getAuthority())
    }

    static boolean isGovOfficer() {
        SpringSecurityUtils.ifAnyGranted(Role.GOV_OFFICER.getAuthority())
    }

    static boolean isGovSeniorOfficer() {
        SpringSecurityUtils.ifAnyGranted(Role.GOV_SENIOR_OFFICER.getAuthority())
    }

    static boolean isGovSup() {
        SpringSecurityUtils.ifAnyGranted(Role.GOV_SUPERVISOR.getAuthority())
    }

    static boolean isSuperAdministrator() {
        SpringSecurityUtils.ifAllGranted(Role.SUPER_ADMINISTRATOR.getAuthority())
    }

    static boolean roleHasAccess(){
        SpringSecurityService springSecurityService = Holders.applicationContext.getBean(SpringSecurityService)
        springSecurityService?.authentication?.authorities?.any { Role.values().collect {it.authority}.contains(it.authority) }
    }

}
