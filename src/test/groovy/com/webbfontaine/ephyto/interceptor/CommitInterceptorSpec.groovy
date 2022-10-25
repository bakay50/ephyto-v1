package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.workflow.Operation
import com.webbfontaine.grails.plugins.conversation.store.ObjectsStore
import grails.testing.gorm.DataTest
import grails.testing.web.interceptor.InterceptorUnitTest
import grails.web.context.ServletContextHolder
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.GrailsApplicationAttributes
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.context.request.RequestContextHolder
import spock.lang.Specification
import spock.lang.Unroll

class CommitInterceptorSpec extends Specification implements InterceptorUnitTest<CommitInterceptor>, DataTest{

    void setupSpec() {
        mockDomain(EphytoGen)
        mockDomain(Applicator)
    }

    @Unroll
    void "Test Commit Operation Save or update Applicator"() {
        given:
        interceptor.objectStoreService = Stub(ObjectsStore) {
            get(_) >> domainClass
        }
        MockHttpServletRequest response = new MockHttpServletRequest()
        GrailsWebRequest webRequest = new GrailsWebRequest(response, new MockHttpServletResponse(), ServletContextHolder.servletContext)
        response.setAttribute(GrailsApplicationAttributes.WEB_REQUEST, webRequest)
        webRequest.params.operationRequest = [Operation.REQUEST.humanName(), Operation.REQUEST.humanName()]
        RequestContextHolder.setRequestAttributes(webRequest)
        def commitOperation = [id : Operation.REQUEST.humanName(), name : [Operation.REQUEST.humanName(), Operation.REQUEST.humanName()]]
        interceptor.ephytoGenBpmService = [getCommitOperation: { a -> commitOperation }, getCommitOperationName: { a ->  Operation.REGISTER.humanName() }, validationNotRequired: {a -> true}]

        when:
        interceptor.before()

        then:
        interceptor.before() == expectResult

        where:
        domainClass      | expectResult
        new EphytoGen()  | true
        new Applicator() | false

    }
}
