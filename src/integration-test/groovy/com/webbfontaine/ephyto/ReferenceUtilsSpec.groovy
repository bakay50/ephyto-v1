package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.constants.UserPropertyConstants
import com.webbfontaine.grails.plugins.rimm.cmp.Company
import com.webbfontaine.grails.plugins.rimm.dec.Declarant
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification

@Integration
@Rollback
class ReferenceUtilsSpec extends Specification {

    def grailsApplication
    def springSecurityService
    def ephytoSecurityService

    void setupSpec() {
        ReferenceUtils.metaClass.static.getFieldsFromRimmData = { def domain, def userPropValues ->
            return initRimmData(domain, userPropValues);
        }

        BusinessLogicUtils.metaClass.static.isUserPropertyControlEnabled = {
            true
        }
    }

    void setup() {
        grailsApplication.mainContext.springSecurityService.metaClass.isLoggedIn = {
            true
        }
        grailsApplication.mainContext.ephytoSecurityService.metaClass.getUserOwnerGroup = {
            'ALL'
        }
        grailsApplication.mainContext.ephytoSecurityService.metaClass.getCompanyName = {
            'Webb Fontaine'
        }

        TypeCastUtils.metaClass.datePattern = {
            return 'dd/MM/yyyy'
        }

        BusinessLogicUtils.metaClass.static.isUserPropertyControlEnabled = {
            true
        }
    }



    def "test getDeclarantFields : User has DEC properties"() {
        when:
        springSecurityService = [authentication:[principal: [TIN: "ALL", DEC: "00155N:22409K", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService

        def result = BusinessLogicUtils.hasUserProperties(UserPropertyConstants.DEC)
        def userPropValues = result.userPropValues
        def hasProperties = result.hasUserProperties
        def fields = ReferenceUtils.getDeclarantFields(userPropValues)

        then:
        fields.size() > 0
        hasProperties == true
    }

    def "test getDeclarantFields: User has no property"() {
        when:
        springSecurityService = [authentication:[principal: [TIN: "ALL", DEC: "ALL", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService

        def result = BusinessLogicUtils.hasUserProperties(UserPropertyConstants.DEC)
        def userPropValues = result.userPropValues
        def hasProperties = result.hasUserProperties
        def fields = ReferenceUtils.getDeclarantFields(userPropValues)

        then:
        fields.size() == 0
        hasProperties == false
    }

    def "test getDeclarantFields: User has default property"() {
        when:
        springSecurityService = [authentication:[principal: [TIN: "ALL", DEC: "22409K", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService

        def result = BusinessLogicUtils.hasUserProperties(UserPropertyConstants.DEC)
        def userPropValues = result.userPropValues
        def hasProperties = result.hasUserProperties
        def hasDefaultProperty = result.hasDefaultProperty
        def fields = ReferenceUtils.getDeclarantFields(userPropValues)

        then:
        fields.size() == 1
        hasProperties == true
        hasDefaultProperty == true
    }

    def "test getExporterFields : User has TIN properties"() {
        when:
        springSecurityService = [authentication:[principal: [TIN: "0004308X:0012401I", DEC: "ALL", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService

        def result = BusinessLogicUtils.hasUserProperties(UserPropertyConstants.TIN)
        def userPropValues = result.userPropValues
        def hasProperties = result.hasUserProperties
        def fields = ReferenceUtils.getCompanyFields(userPropValues)

        then:
        fields.size() > 0
        hasProperties == true
    }

    def "test getExporterFields: User has no property"() {
        when:
        springSecurityService = [authentication:[principal: [TIN: "ALL", DEC: "ALL", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService

        def result = BusinessLogicUtils.hasUserProperties(UserPropertyConstants.TIN)
        def userPropValues = result.userPropValues
        def hasProperties = result.hasUserProperties
        def fields = ReferenceUtils.getCompanyFields(userPropValues)

        then:
        fields.size() == 0
        hasProperties == false
    }

    def "test getExporterFields: User has default property"() {
        when:
        springSecurityService = [authentication:[principal: [TIN: "0012401I", DEC: "ALL", authorities: [com.webbfontaine.ephyto.constants.Roles.ROLE_EPHYTO_DECLARANT]]]]
        ephytoSecurityService.springSecurityService = springSecurityService

        def result = BusinessLogicUtils.hasUserProperties(UserPropertyConstants.TIN)
        def userPropValues = result.userPropValues
        def hasProperties = result.hasUserProperties
        def hasDefaultProperty = result.hasDefaultProperty
        def fields = ReferenceUtils.getCompanyFields(userPropValues)

        then:
        fields.size() == 1
        hasProperties == true
        hasDefaultProperty == true
    }



    def initRimmData(domain, userPropValues) {
        def domainName = domain?.getSimpleName()
        def dataList = "get${domainName}List"(userPropValues)
        dataList
    }


    def getCompanyList(userPropValues) {
        Company comp1 = new Company(code: "0004308X", dov: new Date().clearTime(), description: "WF-0004308X", address1: 'add1', address2: 'add2', address3: 'add3', address4: 'add4')
        Company comp2 = new Company(code: "0012401I", dov: new Date().clearTime(), description: "WF-0012401I", address1: 'add1', address2: 'add2', address3: 'add3', address4: 'add4')
        List dataList = new ArrayList()
        dataList.add(comp1)
        dataList.add(comp2)
        return getDataList(dataList, userPropValues)
    }



    def getDeclarantList(userPropValues) {
        Declarant dec1 = new Declarant(code: "00155N", dov: new Date().clearTime(), description: "WF-00155N", iff: 'IFF', address1: 'add1', address2: 'add2', address3: 'add3', address4: 'add4', isBroker: true)
        Declarant dec2 = new Declarant(code: "22409K", dov: new Date().clearTime(), description: "WF-22409K", iff: 'IFF', address1: 'add1', address2: 'add2', address3: 'add3', address4: 'add4', isBroker: true)
        List dataList = new ArrayList()
        dataList.add(dec1)
        dataList.add(dec2)
        return getDataList(dataList, userPropValues)
    }

     def getDataList(dataList, userPropValues) {
         List newDataList = dataList.findAll { it ->
             it.code in userPropValues
         }
         return newDataList
     }

}
