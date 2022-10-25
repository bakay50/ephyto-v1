package com.webbfontaine.ephyto

import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.UserPropertyConstants
import com.webbfontaine.grails.plugins.validation.rules.Rule
import grails.util.Holders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.gen.EphytoGen
import static com.webbfontaine.ephyto.constants.Operations.OC_EDIT_APPROVED
import static com.webbfontaine.ephyto.constants.Operations.OC_EDIT_QUERIED
import static com.webbfontaine.ephyto.constants.Operations.OC_EDIT_REQUESTED
import static com.webbfontaine.ephyto.constants.Operations.OC_EDIT_STORED
import static com.webbfontaine.ephyto.constants.Operations.OI_APPROVE_REQUESTED
import static com.webbfontaine.ephyto.constants.Operations.OI_DELIVER_APPROVED
import static com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils.isGovOfficer
import static com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils.isGovSeniorOfficer

/**
 *
 * @author sylla
 */
class BusinessLogicUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessLogicUtils.class);

    public static boolean canPrint(EphytoGen ephytoGen) {
        return Statuses.PERMITTED_STATUSES_FOR_PRINT.contains(ephytoGen?.status)
    }
    public static boolean canPrintDraft(EphytoGen ephytoGen) {
        return Statuses.PERMITTED_STATUSES_FOR_DRAFT_PRINT.contains(ephytoGen?.status)
    }
    static List getUserPropertyValueByBeanAlias(String property) {
        def values = []
        if (isUserPropertyControlEnabled()) {
            String userPropertyValue = getUserProperty(property)
            if (userPropertyValue) {
                values = userPropertyValue?.split(":") as List
            }
        }
        values
    }

    static String getUserProperty(String propertyName) {
        EphytoSecurityService ephytoSecurityService = Holders.applicationContext.getBean(EphytoSecurityService)
        String userPropertyValue = ephytoSecurityService.getUserProperty(propertyName)
        userPropertyValue
    }

    static String hasDefaultProperty(String userProp) {
        def result = hasUserProperties(userProp)
        def hasDefaultProperty = result.hasUserProperties && result.userPropValues?.size() == 1
        def defaultPropertyValue = hasDefaultProperty?result.userPropValues[0]:null
        return defaultPropertyValue
    }

    static hasUserProperties(String userProp) {
        List userPropValues = getUserPropertyValueByBeanAlias(userProp)
        if(UserPropertyConstants.ALL in userPropValues){
            return [hasUserProperties: false, userPropValues:null, hasDefaultProperty:false]
        }else{
            boolean hasUserProperties = userPropValues && !(UserPropertyConstants.ALL in userPropValues)
            boolean hasDefaultProperty = hasUserProperties && userPropValues?.size() == 1
            return [hasUserProperties: hasUserProperties, userPropValues: userPropValues, hasDefaultProperty:hasDefaultProperty]
        }

    }

    static void executeSetOfRules(Collection setOfRules, RuleContext ruleContext) {
        setOfRules?.each { Rule rule ->
            LOGGER.trace("applying rule {}", rule.getClass().getName())
            rule.apply(ruleContext)
        }
    }

    static isUserPropertyControlEnabled() {
        return Holders.config.userProperty.checkingEnabled
    }

    static String updateLabelStartedOperations(domainInstance){
        if([OC_EDIT_STORED,OC_EDIT_QUERIED,OC_EDIT_REQUESTED,OC_EDIT_APPROVED].contains(domainInstance?.startedOperation)){
            return EphytoConstants.MODIFY
        }else if([OI_APPROVE_REQUESTED].contains(domainInstance?.startedOperation)){
            return EphytoConstants.APPROVE
        }else if([OI_DELIVER_APPROVED].contains(domainInstance?.startedOperation)){
            return EphytoConstants.DELIVER
        }
        else if([EphytoConstants.CREATE].contains(domainInstance?.startedOperation)){
            return EphytoConstants.CREATE_EPHYTO
        }else if([EphytoConstants.SHOW].contains(domainInstance?.startedOperation)){
            return EphytoConstants.VIEW
        }else if([EphytoConstants.DELETE].contains(domainInstance?.startedOperation)){
            return EphytoConstants.DELETE
        }else{
            return EphytoConstants.VIEW
        }

    }
    static String initdefaultValueTypeTreatment(){
        return EphytoConstants.NATURE_TRAITEMENT
    }

    static isApprovedByGovAgent(EphytoGen ephytoGenInstance){
        return !((isGovOfficer() || isGovSeniorOfficer()) && ephytoGenInstance?.startedOperation == OI_APPROVE_REQUESTED && ephytoGenInstance?.modeOfTransportCode == "4")
    }

}

