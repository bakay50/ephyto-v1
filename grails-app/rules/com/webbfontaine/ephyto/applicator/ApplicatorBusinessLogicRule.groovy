package com.webbfontaine.ephyto.applicator

import com.webbfontaine.ephyto.BusinessLogicUtils
import com.webbfontaine.ephyto.WebRequestUtils
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext

class ApplicatorBusinessLogicRule implements Rule{
    @Override
    void apply(RuleContext ruleContext) {
        if (params.validationRequired) {
            if ([Operations.OP_REGISTER].contains(commitOperation)) {
                BusinessLogicUtils.executeSetOfRules(CREATE_UPDATE_RULES, ruleContext)
            }
            BusinessLogicUtils.executeSetOfRules(VALIDATION_RULES, ruleContext)
        }
    }

    private static final VALIDATION_RULES = [
            new CheckCodeApplicatorRule()
    ]

    private static final CREATE_UPDATE_RULES = [
            new ApplicatorMandatoryFieldRule()
    ]
    private static getParams() {
        WebRequestUtils.getParams()
    }
    private static getCommitOperation() {
        WebRequestUtils.getCommitOperation()
    }

}
