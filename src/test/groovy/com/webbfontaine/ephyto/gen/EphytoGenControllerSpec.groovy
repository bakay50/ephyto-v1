package com.webbfontaine.ephyto.gen

import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.gen.operations.ApproveOperationHandlerService
import com.webbfontaine.grails.plugins.conversation.store.session.SessionStore
import com.webbfontaine.ephyto.*
import com.webbfontaine.ephyto.conversation.ConversationService
import com.webbfontaine.ephyto.gen.operations.StoreOperationHandlerService
import com.webbfontaine.ephyto.workflow.EphytoWorkflow
import com.webbfontaine.grails.plugins.utils.DomainCloningUtils
import com.webbfontaine.grails.plugins.utils.concurrent.SynchronizationService
import com.webbfontaine.grails.plugins.validation.rules.DocVerificationService
import com.webbfontaine.grails.plugins.validation.rules.events.DeepVerifyFinishedEventPublisher
import grails.core.GrailsApplication
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.integration.Integration
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.web.databinding.GrailsWebDataBinder
import org.joda.time.LocalDate
import org.springframework.test.annotation.Rollback
import spock.lang.Specification
import com.webbfontaine.ephyto.security.SecurityTestUtils
import  com.webbfontaine.ephyto.constants.Roles
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 25/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@TestFor(EphytoGenController)
@TestMixin([GrailsUnitTestMixin])
@Mock([EphytoGen])
@Integration
@Rollback
class EphytoGenControllerSpec extends Specification {

    ConversationService conversationService = new ConversationService()
    def sessionStoreService
    def docVerificationService
    def businessLogicService
    def approveOperationHandlerService

    void setupSpec() {
        defineBeans {
            sessionStoreService(SessionStore)
            conversationService(ConversationService) {
                grailsWebDataBinder = new GrailsWebDataBinder(grailsApplication)
            }
            docVerificationService(DocVerificationService)
            ephytoWorkflow(EphytoWorkflow)
            springSecurityService(SpringSecurityService)
            ephytoGenBpmService(EphytoGenBpmService) {
                ephytoWorkflow = ref('ephytoWorkflow')
                springSecurityService = ref('springSecurityService')
            }
            ephytoSecurityService(EphytoSecurityService) {
                springSecurityService = ref('springSecurityService')
            }
            deepVerifyFinishedEventPublisher(DeepVerifyFinishedEventPublisher)
            synchronizationService(SynchronizationService)
            docVerificationService(DocVerificationService)

            storeOperationHandlerService(StoreOperationHandlerService) {
                ephytoGenBpmService = ref('ephytoGenBpmService')
                ephytoWorkflow = ref('ephytoWorkflow')
            }

            approveOperationHandlerService(ApproveOperationHandlerService)
            businessLogicService(BusinessLogicService) {
                ephytoGenBpmService = ref('ephytoGenBpmService')
                ephytoSecurityService = ref('ephytoSecurityService')
                storeOperationHandlerService = ref('storeOperationHandlerService')
            }

        }
    }

    void setup() {
        grailsApplication.mainContext.springSecurityService.metaClass.isLoggedIn = {
            true
        }
        grailsApplication.mainContext.ephytoSecurityService.metaClass.getUserOwnerGroup = {
            'ALL'
        }
        grailsApplication.config.com.webbfontaine.validation.rules.publishDeepVerifyFinishedEvent = false

        controller.conversationService = conversationService
        controller.conversationService.sessionStoreService = sessionStoreService
        GrailsApplication grailsApplication
        GrailsWebDataBinder grailsWebDataBinder = new GrailsWebDataBinder(grailsApplication)
        controller.conversationService.grailsWebDataBinder = grailsWebDataBinder
    }


    def "test the create action to user with create access should redirect to create page"() {
        when: "create action is executed"
        def springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", authorities: [Roles.ROLE_EPHYTO_TRADER]]]]
        controller.businessLogicService.ephytoSecurityService.springSecurityService = springSecurityService
        SecurityTestUtils.authenticateUser(Roles.ROLE_EPHYTO_TRADER)
        controller.businessLogicService = businessLogicService
        controller.create()

        then: "user should be redirected to access-denied page"
        controller.modelAndView.viewName == '/ephytoGen/edit'
    }

    def "test for executeReplaceOperation methode should updated old doc status to Replaced and new doc status to Approved "() {
        when: "create action is executed"
        def springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", authorities: [Roles.ROLE_EPHYTO_GOV_SUPERVISOR]]]]
        controller.businessLogicService = businessLogicService
        controller.businessLogicService.ephytoSecurityService.springSecurityService = springSecurityService
        controller.businessLogicService.approveOperationHandlerService = approveOperationHandlerService
        controller.docVerificationService = docVerificationService

        def ephytoGen = initEphytoInstance()
        EphytoGen newDomainInstance = DomainCloningUtils.deepClone(ephytoGen)
        updateEphytoInstance(newDomainInstance, ephytoGen)

        newDomainInstance = controller.executeReplaceOperation(newDomainInstance)
        def oldDomainInstance = EphytoGen.get(ephytoGen.id)

        then:
        oldDomainInstance.status == Statuses.ST_REPLACED
        newDomainInstance.status == Statuses.ST_APPROVED
    }

    def initEphytoInstance() {
        def ephytoGen = new EphytoGen()
        ephytoGen.requestNumber = "ephytoGen"
        ephytoGen.requestDate = LocalDate.now()
        ephytoGen.userReference = "DOSSIER"
        ephytoGen.otReference = "OT: BICAO 013/ 2016-2017OLAM"
        ephytoGen.customsClearanceOfficeCode = "CIAB3"
        ephytoGen.exporterCode = "000121"
        ephytoGen.commodityCode = "C00"
        ephytoGen.consigneeNameAddress = "Bollore"
        ephytoGen.productTypeCode = "003"
        ephytoGen.declarationSerial = "E"
        ephytoGen.declarationNumber = 25
        ephytoGen.phytosanitaryCertificateRef = "ref1"
        ephytoGen.phytosanitaryCertificateDate = LocalDate.now()
        ephytoGen.requestDate = LocalDate.now()
        def year = ephytoGen.requestDate.getYear()
        ephytoGen.requestYear = year
        ephytoGen.requestNumberSequence = 12131
        ephytoGen.requestNumber = "2345"
        ephytoGen.status = Statuses.ST_APPROVED
        ephytoGen.save()
        ephytoGen
    }

    def updateEphytoInstance(newDomainInstance, ephytoGen) {
        newDomainInstance.tempId = ephytoGen.id
        newDomainInstance.filingDate = LocalDate.now()
        newDomainInstance.phytosanitaryCertificateDate = LocalDate.now()
        newDomainInstance
    }
}
