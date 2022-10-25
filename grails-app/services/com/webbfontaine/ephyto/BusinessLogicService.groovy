package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.ephyto.gen.ItemGood
import com.webbfontaine.ephyto.gen.attachment.AttachedFile
import com.webbfontaine.ephyto.gen.attachment.Attachment
import com.webbfontaine.ephyto.operations.OperationHandlerService
import com.webbfontaine.grails.plugins.utils.DomainCloningUtils
import com.webbfontaine.grails.plugins.rimm.cmp.Company
import com.webbfontaine.grails.plugins.rimm.cuo.CustomsOffice
import com.webbfontaine.grails.plugins.rimm.dec.Declarant
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.joda.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.webbfontaine.ephyto.gen.checkings.NamesAndPartiesRule
import com.webbfontaine.ephyto.gen.checkings.OfficeAccessRule
import static com.webbfontaine.ephyto.constants.Operations.OI_CANCEL_APPROVED
import static com.webbfontaine.ephyto.constants.Operations.OI_CANCEL_QUERIED
import static com.webbfontaine.ephyto.constants.Operations.OI_DELETE_STORED
import static com.webbfontaine.ephyto.constants.Operations.OI_REJECT_REQUESTED
import static com.webbfontaine.ephyto.constants.Operations.OI_APPROVE_REQUESTED
import static com.webbfontaine.ephyto.constants.Operations.OI_REQUEST_QUERIED
import static com.webbfontaine.ephyto.constants.Operations.OI_REQUEST_STORED
import static com.webbfontaine.ephyto.constants.Operations.OI_SIGN_APPROVED
import static com.webbfontaine.ephyto.constants.Operations.OI_UPDATE_STORED
import static com.webbfontaine.ephyto.constants.Operations.OP_REQUEST
import static com.webbfontaine.ephyto.constants.Operations.OP_STORE
import static com.webbfontaine.ephyto.constants.Operations.OI_REPLACE_APPROVED
import static com.webbfontaine.ephyto.constants.Operations.OI_REPLACE_SIGNED
import static com.webbfontaine.ephyto.constants.Statuses.ST_REQUESTED

import static com.webbfontaine.ephyto.constants.UserPropertyConstants.DEC
import static com.webbfontaine.ephyto.constants.UserPropertyConstants.TIN
import static com.webbfontaine.ephyto.constants.UserPropertyConstants.OFFICE

import static com.webbfontaine.ephyto.constants.Operations.OP_REPLACE
import static com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils.isGovOfficer
import static com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils.isGovSeniorOfficer
/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 16/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class BusinessLogicService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessLogicService.class)
    public static final String REQUEST_NUMBER_PREFIX = "PTD"
    def ephytoGenBpmService
    def ephytoSecurityService
    def clientOperationHandlerService
    def storeOperationHandlerService
    def requestQueriedOperationHandlerService
    def requestOperationHandlerService
    def rejectOperationHandlerService
    def cancelOperationHandlerService
    def deleteOperationHandlerService
    def approveOperationHandlerService
    def replaceOperationHandlerService
    def signOperationHandlerService
    def initFieldsValueInUTF8Service
    def sequenceNumberService

    EphytoGen initDocumentForCreate(EphytoGen ephytoGen) {
        ephytoGen.isDocumentEditable = true
        ephytoGen.isItemsEditable = true
        ephytoGen.addAttachedDocs = true
        ephytoGen.editAttachedDocs = true
        ephytoGen.addItemGoods = true
        ephytoGen.editItemGoods = true
        ephytoGen.notProperlyInitialized = false
        initTreatment(ephytoGen)
        initAttachments(ephytoGen)
        initEphytoLog(ephytoGen)
        initItemGood(ephytoGen)
        ephytoGen.startedOperation = EphytoConstants.CREATE
        initNameAndParties(ephytoGen)
        ephytoGenBpmService.initOperations(ephytoGen)
        removeReplaceOperOnCreate(ephytoGen)
        return ephytoGen
    }

    def removeReplaceOperOnCreate(domain){
        if (domain?.operations?.id?.contains(Operations.OP_REPLACE)){
            domain.operations.removeAll {
                it.id == Operations.OP_REPLACE
            }
        }
    }

    def initNameAndParties(EphytoGen ephytoGen) {
        def USERPROPERTY_RULES = [
                new NamesAndPartiesRule('exporterCode', 'exporterName', 'exporterAddress', 'description', Company, TIN),
                new NamesAndPartiesRule('declarantCode', 'declarantName', 'declarantAddress', 'description', Declarant, DEC),
                new OfficeAccessRule('customsClearanceOfficeCode', 'customsClearanceOfficeName', 'description', CustomsOffice, OFFICE)
        ]
        RuleContext ruleContext = new RuleContext(ephytoGen, ephytoGen.errors)
        if (!ephytoGen?.status)  {
            BusinessLogicUtils.executeSetOfRules(USERPROPERTY_RULES, ruleContext)
        }
    }

    def initDocumentForDeleteEphyto(EphytoGen ephytoGen){
        ephytoGen.setIsDocumentEditable(false)
        ephytoGen.setAddAttachedDocs(false)
        ephytoGen.setEditAttachedDocs(false)
        ephytoGen.setAddItemGoods(false)
        ephytoGen.setEditItemGoods(false)
        ephytoGen.startedOperation = EphytoConstants.DELETE
        ephytoGenBpmService.initOperationsForDelete(ephytoGen)
        return ephytoGen
    }

    def initDocumentForEdit(EphytoGen ephytoGen, Map params) {
        ephytoGen.setIsItemsEditable(true)
        ephytoGen.setNotProperlyInitialized(false)
        ephytoGen.startedOperation = EphytoConstants.EDIT
        initEphytoLog(ephytoGen)
        ephytoGenBpmService.initOperationsForEdit(ephytoGen, params)


        LOGGER.info("Operations in initDocumentForEdit:${ephytoGen?.operations?.id}")
        if(ephytoGen?.operations?.id?.contains(Operations.OI_REJECT_REQUESTED) || ephytoGen?.operations?.id?.contains(Operations.OI_QUERY_REQUESTED) || ephytoGen?.operations?.id?.contains(Operations.OI_CANCEL_APPROVED)
                || ephytoGen?.operations?.id?.contains(Operations.OI_CANCEL_SIGNED)){
            initEphytoForRejectOrQuery(ephytoGen)
        }else{
            initFieldsValueInUTF8Service.initFieldsValueInUTF8(ephytoGen,params)
            initEphytoIsDocumentEditable(ephytoGen)
        }
    }

    def initItemGood(EphytoGen ephytoGen) {
        if (ephytoGen.itemGoods == null) {
            ephytoGen.itemGoods = new ArrayList<ItemGood>()
        }
    }

    def initTreatment(EphytoGen ephytoGen) {
        if (ephytoGen.itemTreatments == null) {
            ephytoGen.itemTreatments = new ArrayList<EphytoTreatment>()
        }
    }

    def initDocumentForApprove(EphytoGen ephytoGen, Map params){
        ephytoGen.setIsDocumentEditable(false)
        ephytoGen.setAddAttachedDocs(false)
        ephytoGen.setEditAttachedDocs(false)
        ephytoGen.setAddItemGoods(false)
        ephytoGen.setEditItemGoods(false)
        ephytoGen.phytosanitaryCertificateDate= LocalDate.now()  //--- Set Date to current date
        ephytoGenBpmService.initOperationsForApprove(ephytoGen,params)
        LOGGER.info("Operations in initDocumentForApprove:${ephytoGen?.operations?.id}")
    }

    def initDocumentForReplace(EphytoGen ephytoGen, Map params){
        ephytoGen.status=null
        ephytoGen.setIsDocumentEditable(true)
        ephytoGen.setAddAttachedDocs(true)
        ephytoGen.setEditAttachedDocs(true)
        ephytoGen.setAddItemGoods(true)
        ephytoGen.setEditItemGoods(true)
        ephytoGen.startedOperation = EphytoConstants.REPLACE
        ephytoGen.notProperlyInitialized = false

        initEphytoLog(ephytoGen)
        params.put('op',OP_REPLACE)
        ephytoGenBpmService.initOperationsForEdit(ephytoGen, params)
        LOGGER.info("Operations in initOperationsForReplace:${ephytoGen?.operations?.id}")
    }

    def initEphytoIsDocumentEditable(domainInstance){
        domainInstance.setIsDocumentEditable(true)
        domainInstance.setAddAttachedDocs(true)
        domainInstance.setEditAttachedDocs(true)
        domainInstance.setAddItemGoods(true)
        domainInstance.setEditItemGoods(true)
        initAttachments(domainInstance)
        initEphytoLog(domainInstance)
    }

    def initEphytoForRejectOrQuery(domainInstance){
        domainInstance.setIsDocumentEditable(false)
        domainInstance.setAddAttachedDocs(false)
        domainInstance.setEditAttachedDocs(false)
        domainInstance.setAddItemGoods(false)
        domainInstance.setEditItemGoods(false)
        initAttachments(domainInstance)
        initEphytoLog(domainInstance)
    }


    def initTotalEphyto(EphytoGen ephytoGenInstance){
        ephytoGenInstance?.totalQuantity = ephytoGenInstance.sumQuantityGoods()
        ephytoGenInstance?.totalGrossWeight = ephytoGenInstance.sumGrossWeightGoods()
        ephytoGenInstance?.totalNetWeight = ephytoGenInstance.sumNetWeightGoods()
        ephytoGenInstance?.totalItems = ephytoGenInstance?.itemGoods?.size() == null? 0 : ephytoGenInstance?.itemGoods?.size()
    }



    def initAttachments(EphytoGen ephytoGen) {
        if (ephytoGen.attachments == null) {
            ephytoGen?.attachments = new ArrayList<Attachment>()
        }
    }


    def initEphytoLog(EphytoGen ephytoGen) {
        if (ephytoGen.logs == null) {
            ephytoGen.logs = new TreeSet<>()
        }
    }

    def initEphytoGenOwner(domainInstance, String commitOperation) {
        if ([OP_STORE, OP_REQUEST].contains(commitOperation)) {
            initOwner(domainInstance)
        }
    }

    EphytoGen initDocumentForView(EphytoGen ephytoGen) {
        ephytoGen.setIsDocumentEditable(false)
        ephytoGen.setAddAttachedDocs(false)
        ephytoGen.setEditAttachedDocs(false)
        ephytoGen.setAddItemGoods(false)
        ephytoGen.setEditItemGoods(false)
        ephytoGen.startedOperation = EphytoConstants.SHOW
        return ephytoGen
    }

    def initOwner(domainInstance) {
        domainInstance.ownerUser = ephytoSecurityService.getUserName()
        domainInstance.ownerGroup = ephytoSecurityService.getUserOwnerGroup()
    }

    def OperationHandlerService resolveEphytoGenOperationHandler(String commitOperation) {
        switch (commitOperation) {
            case OP_STORE:
            case OI_UPDATE_STORED:
                return storeOperationHandlerService
            case OP_REQUEST:
            case OI_REQUEST_STORED:
                return requestOperationHandlerService
            case OI_REQUEST_QUERIED:
                return requestQueriedOperationHandlerService
            case OI_REJECT_REQUESTED :
                return rejectOperationHandlerService
            case OI_CANCEL_APPROVED:
            case OI_CANCEL_QUERIED:
                return cancelOperationHandlerService
            case OI_DELETE_STORED :
                return deleteOperationHandlerService
            case OI_APPROVE_REQUESTED :
                return approveOperationHandlerService
            case OP_REPLACE :
            case OI_REPLACE_APPROVED :
            case OI_REPLACE_SIGNED :
                return replaceOperationHandlerService
            case OI_SIGN_APPROVED :
                return signOperationHandlerService
            default:
                return clientOperationHandlerService
        }
    }

    def newInstanceOnReplace(EphytoGen domainInstance, Map params){
        def temporaryKey = domainInstance?.id
        domainInstance.logs  = new TreeSet<>()
        EphytoGen newDomainInstance = DomainCloningUtils.deepClone(domainInstance)
        newDomainInstance?.attachments?.clear()
        domainInstance.attachments.each {
            it.id = null
            it.parent = newDomainInstance
            it.attDoc = new AttachedFile(data: it.attDoc.data, attachment: it)
            newDomainInstance.addToAttachments(it)
        }
        newDomainInstance.logs = new TreeSet<>()
        initDocumentForReplace(newDomainInstance,params)
        newDomainInstance.tempId = temporaryKey
        newDomainInstance.requestDate =null
        newDomainInstance.signatureDate=null
        newDomainInstance.requestYear = null
        newDomainInstance.requestNumberSequence = null
        newDomainInstance.requestNumber=null
        newDomainInstance.status=null
        newDomainInstance.phytosanitaryCertificateRef=null
        newDomainInstance.phytosanitaryCertificateDate=LocalDate.now() //null

        newDomainInstance
    }

    def setGeneratedRequestNumber(EphytoGen domain){
        domain.requestDate = LocalDate.now()
        def year = domain.requestDate.getYear()
        domain.requestYear = year
        sequenceNumberService.nextRequestNumber(domain)

        /*
        domain.requestNumberSequence = sequenceGenerator.nextRequestNumber(year)
        domain.requestNumber = generateRequestNumber(domain)
        */
        domain
    }

    def applyApproveStatus(EphytoGen domainInstance){
           // LOGGER.debug("applyReplaceStatus Method  commitOperation:$commitOperation")
            LOGGER.debug("applyReplaceStatus Method temporaryKey :${domainInstance?.tempId}")
            WebRequestUtils.remove("commitOperation")
            WebRequestUtils.remove("commitOperationName")
            WebRequestUtils.putParam("commitOperation", Operations.OI_APPROVE_REQUESTED)
            WebRequestUtils.putParam("commitOperationName", Operations.OP_APPROVE)
            domainInstance?.status=ST_REQUESTED
            EphytoGen.withNewTransaction { localTransactionStatus ->
            domainInstance = approveOperationHandlerService.execute(domainInstance, localTransactionStatus)
            LOGGER.debug("hasErrors domainInstance :${domainInstance.hasErrors()}")
            if(!domainInstance.hasErrors()){
                domainInstance=setGeneratedRequestNumber(domainInstance)
            }
            if(domainInstance){
             WebRequestUtils.remove("commitOperation")
             WebRequestUtils.remove("commitOperationName")
             WebRequestUtils.putParam("commitOperation", Operations.OI_REPLACE_APPROVED)
             WebRequestUtils.putParam("commitOperationName", Operations.OP_REPLACE)
            }

        }
        domainInstance

    }

    static boolean isBotanicalNameFieldEnabledForGovUser(EphytoGen ephytoGenInstance) {
        return ((isGovOfficer() || isGovSeniorOfficer()) && ephytoGenInstance?.startedOperation == OI_APPROVE_REQUESTED && ephytoGenInstance?.modeOfTransportCode == "4")
    }
}
