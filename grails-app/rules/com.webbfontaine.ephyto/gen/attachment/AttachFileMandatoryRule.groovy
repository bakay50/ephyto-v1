package com.webbfontaine.ephyto.gen.attachment


import com.webbfontaine.grails.plugins.utils.WebRequestUtils
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.gen.EphytoGen

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 03/10/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class AttachFileMandatoryRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachFileMandatoryRule.class);

    @Override
    void apply(RuleContext ruleContext) {
        LOGGER.info("Operations managed : ${WebRequestUtils.getCommitOperation()?.toString()}")
        EphytoGen ephytoGen = ruleContext?.getTargetAs(EphytoGen)
        if(isCommitOperationValid([Operations.OP_STORE,Operations.OI_UPDATE_STORED,Operations.OP_REQUEST,Operations.OI_UPDATE_QUERIED,Operations.OI_REQUEST_STORED,Operations.OI_REQUEST_QUERIED])){
            checkAttachedFile(ephytoGen)
        }

    }

    public static checkAttachedFile(EphytoGen ephytoGen) {
        if(ephytoGen && ephytoGen?.attachments?.size() == 0){
            ephytoGen.errors.rejectValue('attachments', "attachment.attDoc.errors.file.mandatory", "File is Mandatory.")
        }
    }

    private static boolean isCommitOperationValid(List validOperations){
        WebRequestUtils.getCommitOperation()?.toString() in validOperations
    }
}
