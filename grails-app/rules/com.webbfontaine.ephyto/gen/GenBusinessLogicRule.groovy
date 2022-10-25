package com.webbfontaine.ephyto.gen

import com.webbfontaine.ephyto.BusinessLogicUtils
import com.webbfontaine.ephyto.WebRequestUtils
import com.webbfontaine.ephyto.erimm.PackageCode
import com.webbfontaine.grails.plugins.rimm.loc.Port
import com.webbfontaine.grails.plugins.rimm.tar.TariffCode
import static com.webbfontaine.ephyto.constants.Operations.OI_QUERY_REQUESTED
import static com.webbfontaine.ephyto.constants.Operations.OI_REJECT_REQUESTED
import static com.webbfontaine.ephyto.constants.Operations.OI_REQUEST_STORED
import static com.webbfontaine.ephyto.constants.Operations.OI_UPDATE_QUERIED
import static com.webbfontaine.ephyto.constants.Operations.OI_REQUEST_QUERIED
import static com.webbfontaine.ephyto.constants.Operations.OP_QUERY
import static com.webbfontaine.ephyto.constants.Operations.OP_REJECT
import static com.webbfontaine.ephyto.constants.Operations.OP_UPDATE
import static com.webbfontaine.ephyto.constants.UserPropertyConstants.*

import com.webbfontaine.ephyto.gen.checkings.SadRule
import com.webbfontaine.ephyto.gen.checkings.ProductRule
import com.webbfontaine.ephyto.gen.checkings.NamesAndPartiesRule
import com.webbfontaine.ephyto.gen.checkings.OfficeAccessRule


import com.webbfontaine.grails.plugins.rimm.cmp.Company
import com.webbfontaine.grails.plugins.rimm.country.Country
import com.webbfontaine.grails.plugins.rimm.cuo.CustomsOffice
import com.webbfontaine.grails.plugins.rimm.mot.ModeOfTransport
import com.webbfontaine.ephyto.erimm.Product
import com.webbfontaine.grails.plugins.rimm.dec.Declarant
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch
import com.webbfontaine.ephyto.gen.checkings.ExporterRule
import com.webbfontaine.ephyto.gen.checkings.DeclarantRule
import com.webbfontaine.ephyto.gen.checkings.EphytoTreatmentCheckRule
import com.webbfontaine.ephyto.gen.checkings.EphytoCheckMaxTreatmentRule
import com.webbfontaine.ephyto.gen.checkings.GoodValidationRule
import com.webbfontaine.ephyto.gen.checkings.LogMessageRule
import com.webbfontaine.ephyto.gen.setters.EphytoRefValueSetterRule
import static com.webbfontaine.ephyto.constants.Operations.OP_REQUEST
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.VALIDATE_INSTANCE
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getParam
import static com.webbfontaine.ephyto.constants.Operations.OP_STORE
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.gen.checkings.GenMandatoryFieldRule
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 02/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class GenBusinessLogicRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenBusinessLogicRule.class);

    private static final def SETTER_RULES = [
            new EphytoRefValueSetterRule(ModeOfTransport, 'modeOfTransportCode', true, false, 'modeOfTransportName', 'code', 'description', false),
            new EphytoRefValueSetterRule(Country, 'countryOfDestinationCode', true, false, 'countryOfDestinationName', 'code', 'description', false),
            new EphytoRefValueSetterRule(Country, 'countryOfOriginCode', true, false, 'countryOfOriginName', 'code', 'description', false),
            new EphytoRefValueSetterRule(Port, 'placeOfUnloadingCode', true, false, 'placeOfUnloadingName', 'code', 'description', false),
            new EphytoRefValueSetterRule(TariffCode, 'commodityCode', true, false, 'commodityDescription', 'code', 'description', false),
            new EphytoRefValueSetterRule(PackageCode, 'packageCode', true, false, 'packageName', 'code', 'description', false),
            new NamesAndPartiesRule('exporterCode', 'exporterName', 'exporterAddress', 'description', Company, TIN),
            new NamesAndPartiesRule('declarantCode', 'declarantName', 'declarantAddress', 'description', Declarant, DEC),
            new OfficeAccessRule('customsClearanceOfficeCode', 'customsClearanceOfficeName', 'description', CustomsOffice, OFFICE)
    ]


    private static final def SETTER_RULES_TREATMENT = [
            new EphytoTreatmentCheckRule(),
            new EphytoCheckMaxTreatmentRule()
    ]


    private static final def SETTER_RULES_PRODUCT = [
            new ProductRule('productTypeCode', 'productTypeName','botanicalName','name',Product)
    ]

    private static final REQUEST_RULES = [

    ]

    private static final CREATE_UPDATE_RULES = [
            new ExporterRule(),
            new DeclarantRule(),
            new GenMandatoryFieldRule()
    ]

    private static final REQUEST_UPDATE_RULES = [
            new GoodValidationRule()
    ]

    private static final CREATE_SAD_RULES = [
           new SadRule()
    ]

    private static final QUERY_REJECT_RULES = [
            new LogMessageRule()
    ]

    @Override
    void apply(RuleContext ruleContext) {
        EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)

        if (ephytoGen.notProperlyInitialized || !validateInstance) {
            return
        }

        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        LOGGER.debug("id = {}, cid = {}. Items = {}. Started. ", ephytoGen.id, conversationId, ephytoGen?.itemGoods?.size())

        BusinessLogicUtils.executeSetOfRules(SETTER_RULES, ruleContext)

        if(haveBotanicalName(ephytoGen) || isMangueBotanicalName(ephytoGen)){
            BusinessLogicUtils.executeSetOfRules(SETTER_RULES_PRODUCT, ruleContext)
        }
        if(byPassCheckingTreatment(ephytoGen)){
            BusinessLogicUtils.executeSetOfRules(SETTER_RULES_TREATMENT, ruleContext)
        }
        if (params.validationRequired) {
            //BusinessLogicUtils.executeSetOfRules(COMMON_RULES, ruleContext)
            if ([OP_STORE, OP_REQUEST, OP_UPDATE].contains(commitOperation))  {
                BusinessLogicUtils.executeSetOfRules(CREATE_UPDATE_RULES, ruleContext)
            }
            if([OP_REQUEST,OI_REQUEST_STORED,OI_REQUEST_QUERIED].contains(commitOperation)){
                BusinessLogicUtils.executeSetOfRules(REQUEST_RULES, ruleContext)
            }
            if([OP_REQUEST,OI_REQUEST_STORED,OI_REQUEST_QUERIED].contains(commitOperation)){
                BusinessLogicUtils.executeSetOfRules(CREATE_SAD_RULES, ruleContext)
            }
            if([OP_REQUEST, OI_REQUEST_STORED, OI_REQUEST_QUERIED, OI_UPDATE_QUERIED].contains(commitOperation)){
                BusinessLogicUtils.executeSetOfRules(REQUEST_UPDATE_RULES, ruleContext)
            }

            if ([OP_QUERY,OP_REJECT, OI_QUERY_REQUESTED, OI_REJECT_REQUESTED].contains(commitOperation)){
                BusinessLogicUtils.executeSetOfRules(QUERY_REJECT_RULES, ruleContext)
            }
        }

    }

    private static def getValidateInstance() {
        getParam(VALIDATE_INSTANCE)
    }

    private static getConversationId() {
        WebRequestUtils.getConversationId()
    }

    private static getParams() {
        WebRequestUtils.getParams()
    }

    private static getCommitOperation() {
        WebRequestUtils.getCommitOperation()
    }


    private static boolean byPassCheckingTreatment(EphytoGen ephytoGen){
        if(disableCheckingOnProduct(ephytoGen)){
            return false
        }else if(haveBotanicalName(ephytoGen) || forceCheckingOnProduct(ephytoGen)){
            return true
        }
    }

    private static boolean forceCheckingOnProduct(EphytoGen ephytoGen){
        return ephytoGen?.productTypeCode in EphytoConstants.PRODUCT_ALLOW_TREATMENT && ephytoGen?.treatment.equals(EphytoConstants.YES)
    }

    private static boolean disableCheckingOnProduct(EphytoGen ephytoGen){
        return ephytoGen?.productTypeCode in EphytoConstants.PRODUCT_ALLOW_TREATMENT && ephytoGen?.treatment.equals(EphytoConstants.NO)
    }

    private static boolean haveBotanicalName(EphytoGen ephytoGen){
        return (ephytoGen?.productTypeCode in EphytoConstants.ALLOW_CHECKING_TREATMENT)
    }

    private static boolean isMangueBotanicalName(EphytoGen ephytoGen){
        return (ephytoGen?.productTypeCode?.equalsIgnoreCase(EphytoConstants.MANGUE))
    }
}
