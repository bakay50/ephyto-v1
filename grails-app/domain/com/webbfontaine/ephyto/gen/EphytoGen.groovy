package com.webbfontaine.ephyto.gen

import com.webbfontaine.ephyto.VersionCheckingRule
import com.webbfontaine.ephyto.config.CustomsOffice
import com.webbfontaine.ephyto.config.FieldsConfiguration
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.gen.attachment.Attachment
import com.webbfontaine.ephyto.print.EphytoGenPrintFields
import com.webbfontaine.grails.plugins.taglibs.ConfigurableFields
import com.webbfontaine.grails.plugins.workflow.operations.Operation
import org.joda.time.LocalDate

import java.text.SimpleDateFormat

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Yacouba SYLLA
 * Date: 14/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

import static com.webbfontaine.ephyto.EphytoGenUtils.CACAO
import static com.webbfontaine.ephyto.EphytoGenUtils.COFFEE

class EphytoGen implements ConfigurableFields, Serializable {
    def grailsApplication

    //SN = Store New
    //US = Upadte Stored
    //DS = Delete Stored
    //CQ = Cancel Queried
    //CC = Cancel Confirmed
    //RN = Request New
    //RS = Request Stored
    //RQ = Request Queried
    //QR = Query Requested
    //RR = Reject Requested
    //AR = Approve Requested
    //DA = Deliver Approved
    //RP = Reject Processed
    //IP = Invalidate Processed
    //PR = Process Requested
    //CP = Confirm Processed
    //UQ = Update Queried
    //AR = Approve Requested
    //DA = Deliver Approved

    //O = Optional
    //M = Mandatory
    //P = Prohibited (not editable)
    //T = Technical (i.e. field which is internal to the system and absent from client interface)
    //H = Hidden (not used for the document)

    Long id

    Integer requestNumberSequence
    Integer requestYear
    Long tempId
    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //                                                                               SN/US  |   RN/RS/RQ/UQ  |   DS/CQ/CC    |   QR/RR/RP/IP   |   PR   |   CP
    // -------------------------------------------------------------------------------------------------------------------------------------------------------

    // Header Fields -----------------------------------------------------------------------------------------------------------------------------------------
    String requestNumber
    // Request Number                           P	            P	        P	            P	            P	    P
    LocalDate requestDate
    // Request Date                             P	            P	        P	            P	            P	    P
    String nextDocRef
    // Next Document Reference	                H               H           H               H               H       H
    String previousDocRef
    // Previous Document Reference	            H               H           H               H               H       H
    String dockingPermissionRef
    // Docking Permission Reference             H               H           H               H               H       H
    LocalDate dockingPermissionDate
    // Docking Permission Date                  H               H           H               H               H       H
    String phytosanitaryCertificateRef
    // Phytosanitary Certificate Reference      H               H           H               H               H       H
    LocalDate phytosanitaryCertificateDate
    // Phytosanitary Certificate Date           H               H           H               H               H       H
    Integer dockNumber
    // Dock Number                              H               H           H               H               H       H
    String customsClearanceOfficeCode
    // Customs Clearance Office Code            H               H           H               H               H       H
    String customsClearanceOfficeName
    // Customs Clearance Office Name            H               H           H               H               H       H
    String declarationSerial
    // Declaration Serial                       H               H           H               H               H       H
    Integer declarationNumber
    // Declaration Number                       H               H           H               H               H       H
    LocalDate declarationDate
    // Declaration Date                         H               H           H               H               H       H
    String modeOfTransportCode
    // Mode of Transport Code                   O	            M	        P	            P	            P	    P
    String meansOfTransport
    // Means of Transport                       O	            M	        P	            P	            P	    P
    LocalDate boardingDate
    // Boarding Date                            O	            M	        P	            P	            P	    P
    String countryOfDestinationCode
    // Country of Destination Code              O	            M	        P	            P	            P	    P
    String placeOfUnloadingCode
    // Place of Unloading Code                  O	            M	        P	            P	            P	    P
    LocalDate proposedOperationDate
    // Proposed Operation Date                  O	            M	        P	            P	            P	    P
    String proposedOperationTime
    // Proposed Operation Time                  O	            M	        P	            P	            P	    P
    String otReference
    // OT Reference                             O	            O	        P	            P	            P	    P
    String ptReference
    // PT Reference                             O	            O	        P	            P	            P	    P
    String observations
    // Observations                             O	            O	        P	            P	            P	    P
    String userReference
    // User Reference                           Oi	            Oi	        P	            P	            P	    P
    String modeOfTransportName
    // Mode of Transport Name                   P	            P	        P	            P	            P	    P
    String countryOfDestinationName
    // Country of Destination Name              P	            P	        P	            P	            P	    P
    String placeOfUnloadingName
    // Place of Unloading Name                  P	            P	        P	            P	            P	    P
    LocalDate operDate
    // Operation Date                                P	            P	        P	            P	            P	    P
    String operTime
    // operationTime                                 P	            P	        P	            P	            P	    P
    String nameAndSurnameMinistryAgent
    // Name and Surname Ministry Agent          P	            P	        P	            P	            P	    M
    String titleMinistryAgent
    // Title Ministry Agent                     P               P	        P               P               P       M
    String status// Document Status                          P	            P	        P   	        P	            P	    P
    String documentType
    // Document Type                            T               T           T               T               T       T
    LocalDate filingDate
    //Filing Date                               T               T           T               T               T       T
    LocalDate signatureDate
    // signature Date                            T               T           T               T               T       T
    // Names and Parties ----------------------------------------------
    String exporterCode
    // Exporter Code                            M	            M	        P	            P	            P	    P
    String exporterName
    // Exporter Name & Address                  P	            P	        P	            P	            P	    P
    String exporterAddress
    // Exporter Na me & Address                  P	            P	        P	            P	            P	    P
    String consigneeNameAddress
    // Consignee Name & Address                 M	            M	        P	            P	            P	    P
    String declarantCode
    // Declarant Code                           O	            O	        P	            P	            P	    P
    String declarantName
    // Declarant Name & Address                    P	            P	        P	            P	            P	    P
    String declarantAddress
    // Declarant Name & Address                     P	            P	        P	            P	            P	    P
    String applicantName
    // Applicant Name                           O	            M	        P	            P	            P	    P
    String applicantTelephone
    // Applicant Telephone                      O	            M	        P	            P	            P	    P
    String applicantEmail
    // Applicant E-mail                         O	            M	        P	            P	            P	    P
    // Real Exporter Details
    String realExportNameAddress
    // Real Exporter Details                    O	            M	        P	            P	            P	    P
    // Goods ----------------------------------------------------
    String commodityCode
    // Commodity Code                           M	            M	        P	            P	            P	    P
    String commodityDescription
    // Commodity Description                    P	            P	        P	            P	            P	    P
    String productTypeCode
    // Product Type Code                        M	            M	        P	            P	            P	    P
    String productTypeName
    // Product Type Name                        P	            P	        P	            P	            P	    P
    String harvest
    // Harvest                                  O	            O	        P	            P	            P	    P
    String season// Season                                   O	            O	        P	            P	            P	    P
    String commercialDescriptionGoods
    // Commercial Description Goods             O	            O	        P	            P	            P	    P
    String botanicalName
    // botanicalName                            O	            O	        P	            P	            P	    P
    String countryOfOriginCode
    // Country of Origin Code                   O	            M	        P	            P	            P	    P
    String countryOfOriginName
    // Country of Origin Name                   P	            P	        P	            P	            P	    P
    String packageMarks
    // Package Marks                            O	            M	        P	            P	            P	    P
    String packageCode
    // Package Code                             O	            M	        P	            P	            P	    P
    String packageName
    // Package Name                             P	            P	        P	            P	            P	    P
    BigDecimal totalQuantity
    // Total Quantity                           P	            P	        P	            P	            P	    P
    BigDecimal totalNetWeight
    // Total Net Weight                         P	            P	        P	            P	            P	    P
    BigDecimal totalGrossWeight
    // Total Gross Weight                       P	            P	        P	            P	            P	    P
    BigDecimal volume
    // Volume                                   O	            O	        P	            P	            P	    P
    Integer totalItems
    // Total Items                              P	            P	        P	            P	            P	    P

    Integer defaultQuantity
    // default Quantity                         H               H           H               H               H       H
    Integer defaultNetWeight
    // default Net Weight                       H               H           H               H               H       H
    Integer defaultGrossWeight
    // default Gross Weight                     H               H           H               H               H       H
    String treatment
    // Treatment                             Oi	            Oi	        P	            P	            P	    P

    // Supp info -------------------------------------------------------
    LinkedHashSet<Operation> operations
    SortedSet<EphytoLog> logs
    String startedOperation
    // ----------------------------------------------------------------
    String ownerUser
    String ownerGroup

    boolean isDocumentEditable = false
    boolean isItemsEditable = false
    boolean isTreatmentEditable = false
    boolean notProperlyInitialized = true
    boolean addAttachedDocs
    boolean editAttachedDocs
    boolean addItemGoods
    boolean editItemGoods
    boolean isTempProtect = false
    String currentoperation
    String batchAndSubBatches
    String treatmentPeriods
    String disinfectionCertifs
    String concentrations
    String usedProducts
    String treatmentTypes
    String treatmentDurations
    String placeOfIssue
    String expAndRealExp
    String totalQForPrint


    String message
    Boolean exceptionmessage = false

    EphytoLog tempLog

    // Treatment -------------------------------------------------------
    List<EphytoTreatment> itemTreatments
    // Goods ------------------------------------------------------------
    List<ItemGood> itemGoods
    // Attachments -------------------------------------------------------
    List<Attachment> attachments

    static hasMany = [itemGoods: ItemGood, attachments: Attachment, logs: EphytoLog, itemTreatments: EphytoTreatment]

    static userPropertiesMapping = [officeAccess: 'customsClearanceOfficeCode', TIN: 'exporterCode', DEC: 'declarantCode', DOC: 'documentType']

    static transients = ['operations', 'startedOperation', 'isDocumentEditable', 'isItemsEditable', 'addAttachedDocs', 'isTempProtect',
                         'editAttachedDocs', 'notProperlyInitialized', 'tempLog', 'addItemGoods', 'editItemGoods', 'currentoperation',
                         'batchAndSubBatches', 'isTreatmentEditable', 'treatmentPeriods', 'disinfectionCertifs', 'concentrations', 'usedProducts',
                         'treatmentTypes', 'treatmentDurations', 'placeOfIssue', 'message', 'exceptionmessage', 'expAndRealExp', 'tempId', 'totalQForPrint']


    static skipValidation = ['logs']

    static rules = {
        [VersionCheckingRule, GenBusinessLogicRule]
    }

    static constraints = {
        requestNumberSequence(nullable: true, min: 1, unique: ['requestYear'])
        requestYear(nullable: true)
        requestNumber(blank: true, nullable: true, maxSize: 13)
        requestDate(nullable: true)
        filingDate(nullable: true)
        signatureDate(nullable: true)

        documentType(blank: true, nullable: true, maxSize: 3)
        otReference(blank: true, nullable: true, maxSize: 30)
        ptReference(blank: true, nullable: true, maxSize: 30)
        dockingPermissionRef(blank: true, nullable: true, maxSize: 30)
        dockingPermissionDate(nullable: true)
        phytosanitaryCertificateRef(blank: true, nullable: true, maxSize: 30)
        phytosanitaryCertificateDate(nullable: true)
        dockNumber(blank: true, nullable: true)
        customsClearanceOfficeCode(blank: true, nullable: true, maxSize: 5)
        customsClearanceOfficeName(blank: true, nullable: true, maxSize: 35)
        declarationSerial(blank: true, nullable: true, maxSize: 1)
        declarationNumber(blank: true, nullable: true, maxSize: 10)
        declarationDate(nullable: true)
        modeOfTransportCode(blank: true, nullable: true, maxSize: 3)
        modeOfTransportName nullable: true, blank: true, maxSize: 35
        meansOfTransport(blank: true, nullable: true, maxSize: 35)
        boardingDate(nullable: true)
        countryOfDestinationCode(blank: true, nullable: true, maxSize: 2)
        countryOfDestinationName nullable: true, blank: true, maxSize: 35

        placeOfUnloadingCode(blank: true, nullable: true, maxSize: 5)
        placeOfUnloadingName nullable: true, blank: true, maxSize: 35
        proposedOperationDate nullable: true, blank: false
        proposedOperationTime nullable: true, blank: false
        observations nullable: true, blank: true, maxSize: 1000
        userReference nullable: true, blank: true, maxSize: 30
        nextDocRef nullable: true, blank: true, maxSize: 13
        previousDocRef nullable: true, blank: true, maxSize: 13

        operDate nullable: true
        operTime nullable: true
        nameAndSurnameMinistryAgent nullable: true, blank: true, maxSize: 25
        titleMinistryAgent nullable: true, blank: true, maxSize: 15
        status nullable: true, blank: true, maxSize: 15


        exporterCode nullable: false, blank: false, maxSize: 17
        exporterName(nullable: true, maxSize: 400)
        exporterAddress(nullable: true)
        realExportNameAddress nullable: true, blank: true
        consigneeNameAddress nullable: false, blank: false
        declarantCode nullable: true, blank: true, maxSize: 17
        declarantName(nullable: true, maxSize: 400)
        declarantAddress(nullable: true)
        applicantName nullable: true, blank: true, maxSize: 35
        applicantTelephone nullable: true, blank: true, maxSize: 17
        applicantEmail nullable: true, blank: true, maxSize: 128, email: true

        commodityCode nullable: false, blank: false, maxSize: 10
        commodityDescription nullable: true, blank: true, maxSize: 750
        productTypeCode nullable: false, blank: false, maxSize: 30
        productTypeName nullable: true, blank: true, maxSize: 100
        harvest nullable: true, blank: true, maxSize: 100
        season nullable: true, blank: true, maxSize: 100
        commercialDescriptionGoods nullable: true, blank: true, maxSize: 50
        botanicalName nullable: true, blank: true, maxSize: 1500


        countryOfOriginCode nullable: true, blank: true, maxSize: 2
        countryOfOriginName nullable: true, blank: true, maxSize: 35
        packageMarks nullable: true, maxSize: 50
        packageCode nullable: true, maxSize: 17
        packageName nullable: true, maxSize: 35
        totalQuantity nullable: true
        totalNetWeight nullable: true
        totalGrossWeight nullable: true
        volume nullable: true
        totalItems nullable: true

        defaultQuantity nullable: true
        defaultNetWeight nullable: true
        defaultGrossWeight nullable: true

        ownerUser(nullable: true)
        ownerGroup(nullable: true)
        treatment nullable: true, blank: true, maxSize: 5

    }


    static mapping = {
        dynamicUpdate true
        table 'CERTIFICATE_GENERAL'

        itemGoods cascade: 'all-delete-orphan'
        attachments cascade: 'all-delete-orphan'
        itemTreatments cascade: 'all-delete-orphan'

        id generator: 'seqhilo', params: [table: 'EPHYTO_ID_GENERATOR', column: 'CERTIFICATE_GENERAL', max_lo: 1]

        requestNumberSequence column: 'REQ_SEQ'
        requestYear column: 'REQ_YER'

        requestNumber column: 'REQ_NBR', index: 'PHYTO_GEN_REF_NUMBER_IDX', length: 13, unique: true
        requestDate column: 'REQ_DAT'
        filingDate column: 'FILL_DAT'
        signatureDate column: 'SIGN_DAT'

        nextDocRef column: 'NEXT_DOC_REF', length: 13
        previousDocRef column: 'PREV_DOC_REF', length: 13
        dockingPermissionRef column: 'DOCK_PERM_REF', length: 30
        dockingPermissionDate column: 'DOCK_PERM_DAT'
        phytosanitaryCertificateRef column: 'SAN_CERT_REF', length: 30
        phytosanitaryCertificateDate column: 'SAN_CERT_DAT'
        dockNumber column: 'DOCK_NBR'
        customsClearanceOfficeCode column: 'CUS_CLAR_OFF_COD', length: 5
        customsClearanceOfficeName column: 'CUS_CLAR_OFF_NAM', length: 35
        declarationSerial column: 'DEC_SER', length: 1
        declarationNumber column: 'DEC_NBR'
        declarationDate column: 'DEC_DAT'
        modeOfTransportCode column: 'MOD_TPT_COD', length: 3
        meansOfTransport column: 'MEAN_TPT', length: 35
        boardingDate column: 'BRD_DAT'
        countryOfDestinationCode column: 'CTY_DEST_COD', length: 2
        placeOfUnloadingCode column: 'UNL_COD', length: 5
        proposedOperationDate column: 'PROP_OPER_DAT'
        proposedOperationTime column: 'PROP_OPER_TIM'
        otReference column: 'OT_REF', length: 30
        ptReference column: 'PT_REF', length: 30
        observations column: 'OBS', length: 1000
        userReference column: 'USR_REF', length: 1000
        modeOfTransportName column: 'MOD_TPT', length: 13
        countryOfDestinationName column: 'CTY_DEST_NAM', length: 35
        placeOfUnloadingName column: 'UNL_NAM', length: 35
        operDate column: 'OPER_DAT'
        operTime column: 'OPER_TIM'
        nameAndSurnameMinistryAgent column: 'MIN_AGT_NAM_SNAM', length: 25
        titleMinistryAgent column: 'MIN_AGT_TTL', length: 15

        documentType column: 'DOC_TYP', length: 3

        exporterCode column: 'EXP_COD', length: 17
        exporterAddress column: 'EXP_ADD', sqlType: 'CLOB'
        exporterName column: 'EXP_NAM', length: 400
        realExportNameAddress column: 'REAL_EXP_NAME_ADD', sqlType: 'CLOB'
        consigneeNameAddress column: 'CONS_NAM_ADD', sqlType: 'CLOB'
        declarantCode column: 'DEC_COD', length: 17
        declarantAddress column: 'DEC_ADD', sqlType: 'CLOB'
        declarantName column: 'DEC_NAM', length: 400
        applicantName column: 'APP_NAM', length: 35
        applicantTelephone column: 'APP_TEL', length: 17
        applicantEmail column: 'APP_MAIL', length: 138


        commodityCode column: 'CMDTY_COD', length: 10
        commodityDescription column: 'CMDTY_DESC', length: 750
        productTypeCode column: 'PDT_TYP_COD', length: 30
        productTypeName column: 'PDT_TYP_NAM', length: 100
        harvest column: 'HVT', length: 100
        season column: 'SEASON', length: 100
        commercialDescriptionGoods column: 'COM_DESC_GDS', length: 30
        botanicalName column: 'BOT_NAME', length: 1500

        countryOfOriginCode column: 'CTY_ORI_COD', length: 2
        countryOfOriginName column: 'CTY_ORI_NAM', length: 35
        packageMarks column: 'PCK_MRK', length: 50
        packageCode column: 'PCK_COD', length: 17
        packageName column: 'PCK_NAM', length: 35
        totalQuantity column: 'TOT_QUAL'
        totalNetWeight column: 'TOT_NET_WGT'
        totalGrossWeight column: 'TOT_GRS_WGT'
        volume column: 'VOL'
        totalItems column: 'TOT_ITM'

        defaultQuantity column: 'DEF_QTY'
        defaultNetWeight column: 'DEF_NET_WGT'
        defaultGrossWeight column: 'DEF_GRS_WGT'
    }


    public Integer getItemSequenceNumber() {
        if (itemGoods != null) {
            return itemGoods.size() + 1
        } else {
            itemGoods = new ArrayList<ItemGood>()
            return getItemSequenceNumber()
        }
    }

    public BigDecimal getTotalQForPrint() {
        return (this?.packageCode?.equalsIgnoreCase(EphytoConstants.VR)) ? 1 : (this?.totalQuantity)

    }

    public Integer getTreatmentSequenceNumber() {
        if (itemTreatments != null) {
            return itemTreatments.size() + 1
        } else {
            itemTreatments = new ArrayList<ItemGood>()
            return getTreatmentSequenceNumber()
        }
    }

    public BigDecimal sumNetWeightGoods() {
        return this?.itemGoods?.netWeight?.grep { it != null }?.sum() == null ? 0 : this?.itemGoods?.netWeight?.grep {
            it != null
        }?.sum()
    }

    public BigDecimal sumGrossWeightGoods() {
        return this?.itemGoods?.grossWeight?.grep {
            it != null
        }?.sum() == null ? 0 : this?.itemGoods?.grossWeight?.grep { it != null }?.sum()
    }

    public BigDecimal sumQuantityGoods() {
        return this?.itemGoods?.quantity?.grep { it != null }?.sum() == null ? 0 : this?.itemGoods?.quantity?.grep {
            it != null
        }?.sum()

    }

    public sumItemGoods() {
        this?.itemGoods?.size() == null ? 0 : this?.itemGoods?.size()
    }

    public String getExpAndRealExp() {
        String exporterDetails = this.exporterName + '\n' + this.exporterAddress
        String exp = exporterDetails?.trim()?.toLowerCase()?.replaceAll(" ", "")?.replaceAll("[\n\r]", "")
        String realExp = (this.realExportNameAddress) ? (this?.realExportNameAddress?.toString()?.trim()?.toLowerCase()?.replaceAll(" ", "")?.replaceAll("[\n\r]", "")) : null
        if (exp == realExp) {
            exporterDetails = exporterDetails
        } else if (this?.realExportNameAddress && this?.realExportNameAddress?.trim() != " ") {
            exporterDetails = exporterDetails + "  P/C  " + this?.realExportNameAddress
        }
        exporterDetails
    }


    public String getPlaceOfIssue() {
        def result = this.customsClearanceOfficeName
        if (CustomsOffice.customsOffice.containsKey(this?.customsClearanceOfficeCode)) {
            result = CustomsOffice.customsOffice[this?.customsClearanceOfficeCode]
        }
        result
    }

    public String getBatchAndSubBatches() {
        def listBatches = ''
        this?.itemGoods?.each {
            if (it?.batchNumber) {
                if ((it.subBatchNumber)) {
                    listBatches = "${listBatches}   ${it?.batchNumber}/${(it?.subBatchNumber)}(${it.quantity ? it.quantity : 0}sacs)".trim()
                } else if ((it?.ephytoGen?.productTypeCode == COFFEE && it?.quantity != EphytoGenPrintFields.getFieldCoffeeDefaultQuantity()) || (it.ephytoGen?.productTypeCode == CACAO && it.quantity != EphytoGenPrintFields.getFieldCacaoDefaultQuantity())) {
                    listBatches = "${listBatches}   ${it?.batchNumber}(${it.quantity ? it.quantity : 0}sacs)".trim()
                } else {
                    listBatches = "${listBatches}   ${it.batchNumber}".trim()
                }
            }
        }
        return listBatches
    }

    public String getTreatmentDurations() {
        int i = 0
        ArrayList<String> listDurations = new ArrayList<String>()
        StringBuffer sb = new StringBuffer()
        this?.itemTreatments?.each {
            if (it?.treatmentDuration) {
                listDurations.add(it?.treatmentDuration)
            }
        }
        listDurations.unique().each() {
            if (i == 0) {
                sb.append("${it} H");
                i++
            } else {
                sb.append("; ");
                sb.append("${it} H");
            }
        }


        return sb.toString()
    }

    public String getTreatmentTypes() {
        int i = 0
        ArrayList<String> listCon = new ArrayList<String>()
        StringBuffer sb = new StringBuffer()
        if (this?.productTypeCode.equalsIgnoreCase(EphytoConstants.MANGUE)) {
            listCon.add(EphytoConstants.APPROCH_SYSTEMIQUE)
        } else {
            this?.itemTreatments?.each {
                if (it?.treatmentType) {
                    listCon.add(it?.treatmentType)
                }
            }
        }

        listCon.unique().each() {
            if (i == 0) {
                sb.append("${it}");
                i++
            } else {
                sb.append("\n");
                sb.append("${it}");
            }
        }

        return sb.toString()

    }

    public String getUsedProducts() {
        int i = 0
        ArrayList<String> listCon = new ArrayList<String>()
        StringBuffer sb = new StringBuffer()
        this?.itemTreatments?.each {
            if (it?.usedProducts) {
                listCon.add(it?.usedProducts)
            }
        }
        listCon.unique().each() {
            if (i == 0) {
                sb.append("${it}");
                i++
            } else {
                sb.append("; ");
                sb.append("${it}");
            }
        }

        return sb.toString()
    }

    public String getConcentrations() {
        int i = 0
        ArrayList<String> listCon = new ArrayList<String>()
        StringBuffer sb = new StringBuffer()
        this?.itemTreatments?.each {
            if (it?.concentration) {
                listCon.add(it?.concentration)
            }
        }
        listCon.unique().each() {
            if (i == 0) {
                sb.append("${it}");
                i++
            } else {
                sb.append("; ");
                sb.append("${it}");
            }
        }

        return sb.toString()

    }


    public String getTreatmentPeriods() {
        int i = 0
        ArrayList<String> listTreatDates = new ArrayList<String>()
        def startD
        def endD
        def tabs
        StringBuffer sb = new StringBuffer()
        this?.itemTreatments?.each {
            if (it?.treatmentSartDate && it?.treatmentEndDate) {
                //def dbDates = new DoubleDate()
                startD = new SimpleDateFormat("dd/MM/yyyy").format(it?.treatmentSartDate?.toDate())
                endD = new SimpleDateFormat("dd/MM/yyyy").format(it?.treatmentEndDate?.toDate())
                listTreatDates.add(startD + "-" + endD)
            }
        }
        listTreatDates.unique().each() {
            if (i == 0) {
                tabs = it.split('-')
                sb.append("${tabs[0]} au ${tabs[1]}");
                i++
            } else {
                tabs = it.split('-')
                sb.append("\n");
                sb.append("${tabs[0]} au ${tabs[1]}");
            }
        }

        return sb.toString()

    }


    public String getDisinfectionCertifs() {
        int i = 0
        def startD
        StringBuffer sb = new StringBuffer()
        this?.itemTreatments?.each {
            if (it?.disinfectionCertificateRef && it?.disinfectionCertificateDate) {
                startD = new SimpleDateFormat("dd/MM/yyyy").format(it?.disinfectionCertificateDate?.toDate())
                if (i == 0) {
                    sb.append("${it.disinfectionCertificateRef} du ${startD}");
                    i++
                } else {
                    sb.append("\n");
                    sb.append("${it.disinfectionCertificateRef} du ${startD}");
                }
            }
        }

        return sb.toString()
    }

    public addItem(ItemGood item) {
        try {
            item.ephytoGen = this
            item.itemRank = getItemSequenceNumber()
        } catch (Exception e) {

        }
        itemGoods.add(item)
    }

    public addTreatment(EphytoTreatment treat) {
        treat.ephytoGen = this
        treat.itemNumber = getTreatmentSequenceNumber()
        itemTreatments.add(treat)
    }


    public ItemGood getItem(Integer itemRank) {
        if (itemRank != null && 0 < itemRank && itemRank <= itemGoods.size()) {
            return itemGoods.get(itemRank - 1)
        }
        return null
    }

    public removeItem(Integer itemRank) {
        if (itemRank != null && itemRank <= itemGoods.size()) {
            itemGoods.remove(itemRank - 1)
            itemGoods.subList(itemRank - 1, itemGoods.size()).each { ItemGood item ->
                item.itemRank = item.itemRank - 1;
            }
        }
    }


    public EphytoTreatment getTreatement(Integer itemNumber) {
        if (itemNumber != null && 0 < itemNumber && itemNumber <= itemTreatments.size()) {
            return itemTreatments.get(itemNumber - 1)
        }
        return null
    }

    public removeTreatement(Integer itemNumber) {
        if (itemNumber != null && itemNumber <= itemTreatments.size()) {
            itemTreatments.remove(itemNumber - 1)
            itemTreatments.subList(itemNumber - 1, itemTreatments.size()).each { EphytoTreatment item ->
                item.itemNumber = item.itemNumber - 1;
            }
        }
    }

    def boolean isTreatmentEditable(String fieldName) {
        return (this.isDocumentEditable && !FieldsConfiguration.isTreatmentFieldProhibited(fieldName, this))
    }

    def boolean isFieldEditable(String fieldName) {
        return (this.isDocumentEditable && !FieldsConfiguration.isGenFieldProhibited(fieldName, this))
    }

    def boolean isFieldMandatory(String fieldName) {
        if (this.isDocumentEditable) {
            return FieldsConfiguration.isGenFieldMandatory(fieldName, this)
        }
        return false
    }

    def boolean isEphytoGenMandatory(String fieldName) {
        if (this.isDocumentEditable) {
            return FieldsConfiguration.isGenFieldMandatory(fieldName, this)
        }
        return false
    }

    def boolean isTreatmentMandatory(String fieldName) {
        if (this.isDocumentEditable) {
            return FieldsConfiguration.isTreatmentFieldMandatory(fieldName, this)
        }
        return false
    }


    def boolean isEphytoLogMandatory(String fieldName) {
        return FieldsConfiguration.isEphytoLogFieldMandatory(fieldName, this)
    }

    def boolean isHidden(String fieldName) {
        return (FieldsConfiguration.isGenFieldHidden(fieldName, this))
    }


    def boolean isAttachmentEditable(String fieldName) {
        return this.isDocumentEditable && !FieldsConfiguration.isAttachmentProhibited(fieldName, this)
    }

    void setIsDocumentEditable(boolean editable) {
        this.isDocumentEditable = editable
    }

    boolean isDocumentEditable() {
        return this.isDocumentEditable
    }

    boolean isLogEditable(String fieldName) {
        return (!FieldsConfiguration.isEphytoLogProhibited(fieldName, this))
    }

    boolean isItemGoodEditable(String fieldName) {
        return (this.isDocumentEditable && !FieldsConfiguration.isItemGoodHidden(fieldName, this))
    }

    boolean isVisibleReference() {
        def isReplace = this?.operations?.id?.contains(Operations.OP_REPLACE)
        def isSign = this?.operations?.id?.contains(Operations.OP_SIGN)
        def hasRefAndDate = this?.phytosanitaryCertificateRef && this?.phytosanitaryCertificateDate
        if (isReplace || hasRefAndDate) {
            return false
        } else if (isSign || hasRefAndDate) {
            return false
        } else {
            isForApproval();
        }

    }

    boolean isDocRefSet() {
        return (this.nextDocRef || this.previousDocRef)
    }

    def isForApproval() {
        !(!this?.isDocumentEditable && this?.startedOperation == Operations.OI_APPROVE_REQUESTED && this?.status == Statuses.ST_REQUESTED && (this?.operations?.size() == 1 && this?.operations?.id?.contains(Operations.OI_APPROVE_REQUESTED)))
    }

    def boolean isEditableForApprove(String fieldName) {
        if (this.isDocumentEditable) {
            return isFieldEditable(fieldName)
        } else {
            return !isForApproval()
        }
    }

    String getUserReference() {
        return this?.@userReference?.replaceAll("[\n\r]", "");
    }

    def boolean isNewproduct() {
        boolean result = true
        if (this.productTypeCode in ["CAFE", "CACAO"]) {
            result = false
        }
        return result
    }

    boolean isTempProtect() {
        boolean isTemp = false
        this.itemTreatments.any {
            if (!it?.treatmentSartDate && !it?.treatmentSartTime && !it?.treatmentEndDate && !it?.treatmentEndTime && !it?.treatmentDuration) {
                isTemp = true
                return true
            }
        }
        return isTemp
    }

    def isBotanicEditable() {
        boolean isEditable = (this?.startedOperation == Operations.OI_APPROVE_REQUESTED && this?.status == Statuses.ST_REQUESTED && (this?.operations?.size() == 1 && this?.operations?.id?.contains(Operations.OI_APPROVE_REQUESTED)) && isAirPort())
        return isEditable ?: false
    }

    boolean isAirPort() {
        boolean hasAirPort = productTypeCode?.equalsIgnoreCase("DIVERS") && modeOfTransportCode?.equalsIgnoreCase("4")
        return hasAirPort ?: false
    }

}
