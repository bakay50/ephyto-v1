package com.webbfontaine.ephyto.gen

import com.webbfontaine.grails.plugins.taglibs.ConfigurableFields

class ItemGood implements ConfigurableFields,Serializable{

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
    Long id
    Integer itemRank                        // Item Rank                                P	        P	            P	            P	            P	    P
    String previousDocumentReference        // Previous Document Reference              H	        H	            H	            H	            H	    H
    Integer previousDocumentItem            // Previous Document Item                   H	        H	            H	            H	            H	    H
    String batchNumber                      // Batch Number                             O	        O	            P	            P	            P	    P
    String subBatchNumber                   // Sub-Batch Number                         Oi	        Oi	            P	            P	            P	    P
    BigDecimal quantity                        // Quantity                                 M	        M	            P	            P	            P	    P
    Integer remainingQuantiy                // Remaining Quantiy                        P	        P	            P	            P	            P	    P
    BigDecimal netWeight                       // Net Weight                               M	        M	            P	            P	            P	    P
    Integer remainingNetWeight              // Remaining Net Weight                     P	        P	            P	            P	            P	    P
    BigDecimal grossWeight                     // Gross Weight                             M	        M	            P	            P	            P	    P
    Integer remainingGrossWeight            // Remaining Gross Weight                   P	        P	            P	            P	            P	    P
    Boolean analysisResult                  // Analysis Result                          H	        H	            H	            H	            H	    H
    Boolean potted                          // Potted                                   H	        H	            H	            H	            H	    H
    String observations                     // Observations                             P	        P	            P	            P	            O	    P

    static belongsTo = [ephytoGen : EphytoGen]

    static mapping = {
        dynamicUpdate true

        id generator: 'seqhilo', params: [table: 'EPHYTO_ID_GENERATOR', column: 'CERTIFICATE_GOODS', max_lo: 1]

        table 'CERTIFICATE_GOODS'

        ephytoGen column: 'EPHYTO_GEN_ID'

        itemRank column: 'ITM_RNK'
        previousDocumentReference column: 'PREV_DOC_REF', length: 13
        previousDocumentItem column: 'PREV_DOC_ITM'
        batchNumber column: 'BATCH_NBR', length: 15
        subBatchNumber column: 'SUB_BATCH_NBR', length: 15
        quantity column: 'QTY'
        remainingQuantiy column: 'RMG_QTY'
        netWeight column: 'NET_WGT'
        remainingNetWeight column: 'RMG_NET_WGT'
        grossWeight column: 'GRS_WGT'
        remainingGrossWeight column: 'RMG_GRS_WGT'
        analysisResult column: 'ANL_RES'
        potted column: 'POTTED'
        observations column: 'OBS', length: 4000
    }

    static constraints = {
        itemRank nullable: true
        previousDocumentReference nullable: true
        batchNumber nullable: true, maxSize: 15
        subBatchNumber nullable: true, maxSize: 15
        quantity nullable: false
        remainingQuantiy nullable: true
        netWeight nullable: false
        remainingNetWeight nullable: true
        grossWeight nullable: false
        previousDocumentItem nullable: true
        remainingGrossWeight nullable: true
        analysisResult nullable: true
        potted nullable: true
        observations nullable: true
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
