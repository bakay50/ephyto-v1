package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import com.webbfontaine.grails.plugins.utils.WebRequestUtils
import grails.util.Holders
import org.grails.web.servlet.mvc.GrailsWebRequest
import java.text.SimpleDateFormat
import com.webbfontaine.ephyto.sad.SadService
import static com.webbfontaine.ephyto.constants.EphytoConstants.CORRECT_RESULT
import static com.webbfontaine.ephyto.constants.EphytoConstants.MODE_OF_TRANSPORT_AIR

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Y. SYLLA
 * Date: 10/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class SadRule implements Rule{

    @Override
    void apply(RuleContext ruleContext) {
        checkSadErrors(ruleContext)
    }

    static void checkSadErrors(RuleContext ruleContext){
        if(isCommitOperationValid() && isSadCheckEnabled()){
            EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)
            String result = CORRECT_RESULT
            def clearanceOfficeCode=ephytoGen.customsClearanceOfficeCode
            def declarationSerial=ephytoGen.declarationSerial
            def declarationNumber=ephytoGen.declarationNumber
            def declarationDate=ephytoGen.declarationDate

            if(!clearanceOfficeCode || isDeclarationFieldsRequired(ephytoGen)) {
                ruleContext.rejectValue("customsClearanceOfficeCode",getErrorCode(ephytoGen.modeOfTransportCode),"Clearance Office Code is required.")
            }else if(clearanceOfficeCode && declarationSerial && declarationNumber && declarationDate){
                result = getSadResult(ephytoGen)
                if(!result.equalsIgnoreCase(CORRECT_RESULT)){
                    ruleContext.rejectValue("customsClearanceOfficeCode",null,result)
                }
            }
        }
    }

    static boolean isDeclarationFieldsRequired(EphytoGen ephytoGen){
        if(ephytoGen.modeOfTransportCode == MODE_OF_TRANSPORT_AIR){
            return false
        }else{
            return !ephytoGen.declarationSerial || !ephytoGen.declarationNumber || !ephytoGen.declarationDate
        }
    }
    static String getErrorCode(String modeOfTransportCode){
        if(modeOfTransportCode == MODE_OF_TRANSPORT_AIR){
            return "ephytoGen.input.clearanceOfficeCode.required"
        }
        else{
            return "ephytoGen.sad.input.required"
        }
    }

    static String getSadResult(EphytoGen ephytoGen){

        SadService sadService = Holders.applicationContext.getBean(SadService)
        def request = GrailsWebRequest.lookup()?.request
        String clearanceOfficeCode=ephytoGen.customsClearanceOfficeCode?.toString()
        String declarationSerial=ephytoGen.declarationSerial?.toString()
        String declarationNumber=ephytoGen.declarationNumber?.toString()
        String declarationDate = ephytoGen.declarationDate ? new SimpleDateFormat("dd/MM/yyyy").format(ephytoGen.declarationDate.toDate()) : null

        sadService.checkIfSadExists(clearanceOfficeCode, declarationSerial,declarationNumber,declarationDate, ephytoGen.exporterCode, request)
    }


    static boolean isCommitOperationValid(){
        WebRequestUtils.getCommitOperation()?.toString() in [Operations.OP_REQUEST,Operations.OP_UPDATE,Operations.OI_REQUEST_STORED, Operations.OI_REQUEST_QUERIED, Operations.OI_UPDATE_QUERIED]
    }

    static boolean isSadCheckEnabled(){
        return Holders.config.rest.sad.enabled
    }

}
