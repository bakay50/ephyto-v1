package com.webbfontaine.ephyto.workflow


import com.webbfontaine.ephyto.EphytoGenBpmService
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.workflow.operations.OperationClass
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import spock.lang.Specification
import spock.lang.Unroll
import static com.webbfontaine.ephyto.constants.Operations.*
import static com.webbfontaine.ephyto.constants.Statuses.*
import static com.webbfontaine.ephyto.constants.Roles.*
import static com.webbfontaine.ephyto.security.SecurityTestUtils.*
import static com.webbfontaine.ephyto.utils.TestUtils.createEphytoGen
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 24/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@TestMixin(GrailsUnitTestMixin)
class EphytoWorkflowSpec extends Specification {

    private
    static String VIEW_STORED_ROLES = "ROLE_EPHYTO_DECLARANT, ROLE_EPHYTO_TRADER,ROLE_EPHYTO_GOV_OFFICER,ROLE_EPHYTO_ADMINISTRATOR,ROLE_EPHYTO_SUPER_ADMINISTRATOR,ROLE_EPHYTO_GOV_SUPERVISOR"
    private
    static String ALL_ROLES = "ROLE_EPHYTO_DECLARANT, ROLE_EPHYTO_TRADER,ROLE_EPHYTO_GOV_OFFICER,ROLE_EPHYTO_ADMINISTRATOR,ROLE_EPHYTO_SUPER_ADMINISTRATOR,ROLE_EPHYTO_GOV_SUPERVISOR"

    void setupSpec() {
        defineBeans {

            springSecurityService(SpringSecurityService)

            ephytoWorkflow(EphytoWorkflow)

            ephytoGenBpmService(EphytoGenBpmService) {
                springSecurityService = ref('springSecurityService')
            }

            roleHierarchy(RoleHierarchyImpl) {
                hierarchy = ''
            }
        }
    }

    void setup() {
        EphytoGen ephytoGen = createEphytoGen("0001")
    }

    def "test create operations"() {
        when: "retrieving operations for null status"
        def validOperations = workflow.operationsFromStatus(null)

        then: "valid operations are Store and Request"
        validOperations.size() == 3
        validOperations.contains(workflow.operationById(OP_STORE))
        validOperations.contains(workflow.operationById(OP_REQUEST))
    }

    def "test editStored operations"() {
        when: "retrieving operations for Stored status"
        def validOperations = workflow.operationsFromStatus(ST_STORED)

        then: "there should be 1 OperationClass that contains 3 operations"
        validOperations.size() == 3
        validOperations[0] instanceof OperationClass
        validOperations[0].getOperations().size() == 3
        validOperations[0].id == OC_EDIT_STORED
        workflow.operationById(OI_UPDATE_STORED) != null
        workflow.operationById(OI_REQUEST_STORED) != null
        workflow.operationById(OA_DELETE) != null
        workflow.operationById(OI_DIRECT_DELETE) != null
        workflow.operationById(OI_VIEW_STORED) != null
    }

    def "test create operations for user with valid roles"() {
        given: "a new instance of ephytoGen"
        def ephytoGen = new EphytoGen()

        when: "init operations for new ephytoGen instance"
        authenticateUser(ROLE_EPHYTO_DECLARANT)

        byPassIsLoggedIn()
        setupWorkflow()

        bpmService.initOperations(ephytoGen)

        then: "valid operations for declarant role should be added to ephytoGen instance"
        ephytoGen.operations.findAll { it.id in [OP_STORE, OP_REQUEST] }.size() == 2
    }

    def "test create operations with user invalid role"() {
        given: "new instance of ephytoGen"
        def ephytoGen = new EphytoGen()

        when: "init operations for new ephytoGen instance"
        authenticateUser(ROLE_EPHYTO_GOV_SUPERVISOR)

        byPassIsLoggedIn()
        setupWorkflow()

        bpmService.initOperations(ephytoGen)

        then: "there should be no operations available for user"
        ephytoGen.operations.size() == 0
    }

    def "test count of operations available from Stored ephytoGen"() {
        given: "new instance of ephytoGen"
        def ephytoGen = new EphytoGen(status: ST_STORED)

        when: "init operations for stored ephytoGen"
        authenticateUser(ROLE_EPHYTO_DECLARANT)
        byPassIsLoggedIn()
        setupWorkflow()

        bpmService.initOperations(ephytoGen)

        then: "there should be 3 operations from ephytoGen.operations available"
        ephytoGen.operations.size() == 3
        ephytoGen.operations[0].getOperations().size() == 3
    }

    @Unroll("test operations for #status ephytoGen with #role")
    def "test operations for #status ExemptionGen with #role"() {
        given: "instance of ephytoGen with #status"
        def ephytoGen = new EphytoGen(status: status)

        when: "init operations for ExemptionGen"
        authenticateUser(role)
        byPassIsLoggedIn()
        setupWorkflow()

        bpmService.initOperations(ephytoGen)

        then: "Operation #operationId should be available"
        workflow.operationById(operationId) != null

        where:
        status                | operationId                             || role
        null                  | OP_STORE                                || ROLE_EPHYTO_DECLARANT
        null                  | OP_STORE                                || ROLE_EPHYTO_TRADER
        null                  | OP_REQUEST                              || ROLE_EPHYTO_DECLARANT
        null                  | OP_REQUEST                              || ROLE_EPHYTO_TRADER
        ST_STORED             | OI_UPDATE_STORED                        || ROLE_EPHYTO_DECLARANT
        ST_STORED             | OI_UPDATE_STORED                        || ROLE_EPHYTO_TRADER
        ST_STORED             | OI_REQUEST_STORED                       || ROLE_EPHYTO_DECLARANT
        ST_STORED             | OI_REQUEST_STORED                       || ROLE_EPHYTO_TRADER
        ST_STORED             | OA_DELETE                               || ROLE_EPHYTO_DECLARANT
        ST_STORED             | OA_DELETE                               || ROLE_EPHYTO_TRADER
        ST_STORED             | OA_DELETE                               || ROLE_EPHYTO_DECLARANT
        ST_STORED             | OA_DELETE                               || ROLE_EPHYTO_TRADER
        ST_QUERIED            | OI_UPDATE_QUERIED                       || ROLE_EPHYTO_DECLARANT
        ST_QUERIED            | OI_UPDATE_QUERIED                       || ROLE_EPHYTO_TRADER
        ST_QUERIED            | OI_CANCEL_QUERIED                       || ROLE_EPHYTO_DECLARANT
        ST_QUERIED            | OI_CANCEL_QUERIED                       || ROLE_EPHYTO_TRADER
        ST_QUERIED            | OI_REQUEST_QUERIED                      || ROLE_EPHYTO_DECLARANT
        ST_QUERIED            | OI_REQUEST_QUERIED                      || ROLE_EPHYTO_TRADER
        ST_REQUESTED          | OI_APPROVE_REQUESTED                    || ROLE_EPHYTO_GOV_OFFICER
        ST_REQUESTED          | OI_QUERY_REQUESTED                      || ROLE_EPHYTO_GOV_OFFICER
        ST_REQUESTED          | OI_REJECT_REQUESTED                     || ROLE_EPHYTO_GOV_OFFICER
    }

    @Unroll("test View operation by Role: #status can by viewed by #roles")
    def "test view operation by role"() {
        given: "instance of ephytoGen"
        def ephytoGen = new EphytoGen(status: status)

        when: "init operations for ephytoGen"
        authenticateUser(roles)
        byPassIsLoggedIn()
        setupWorkflow()

        bpmService.initOperations(ephytoGen)

        then: "View operetion for #status should exist"
        workflow.operationById(operationId) != null

        where:
        status                      | operationId                      || roles
        ST_STORED                   | OI_VIEW_STORED                   || VIEW_STORED_ROLES
        ST_REQUESTED                | OI_VIEW_REQUESTED                || ALL_ROLES
        ST_REJECTED                 | OI_VIEW_REJECTED                 || ALL_ROLES
        ST_QUERIED                  | OI_VIEW_QUERIED                  || ALL_ROLES
        ST_CANCELED                 | OI_VIEW_CANCELED                 || ALL_ROLES
        ST_APPROVED                 | OI_VIEW_APPROVED                 || ALL_ROLES
//      ST_DELIVERED                | OI_VIEW_DELIVERED                || ALL_ROLES(SWEPHYCI-136)
    }

    private def getWorkflow() {
        return grailsApplication.mainContext.getBean(EphytoWorkflow)
    }

    private def getBpmService() {
        return grailsApplication.mainContext.getBean(EphytoGenBpmService)
    }

    private def byPassIsLoggedIn() {
        grailsApplication.mainContext.springSecurityService.metaClass.isLoggedIn = {
            true
        }
    }

    private def setupWorkflow() {
        bpmService.metaClass.getWorkflow = {
            workflow
        }
    }

}
