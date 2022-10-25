package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.workflow.Operation
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ApplicatorBusinessLogicServiceSpec extends Specification implements ServiceUnitTest<ApplicatorBusinessLogicService>, DataTest{
    void setupSpec() {
        mockDomain(Applicator)
    }

    def "test init Applicator for create"() {
        given:
       Applicator applicator = new Applicator(operations: Operation.REGISTER.humanName())

        service.applicatorWorkflowService = [initOperations: { a -> new Applicator(operations: Operation.REGISTER.humanName()) }]
        when:
        def applicatorInstance =  service.initDocumentForCreate(applicator)

        then:
        applicatorInstance.isDocumentEditable
    }
}
