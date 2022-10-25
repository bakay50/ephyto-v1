package com.webbfontaine.ephyto.signature

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import org.springframework.core.io.FileSystemResource

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo


/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GwsSignatureRestService)
@TestMixin(GrailsUnitTestMixin)
class GwsSignatureRestServiceSpec extends Specification {
    RestTemplate restTemplate = new RestTemplate()

    void setup() {
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept(Arrays.asList(APPLICATION_OCTET_STREAM))
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate)
        mockServer.expect(requestTo(new URI("https://uatapp.webbfontaine.ci/gws/api/sign")))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body([0, 0, 0, 0, 0] as byte[]))
        restTemplate.postForEntity(_,_,byte[].class) >> new ResponseEntity([0, 0, 0, 0, 0] as byte[],HttpStatus.OK)
        service.restTemplate = restTemplate
    }

    void "Test sign PDF file report"(){
        given: "Set test File"
        File pdfFile = new File(new FileSystemResource("/com/webbfontaine/ephyto/resources/sign/PdfSigningTest.pdf").getURI().getPath())
        when: "Call Sign()"
        def response = service.sign(pdfFile,"PDFFile.pdf")
        then: "expected status 200/OK"
        pdfFile.getName() == "PdfSigningTest.pdf"
        response.statusCode == HttpStatus.OK
    }
}
