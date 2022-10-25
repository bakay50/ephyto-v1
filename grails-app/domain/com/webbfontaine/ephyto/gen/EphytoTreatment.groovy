package com.webbfontaine.ephyto.gen

import com.webbfontaine.grails.plugins.taglibs.ConfigurableFields
import org.joda.time.LocalDate
import com.webbfontaine.ephyto.gen.treatment.EphytoTreatmentBusinessLogicRule
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 22/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoTreatment implements ConfigurableFields,Serializable{

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


    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    //                                                                               SN/US  |   RN/RS/RQ/UQ  |   DS/CQ/CC    |   QR/RR/RP/IP   |   PR   |   CP
    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    // Treatment ----------------------------------------------
    Long id
    Integer itemNumber          // item number                                          P               P           P               P               P       P
    String warehouse                        // Warehouse                                O	            M	        P	            P	            P	    P
    String applicatorCode                   // Applicator Code                          O	            M	        P	            P	            P	    P
    String applicatorAgreement              // Applicator Agreement                     P	            P	        P	            P	            P	    P
    String applicatorNameAddress            // Applicator Name & Address                P	            P	        P	            P	            P	    P
    String disinfectionCertificateRef       // Disinfection Certificate Reference       P	            P	        P	            P	            M	    P
    LocalDate disinfectionCertificateDate   // Disinfection Certificate Date            P	            P	        P	            P	            M	    P
    String treatmentType                    // Treatment Type                           P	            P	        P	            P	            M	    P
    LocalDate treatmentSartDate             // Treatment Sart Date                      P	            P	        P	            P	            M	    P
    String treatmentSartTime                // Treatment Sart Time                      P	            P	        P	            P	            M	    P
    LocalDate treatmentEndDate              // Treatment End Date                       P	            P	        P	            P	            M	    P
    String treatmentEndTime                 //  Treatment End Time                      P	            P	        P	            P	            M	    P
    String usedProducts                     // Used Products                            P	            P	        P	            P	            M	    P
    String concentration                    // Concentration                            P	            P	        P	            P	            M	    P
    String treatmentDuration                // Treatment Duration                       P	            P	        P	            P	            P	    P


    static belongsTo = [ephytoGen: EphytoGen]


    static rules = { [EphytoTreatmentBusinessLogicRule] }

    static constraints = {
        itemNumber(nullable: false)
        warehouse nullable: true, blank: true, maxSize: 35
        applicatorCode nullable: true, blank: true, maxSize: 10
        applicatorAgreement nullable: true, blank: true, maxSize: 20
        applicatorNameAddress nullable: true, blank: true
        disinfectionCertificateRef nullable: true, blank: true, maxSize: 1000
        disinfectionCertificateDate nullable: true, blank: true
        treatmentType nullable: true, blank: false, maxSize: 35
        treatmentSartDate nullable: true, blank: false
        treatmentSartTime nullable: true, blank: false
        treatmentEndDate nullable: true, blank: false
        treatmentEndTime nullable: true, blank: false
        usedProducts nullable: true, blank: true, maxSize: 35
        concentration nullable: true, blank: true, maxSize: 35
        treatmentDuration nullable: true, blank: true

    }

    static mapping = {
        dynamicUpdate true
        id generator: 'seqhilo', params: [table: 'EPHYTO_ID_GENERATOR', column: 'CERTIFICATE_TREATMENT', max_lo: 1]
        table 'CERTIFICATE_TREATMENT'
        ephytoGen column: 'EPHYTO_GEN_ID'

        itemNumber column: 'RNK'
        warehouse column: 'WHS', length: 35
        applicatorCode column: 'APP_COD', length: 10
        applicatorAgreement column: 'APP_AGR', length: 20
        applicatorNameAddress column: 'APP_NAM_ADD', sqlType: 'CLOB'
        disinfectionCertificateRef column: 'DINF_CERT_REF', length: 1000
        disinfectionCertificateDate column: 'DINF_CERT_DAT'
        treatmentType column: 'TREAT_TYP', length: 35
        treatmentSartDate column: 'TREAT_ST_DAT'
        treatmentSartTime column: 'TREAT_ST_TIM'
        treatmentEndDate column: 'TREAT_END_DAT'
        treatmentEndTime column: 'TREAT_END_TIM'
        usedProducts column: 'USR_PROD', length: 35
        concentration column: 'CONCENT', length: 35
        treatmentDuration column: 'TREAT_DUR'
    }

    @Override
    boolean isFieldMandatory(String fieldName) {
        return false
    }

    @Override
    boolean isFieldEditable(String fieldName) {
        return false
    }


}
