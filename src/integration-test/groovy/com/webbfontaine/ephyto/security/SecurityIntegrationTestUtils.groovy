package com.webbfontaine.ephyto.security

import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder as SCH

import java.lang.reflect.Modifier

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 21/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class SecurityIntegrationTestUtils {
    /**
     * Register a currently authenticated user.
     * @return the authentication
     */
    static Authentication authenticate() {
        authenticate null, null, null
    }

    /**
     * Register a currently authenticated user.
     *
     * @param principal the principal
     * @param credentials the password
     * @param authorities the roles
     * @return the authentication
     */
    static Authentication authenticate(principal, credentials, List<GrantedAuthority> authorities) {
        Authentication authentication = new TestingAuthenticationToken(principal, credentials, authorities)
        authentication.authenticated = true
        SCH.context.authentication = authentication
        authentication
    }

    static Authentication authenticate(roleNames) {
        authenticate null, null, roleNames.collect { new SimpleGrantedAuthority(it) }
    }

    /**
     * De-register the currently authenticated user.
     */
    static void logout() {
        SCH.clearContext()
    }

    static boolean testPrivateConstructor(Class clazz) {
        assert 1 == clazz.declaredConstructors.length
        def constructor = clazz.getDeclaredConstructor()
        assert Modifier.isPrivate(constructor.modifiers)
        assert !constructor.accessible
        constructor.accessible = true
        constructor.newInstance()
        true
    }

    static void authenticateUser(roles) {
        def authorities = SpringSecurityUtils.parseAuthoritiesString(roles)
        def principal = new org.springframework.security.core.userdetails.User('username', 'password', true, true, true, true, authorities)
        def authentication = new TestingAuthenticationToken(principal, null, authorities)
        authentication.authenticated = true
        SCH.context.authentication = authentication
    }

    static void authenticateUser(roles, credentials) {
        def authorities = SpringSecurityUtils.parseAuthoritiesString(roles)
        def principal = new org.springframework.security.core.userdetails.User(credentials.username, credentials.password, true, true, true, true, authorities)
        def authentication = new TestingAuthenticationToken(principal, null, authorities)
        authentication.authenticated = true
        SCH.context.authentication = authentication
    }
}
