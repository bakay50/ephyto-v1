package com.webbfontaine.ephyto.gen

import com.webbfontaine.ephyto.BusinessLogicService
import com.webbfontaine.ephyto.EphytoSecurityService
import com.webbfontaine.ephyto.IdGenerator
import com.webbfontaine.ephyto.InitFieldsValueInUTF8Service
import com.webbfontaine.ephyto.TypeCastUtils
import com.webbfontaine.ephyto.command.SearchCommand
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.constants.Roles
import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.conversation.ConversationService
import com.webbfontaine.grails.plugins.utils.concurrent.SynchronizationService
import com.webbfontaine.grails.plugins.validation.rules.DocVerificationService
import com.webbfontaine.grails.plugins.validation.rules.events.DeepVerifyFinishedEventPublisher
import grails.core.GrailsApplication
import grails.plugin.springsecurity.SpringSecurityService
import grails.testing.mixin.integration.Integration
import grails.web.context.ServletContextHolder
import grails.web.servlet.mvc.GrailsParameterMap
import org.grails.plugins.testing.GrailsMockHttpServletRequest
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.GrailsApplicationAttributes
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import grails.gorm.transactions.Rollback
import org.springframework.web.context.request.RequestContextHolder
import spock.lang.Shared
import spock.lang.Specification

import static com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser
import static com.webbfontaine.ephyto.utils.IntegrationTestUtils.*

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 25/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@Integration
@Rollback
class EphytoGenControllerIntegrationSpec extends Specification {

    def controller = new EphytoGenController()

    def springSecurityService

    @Shared
    IdGenerator idGenerator

    @Autowired
    GrailsApplication grailsApplication

    @Autowired
    SynchronizationService synchronizationService

    GrailsParameterMap params

    ConversationService conversationService = new ConversationService()
    BusinessLogicService businessLogicService = new BusinessLogicService()
    DocVerificationService docVerificationService = new DocVerificationService()
    EphytoSecurityService ephytoSecurityService = new EphytoSecurityService()
    InitFieldsValueInUTF8Service initFieldsValueInUTF8Service = new InitFieldsValueInUTF8Service()
    EphytoGenSearchService ephytoGenSearchService = new EphytoGenSearchService()

    def setupData() {
        grailsApplication.mainContext.springSecurityService.metaClass.isLoggedIn = {
            true
        }
        idGenerator = new IdGenerator(
                ephytoGen: 1,
                itemGood: 1,
                ephytoAtt: 1,
                ephytoTreatment: 1
        )
        IdGenerator.withNewSession {
            idGenerator.save(failOnError:false)
        }
        fillEphytoCreate()
        fillEphytoCreate1()
        fillEphytoCreate2()
        fillEphytoCreate3()
    }


    void setup() {
        setupRequestContextHandler()
        grailsApplication.mainContext.springSecurityService.metaClass.isLoggedIn = {
            true
        }
        grailsApplication.mainContext.ephytoSecurityService.metaClass.getUserOwnerGroup = {
            'ALL'
        }
        grailsApplication.mainContext.ephytoSecurityService.metaClass.getCompanyName = {
            'Webb Fontaine'
        }
        grailsApplication.mainContext.conversationService.metaClass.getEphytoGenFromConversation = {
            new EphytoGen()
        }

        controller.conversationService = conversationService
        controller.businessLogicService = businessLogicService
        controller.ephytoGenSearchService = ephytoGenSearchService
        controller.docVerificationService = docVerificationService
        controller.initFieldsValueInUTF8Service = initFieldsValueInUTF8Service
        springSecurityService = [authentication: [principal: [TDR: "ALL", DEC: "ALL", INS: 'ALL', BRK: 'ALL', CPB: 'ALL', ownerGroup: 'ALL', authorities: [Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        TypeCastUtils.metaClass.datePattern = {
            return 'dd/MM/yyyy'
        }
    }

    def "Test the create action returns the correct model"() {
        when: "create action is executed"
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT)
        def model = controller.create()

        then: "response model should be properly created"
        controller.getViewUri('/ephytoGen/edit/')
    }

    def "Test save action for Store operation should store Ephyto properly"() {
        when: "create action is executed by authorized user"
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", username: 'trader', password: 'trader123', authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        controller.synchronizationService = synchronizationService
        controller.setDocVerificationService(setDocVerificationService())
        controller.request.method = 'POST'
        fillEphyto(controller.params)
        fillStoreOperationParams(controller.params)

        EphytoGen.withTransaction {
            controller.create()
            controller.save()
        }

        then: "An ephyto instance must be properly Stored"
        EphytoGen.withTransaction {
            EphytoGen.get(controller.flash.ephtytoToShow.id).status == 'Stored'
        }

        then: "Owner of document must be properly set"
        EphytoGen.withTransaction {
            EphytoGen.get(controller.flash.ephtytoToShow.id).ownerUser == 'trader'
        }

    }

    def "Test save action for Store operation should set correct user owner"() {
        when: "create action is executed by authorized user"
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", username: 'trader', password: 'trader123', authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        controller.synchronizationService = synchronizationService
        controller.setDocVerificationService(setDocVerificationService())
        controller.request.method = 'POST'
        fillEphyto(controller.params)
        fillStoreOperationParams(controller.params)

        EphytoGen.withTransaction {
            controller.create()
            controller.save()
        }


        then: "Owner of document must be set to trader user"
        EphytoGen.withTransaction {
            EphytoGen.get(controller.flash.ephtytoToShow.id).ownerUser == 'trader'
        }

    }

    def "Test edit action for Stored EphytoGen"() {
        when: "create action is executed by authorized user"
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", username: 'trader', password: 'trader123', authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        controller.synchronizationService = synchronizationService
        controller.setDocVerificationService(setDocVerificationService())
        controller.request.method = 'POST'
        fillEphyto(controller.params)
        fillStoreOperationParams(controller.params)
        controller.metaClass.'static'.ephytoGenById(_) >> {
            return setupData()
        }
        EphytoGen.withTransaction {
            controller.update()
        }
        then: "ephyto instance to show should be available"
        controller.flash.ephtytoToShow != null
        controller.getViewUri("/ephytoGen/show/${controller.flash.ephtytoToShow?.id}")

    }





    def "Test show action after update operation should return cached data"() {
        when:
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", username: 'trader', password: 'trader123', authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        setupData()
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        controller.synchronizationService = synchronizationService
        controller.conversationService = conversationService
        controller.setDocVerificationService(setDocVerificationService())
        // prepare save requirements
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        GrailsParameterMap params
        params = new GrailsParameterMap([id: '1',status:'Stored'], request)
        params.put("commitOperation",Operations.OI_UPDATE_STORED)
        com.webbfontaine.grails.plugins.utils.WebRequestUtils.metaClass.static.getParams = {
            return params = updateParams(params)
        }
        controller.request.method = 'POST'
        fillStoreOperationParams(controller.params)
        controller.params.put("status", "Stored")
        controller.params.commitOperation = Operations.OI_UPDATE_STORED
        controller.params.commitOperationName = Operations.OP_UPDATE

        controller.params.put("id",'1')

        controller.metaClass.'static'.ephytoGenById  =  {def id ->
            return new EphytoGen(controller.params).save(failOnError:false)
        }
        EphytoGen.withTransaction {
            controller.update()
        }

        then: "show action should respond with cached data"
        controller.flash.ephtytoToShow != null
        controller.getViewUri("/ephytoGen/show/${controller.flash.ephtytoToShow.id}")
    }

    def "Test search action"() {
        when: "search action is executed by authorized user"
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", username: 'trader', password: 'trader123', authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        controller.request.method = 'POST'
        fillSearchParams(controller.params)
        controller.initFieldsValueInUTF8Service = initFieldsValueInUTF8Service
        def searchCommand = new SearchCommand()
        controller.search(searchCommand)
        then: "model should be properly set"
        controller.modelAndView.model.containsKey('actionsTemplate')
        controller.modelAndView.model.containsKey('searchCommand')
        controller.modelAndView.model.containsKey('resultList')
        controller.modelAndView.model.containsKey('totalCount')
        controller.modelAndView.model.containsKey('searchResultMessage')
        when: "user inputs value to a search criteria"
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        controller.request.method = 'POST'
        fillSearchParams(controller.params)
        searchCommand.phytosanitaryCertificateRef = "CERT001"
        controller.params.phytosanitaryCertificateRef = "CERT001"
        controller.search(searchCommand)
        then: "model should be properly set"
        controller.flash.searchResultMessage == controller.modelAndView.model.searchResultMessage

    }


    def "Test save action for Request operation should set exemption status to Requested"() {
        given: "authenticated trader creates a new exemption doc"
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", username: 'trader', password: 'trader123', authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        controller.synchronizationService = synchronizationService
        controller.conversationService = conversationService
        controller.setDocVerificationService(setDocVerificationService())
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        GrailsParameterMap params
        params = new GrailsParameterMap([id:'8',commitOperation:'RequestStored',commitOperationName:'RequestStored',status:'Stored',previousDocReference:null], request)
        params.put("commitOperation",Operations.OI_REQUEST_STORED)
        com.webbfontaine.grails.plugins.utils.WebRequestUtils.metaClass.static.getParams = {
            return params = updateParams(params)
        }
        fillEphyto(controller.params)
        fillRequestFromNull(controller.params)

        EphytoGen ephytoGen = new EphytoGen()
        ephytoGen.addItem(new ItemGood(fillGoodParams()))


        // set correct request method
        controller.request.method = 'POST'
        controller.params.commitOperation = Operations.OI_UPDATE_STORED
        controller.params.commitOperationName = Operations.OP_UPDATE


        when: 'save action is executed'
        EphytoGen.withTransaction {
            controller.save()
        }
        then: 'status of exemption should be Requested'
        controller.flash.operationDoneMessage != null
        controller.flash.ephtytoToShow != null
        controller.flash.ephtytoToShow.status == Statuses.ST_REQUESTED
    }


    def "Test edit action for Stored Ephyto"() {
        when:
        springSecurityService = [authentication: [principal: [TIN: "ALL", DEC: "ALL", username: 'trader', password: 'trader123', authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService
        com.webbfontaine.ephyto.security.SecurityIntegrationTestUtils.authenticateUser(com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT, [username: 'trader', password: 'trader123'])
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        controller.request.method = 'POST'
        controller.params.requestNumber = 'PTD000001'
        fillEphytoParams(controller.params)
        fillRequestFromNull(controller.params)
        controller.synchronizationService = synchronizationService
        controller.conversationService = conversationService
        controller.setDocVerificationService(setDocVerificationService())
        EphytoGen ephytoGen = new EphytoGen()
        ephytoGen.addItem(new ItemGood(fillGoodParams()))
        controller.params.put("id",'3')
        controller.metaClass.'static'.ephytoGenById(_) >> {
            return setupData()
        }


        EphytoGen.withTransaction {
            controller.update()
        }
        then:
        controller.flash.ephtytoToShow?.id != null
        controller.getViewUri("/ephytoGen/show/${controller.flash.ephtytoToShow?.id}")
    }



    private void fillSearchParams(Map params) {
        params.status = ""
        params.requestNumber = ""
        params.op_requestDate = ""
        params.op_disinfectionCertificateDate = ""
        params.op_dockingPermissionDate = ""
        params.op_phytosanitaryCertificateDate = ""
    }

    private void fillStoreOperationParams(Map params) {
        params.commitOperation = 'Store'
        params.commitOperationName = 'Store'
    }

    private void fillRequestFromNull(Map params) {
        params.commitOperation = 'Request'
        params.commitOperationName = 'Request'
    }

    private void fillEphyto(Map params) {
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "RAS"
        params.commodityCode = "0000011111"
        params.otReference = "0001"
        params.ptReference = "pt001"
        params.productTypeCode = "P001"
        params.dockingPermissionRef = "DOCK0012"
        params.phytosanitaryCertificateRef = ""
        params.dockNumber = "IM"
        params.customsClearanceOfficeCode = "CIAB1"
        params.customsClearanceOfficeName = "Abidjan Port"
        params.declarationSerial = "E"
        params.declarationNumber = "1"
        params.modeOfTransportCode = "2"
    }

    private void fillEphyto_delete(Map params) {
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "RAS"
        params.commodityCode = "0000011111"
        params.otReference = "0001"
        params.ptReference = "pt001"
        params.productTypeCode = "P001"
        params.dockingPermissionRef = "DOCK0012"
        params.phytosanitaryCertificateRef = ""
        params.dockNumber = "IM"
        params.customsClearanceOfficeCode = "CIAB1"
        params.customsClearanceOfficeName = "Abidjan Port"
        params.declarationSerial = "E"
        params.declarationNumber = "1"
        params.modeOfTransportCode = "2"
        params.ownerUser = 'trader'
        params.conversationId = 1201205
    }

    private fillEphytoCreate() {
        Map params = new HashMap()
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "RAS"
        params.commodityCode = "0000011111"
        params.otReference = "0001"
        params.ptReference = "pt001"
        params.productTypeCode = "P001"
        params.dockingPermissionRef = "DOCK0012"
        params.phytosanitaryCertificateRef = ""
        params.dockNumber = "IM"
        params.customsClearanceOfficeCode = "CIAB1"
        params.customsClearanceOfficeName = "Abidjan Port"
        params.declarationSerial = "E"
        params.declarationNumber = "1"
        params.modeOfTransportCode = "2"
        EphytoGen ephyto
        EphytoGen.withTransaction {
            ephyto = new EphytoGen(params).save(failOnError: false)
        }
        return ephyto
    }

    private fillEphytoCreate2() {
        Map params = new HashMap()
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "RAS"
        params.commodityCode = "0000011111"
        params.otReference = "0001"
        params.ptReference = "pt001"
        params.productTypeCode = "P001"
        params.dockingPermissionRef = "DOCK0012"
        params.phytosanitaryCertificateRef = ""
        params.dockNumber = "IM"
        params.customsClearanceOfficeCode = "CIAB1"
        params.customsClearanceOfficeName = "Abidjan Port"
        params.declarationSerial = "E"
        params.declarationNumber = "1"
        params.modeOfTransportCode = "2"
        EphytoGen ephyto
        EphytoGen.withTransaction {
            ephyto = new EphytoGen(params).save(failOnError: false)
        }
        return ephyto
    }

    private fillEphytoCreate3() {
        Map params = new HashMap()
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "RAS0020"
        params.commodityCode = "0000011111"
        params.otReference = "0001"
        params.ptReference = "pt001"
        params.productTypeCode = "P001"
        params.dockingPermissionRef = "DOCK0012"
        params.phytosanitaryCertificateRef = ""
        params.dockNumber = "IM"
        params.customsClearanceOfficeCode = "CIAB1"
        params.customsClearanceOfficeName = "Abidjan Port"
        params.declarationSerial = "E"
        params.declarationNumber = "1"
        params.modeOfTransportCode = "2"
        EphytoGen ephyto
        EphytoGen.withTransaction {
            ephyto = new EphytoGen(params).save(failOnError: false)
        }
        return ephyto
    }

    private static updateParams(def params) {
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "RAS"
        params.commodityCode = "0000011111"
        params.otReference = "0001"
        params.ptReference = "pt001"
        params.productTypeCode = "P001"
        params.dockingPermissionRef = "DOCK0012"
        params.phytosanitaryCertificateRef = ""
        params.dockNumber = "IM"
        params.customsClearanceOfficeCode = "CIAB1"
        params.customsClearanceOfficeName = "Abidjan Port"
        params.declarationSerial = "E"
        params.declarationNumber = "1"
        params.modeOfTransportCode = "2"
        return params
    }

    private fillEphytoCreate1() {
        Map params = new HashMap()
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "RAS"
        params.commodityCode = "0000011111"
        params.otReference = "0001"
        params.ptReference = "pt001"
        params.productTypeCode = "P001"
        params.dockingPermissionRef = "DOCK0012"
        params.phytosanitaryCertificateRef = ""
        params.dockNumber = "IM"
        params.customsClearanceOfficeCode = "CIAB1"
        params.customsClearanceOfficeName = "Abidjan Port"
        params.declarationSerial = "E"
        params.declarationNumber = "1"
        params.modeOfTransportCode = "2"
        EphytoGen ephyto
        EphytoGen.withTransaction {
            ephyto = new EphytoGen(params).save(failOnError: false)
        }
        return ephyto
    }

    private static Map fillGoodParams() {
        Map params = new HashMap()
        params.itemRank = 1
        params.batchNumber = '1'
        params.subBatchNumber = '1'
        params.quantity = 2
        params.grossWeight = 2
        params.netWeight = 4
        return params
    }

    def DocVerificationService setDocVerificationService() {
        DeepVerifyFinishedEventPublisher deepVerifyFinishedEventPublisher = new DeepVerifyFinishedEventPublisher()
        deepVerifyFinishedEventPublisher.setApplicationEventPublisher(new ApplicationEventPublisher() {
            @Override
            void publishEvent(ApplicationEvent applicationEvent) {
                //noop
            }

            @Override
            void publishEvent(Object event) {

            }
        })
        DocVerificationService docVerificationService = new DocVerificationService()
        docVerificationService.setDeepVerifyFinishedEventPublisher(deepVerifyFinishedEventPublisher)
        docVerificationService
    }

    private void setupRequestContextHandler() {
        MockHttpServletRequest request = new MockHttpServletRequest()
        GrailsWebRequest webRequest = new GrailsWebRequest(request, new MockHttpServletResponse(), ServletContextHolder.servletContext)
        request.setAttribute(GrailsApplicationAttributes.WEB_REQUEST, webRequest)
        RequestContextHolder.setRequestAttributes(webRequest)
    }


}
