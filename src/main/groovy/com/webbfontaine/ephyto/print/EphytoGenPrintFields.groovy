package com.webbfontaine.ephyto.print

import com.webbfontaine.ephyto.gen.EphytoGen
import grails.util.Holders
import org.joda.time.LocalDate


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Faustine Valery Tamayo
 * Date: 6/15/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoGenPrintFields {
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
    // Proposed Operation Date              O	            M	        P	            P	            P	    P
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
    String status
    // Document Status                          P	            P	        P   	        P	            P	    P
    String documentType
    // Document Type                            T               T           T               T               T       T
    // Names and Parties ----------------------------------------------
    String exporterCode
    // Exporter Code                            M	            M	        P	            P	            P	    P
    String exporterName
    // Exporter Name & Address                  P	            P	        P	            P	            P	    P
    String exporterAddress
    // Exporter Name & Address                  P	            P	        P	            P	            P	    P
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
    // Treatment ----------------------------------------------
    String warehouse
    // Warehouse                                O	            M	        P	            P	            P	    P
    String applicatorCode
    // Applicator Code                          O	            M	        P	            P	            P	    P
    String applicatorAgreement
    // Applicator Agreement                     P	            P	        P	            P	            P	    P
    String applicatorNameAddress
    // Applicator Name & Address                P	            P	        P	            P	            P	    P
    String disinfectionCertificateRef
    // Disinfection Certificate Reference       P	            P	        P	            P	            M	    P
    LocalDate disinfectionCertificateDate
    // Disinfection Certificate Date            P	            P	        P	            P	            M	    P
    String treatmentType
    // Treatment Type                           P	            P	        P	            P	            M	    P
    LocalDate treatmentSartDate
    // Treatment Sart Date                      P	            P	        P	            P	            M	    P
    String treatmentSartTime
    // Treatment Sart Time                      P	            P	        P	            P	            M	    P
    LocalDate treatmentEndDate
    // Treatment End Date                       P	            P	        P	            P	            M	    P
    String treatmentEndTime
    //  Treatment End Time                      P	            P	        P	            P	            M	    P
    String usedProducts
    // Used Products                            P	            P	        P	            P	            M	    P
    String concentration
    // Concentration                            P	            P	        P	            P	            M	    P
    String treatmentDuration
    // Treatment Duration                       P	            P	        P	            P	            P	    P
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
    String season
    // Season                                   O	            O	        P	            P	            P	    P
    String commercialDescriptionGoods
    // Commercial Description Goods             O	            O	        P	            P	            P	    P
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
    String batchAndSubBatches
    String treatmentPeriods
    String disinfectionCertifs
    String concentrations
    String treatmentTypes
    String treatmentDurations
    String placeOfIssue
    String expAndRealExp
    BigDecimal totalQForPrint
    String botanicalName
    boolean ifNewproduct
    boolean canDisplay
    boolean hasAirPort

    static getConfig() {
        return Holders.config.com.webbfontaine.ephyto.productCode
    }

    static getFieldCoffeeDefaultQuantity() {
        return config.coffee.defaultQuantity
    }

    static getFieldCacaoDefaultQuantity() {
        return config.cacao.defaultQuantity
    }

    public EphytoGenPrintFields() {

    }

    public EphytoGenPrintFields(EphytoGen ephytoGen) {
        def map = ephytoGen.getProperties()
        metaClass.setProperties(this, map.findAll { key, value -> this.hasProperty(key) })
    }

    String getOtReference() {
        return otReference ? otReference : ''
    }

    String getHarvest() {
        return harvest ? harvest : ''

    }

    String getSeason() {
        return season ? season : ''

    }

    boolean getIfNewproduct() {
        boolean result = true
        if (this?.productTypeCode in ["CAFE", "CACAO"]) {  //,"DIVERS"
            result = false
        }
        return result
    }

    boolean getCanDisplay() {
        boolean result = true
        if ((this?.productTypeCode in ["CAFE", "CACAO"]) || (this?.productTypeCode.toUpperCase().equals("DIVERS") &&
                this?.harvest != null && this?.season != null)) {
            result = false
        }
        return result
    }

    boolean getHasAirPort() {
        hasAirPort = modeOfTransportCode?.equalsIgnoreCase("4")
    }

    String getBatchAndSubBatches() {
        getHasAirPort() ? botanicalName : batchAndSubBatches
    }

}

