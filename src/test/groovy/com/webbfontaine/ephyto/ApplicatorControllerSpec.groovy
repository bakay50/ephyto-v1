package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.applicator.ApplicatorController
import com.webbfontaine.ephyto.constants.Roles
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.security.SecurityTestUtils
import com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ApplicatorControllerSpec extends Specification implements ControllerUnitTest<ApplicatorController>, DataTest {

    def setup() {
        mockDomain(Applicator)
        Applicator applicator = new Applicator(id: "1", code : "0025", agreement: "CIV-0041", nameAddress : "003 BP ABJ")
        applicator.save(flush : true, validate : false)
    }

    def cleanup() {
    }

    def "test create() functionality"() {
        given:
        SecurityTestUtils.authenticateUser(Roles.ROLE_EPHYTO_SUPER_ADMINISTRATOR)
        GroovyMock(SecurityTestUtils, global: true)
        EphytoSecurityServiceUtils.isSuperAdministrator() >> true
        controller.applicatorService = [addToSessionStore: { a -> new Applicator() }]
        controller.applicatorBusinessLogicService = [initDocumentForCreate: { a -> new Applicator() }]
        controller.conversationService = [addToConversation: { a -> true }]

        when:
        controller.create()

        then:
        controller.modelAndView.viewName == "/applicator/edit"
    }
}
