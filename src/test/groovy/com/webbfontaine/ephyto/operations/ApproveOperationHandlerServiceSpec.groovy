package com.webbfontaine.ephyto.operations

import com.webbfontaine.ephyto.BusinessLogicService
import com.webbfontaine.ephyto.InitFieldsValueInUTF8Service
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.operations.ApproveOperationHandlerService
import com.webbfontaine.ephyto.utils.TestUtils
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.util.Holders
import grails.web.context.ServletContextHolder
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.GrailsApplicationAttributes
import org.joda.time.LocalDate
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.context.request.RequestContextHolder
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 21/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
@TestFor(ApproveOperationHandlerService)
@Mock([EphytoGen])
class ApproveOperationHandlerServiceSpec extends Specification {


    def setup(){
        createRequestContextHolder()
        service.setInitFieldsValueInUTF8Service(new InitFieldsValueInUTF8Service())
    }

    def "Test ephyto with data will render  phytosanitaryCertificateRef and phytosanitaryCertificateDate properly"() {
        given:
        // mock ephyto data
        Calendar startCalendar = Calendar.getInstance()
        startCalendar.set(2016,27,10)
        EphytoGen ephytoGen = TestUtils.createEphytoGen("0001")
        ephytoGen.phytosanitaryCertificateRef = "PHYTO001"
        ephytoGen.phytosanitaryCertificateDate = LocalDate.fromCalendarFields(startCalendar)

        service.metaClass.getCommitOperation = {
            return Operations.OI_APPROVE_REQUESTED
        }

        when:
        service.beforePersist(ephytoGen)

        then:
        ephytoGen.phytosanitaryCertificateRef == "PHYTO001"
        ephytoGen.phytosanitaryCertificateDate == LocalDate.fromCalendarFields(startCalendar)
    }

    private void createRequestContextHolder() {
        MockHttpServletRequest request = new MockHttpServletRequest()
        GrailsWebRequest webRequest = new GrailsWebRequest(request, new MockHttpServletResponse(), ServletContextHolder.servletContext)
        request.setAttribute(GrailsApplicationAttributes.WEB_REQUEST, webRequest)
        RequestContextHolder.setRequestAttributes(webRequest)
    }


}
