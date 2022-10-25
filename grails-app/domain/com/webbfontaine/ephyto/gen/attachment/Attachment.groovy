package com.webbfontaine.ephyto.gen.attachment

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.taglibs.ConfigurableFields
import grails.util.Holders
import org.joda.time.LocalDate


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Sidoine BOSSO
 * Date: 14/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class Attachment implements ConfigurableFields,Serializable{
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

    //O = Optional
    //M = Mandatory
    //P = Prohibited (not editable)
    //T = Technical (i.e. field which is internal to the system and absent from client interface)
    //H = Hidden (not used for the document)

    Long id

    // ------------------------------------------------------------------------------------------------------------------------------------------
    //                                                                  SN/US  |   RN/RS/RQ/UQ  |   DS/CQ/CC    |   QR/RR/RP/IP   |   PR   |   CP
    // ------------------------------------------------------------------------------------------------------------------------------------------
    Integer attNumber       // Attached doc rank                            P       P                P                 P              P        P
    String docCode          // Attached doc code                            M       M                P                 M              P        P
    String docType          // Attached doc type                            M       M                P                 M              P        P
    String docRef           // Attached doc ref                             M       M                P                 M              P        P
    LocalDate docDate       // Attached doc date                            M       M                P                 M              P        P
    AttachedFile attDoc     // Attached doc file                            M       M                P                 M              P        P
    String fileExtension    // file extension
    boolean hasAttachedFile
    boolean isUpdated = false

    static transients = ['isUpdated']

    static constraints = {
        docCode(nullable: true, maxSize: 35)
        docType(blank: false, nullable: false, maxSize: 100)
        docRef(blank: false, nullable: false, maxSize: 35)
        docDate(nullable: false)
        attDoc(nullable: true)
        fileExtension(nullable: true, inList: Holders.grailsApplication.config.attachmentAcceptedFormats)
    }

    static belongsTo = [parent: EphytoGen]

    //static rules = {[AttachmentBusinessLogicRule]}

    static mapping = {
        id generator: 'seqhilo', params: [table: 'EPHYTO_ID_GENERATOR', column: 'CERTIFICATE_ATTACHMENTS', max_lo: 1]

        table 'CERTIFICATE_ATTACHMENTS'
        attDoc cascade: 'all-delete-orphan'

        version false
        parent column: 'INSTANCE_ID'
        docCode column: 'DOC_COD'
        docType column: 'DOC_TYP'
        docRef column: 'DOC_REF'
        docDate column: 'DOC_DAT'
        attDoc column: 'ATT_DOC'
        fileExtension column: 'CNT_TYP', length: 10
        attNumber column: 'ATT_NUM'
        hasAttachedFile column: 'HAS_ATT_FIL'
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
