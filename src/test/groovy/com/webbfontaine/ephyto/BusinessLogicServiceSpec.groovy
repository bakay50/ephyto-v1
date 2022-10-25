package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.operations.ApproveOperationHandlerService
import com.webbfontaine.ephyto.workflow.EphytoWorkflow
import com.webbfontaine.grails.plugins.validation.rules.DocVerificationService
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.web.context.ServletContextHolder
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.GrailsApplicationAttributes
import org.joda.time.LocalDate
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.context.request.RequestContextHolder
import com.webbfontaine.grails.plugins.rimm.hist.HistorizationSupport
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.apache.poi.ss.formula.functions.T
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */

@TestFor(BusinessLogicService)
@TestMixin(GrailsUnitTestMixin)
@Mock([EphytoGen, EphytoSecurityService ])
class BusinessLogicServiceSpec extends Specification {
    void setupSpec() {
        defineBeans {
            ephytoWorkflow(EphytoWorkflow)
            springSecurityService(SpringSecurityService)
            ephytoGenBpmService(EphytoGenBpmService) {
                ephytoWorkflow = ref('ephytoWorkflow')
                springSecurityService = ref('springSecurityService')
            }

        }
    }

    void setup() {
            createRequestContextHolder()
        service.setInitFieldsValueInUTF8Service(new InitFieldsValueInUTF8Service())
        grailsApplication.mainContext.springSecurityService.metaClass.isLoggedIn = {
            true
        }
    }

    def "test init EphytoGen for create"() {
        when:
        def springSecurityService = [authentication:[principal: [TIN: "ALL", DEC: "ALL", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        service.ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT)

        def ephytoGen = new EphytoGen()
        service.initDocumentForCreate(ephytoGen)

        then:
        ephytoGen.operations != null
        ephytoGen.isDocumentEditable
        !ephytoGen.notProperlyInitialized
    }

    def "test init EphytoGen for ReplaceOperation"() {
        when:
        service.approveOperationHandlerService= new ApproveOperationHandlerService()
        def springSecurityService = [authentication:[principal: [TIN: "ALL", DEC: "ALL", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_GOV_SUPERVISOR]]]]
        service.ephytoSecurityService.springSecurityService = springSecurityService
        def ephytoGen = new EphytoGen()
        ephytoGen.id=12131
        ephytoGen.requestNumber="ephytoGen"
        ephytoGen.requestDate=LocalDate.now()
        ephytoGen.userReference="DOSSIER 52456565653 BAGS"
        ephytoGen.otReference="OT: BICAO 013/ 2016-2017OLAM"
        ephytoGen.customsClearanceOfficeCode="CIAB3"
        ephytoGen.declarationSerial="E"
        ephytoGen.declarationNumber=25
        ephytoGen.phytosanitaryCertificateRef="ref1"
        ephytoGen.phytosanitaryCertificateDate=LocalDate.now()
        ephytoGen.requestDate = LocalDate.now()
        def year = ephytoGen.requestDate.getYear()
        ephytoGen.requestYear = year
        ephytoGen.requestNumberSequence =12131
        ephytoGen.requestNumber ="2345"
        ephytoGen.status=null
        Map params= new HashMap()
        params.put("op","Approve")
        service.initDocumentForReplace(ephytoGen,params)
        then:
        ephytoGen.startedOperation=="Replace"
    }

    private void createRequestContextHolder() {
        MockHttpServletRequest request = new MockHttpServletRequest()
        GrailsWebRequest webRequest = new GrailsWebRequest(request, new MockHttpServletResponse(), ServletContextHolder.servletContext)
        request.setAttribute(GrailsApplicationAttributes.WEB_REQUEST, webRequest)
        RequestContextHolder.setRequestAttributes(webRequest)
    }
}
