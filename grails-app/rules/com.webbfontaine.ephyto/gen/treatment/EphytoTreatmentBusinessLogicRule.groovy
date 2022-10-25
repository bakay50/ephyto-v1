package com.webbfontaine.ephyto.gen.treatment


import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder
import com.webbfontaine.ephyto.gen.treatment.setters.ApplicatorTreatmentRule
import com.webbfontaine.ephyto.gen.treatment.checkings.CheckTreatmentApplicatorRule
import com.webbfontaine.ephyto.gen.treatment.checkings.CheckMaximumTreatmentRule
import com.webbfontaine.ephyto.BusinessLogicUtils
import com.webbfontaine.ephyto.gen.treatment.checkings.CheckTreatmentDateRule
import com.webbfontaine.ephyto.gen.treatment.checkings.EphytoTreatmentMaxLengthRule
import static com.webbfontaine.ephyto.constants.Operations.OI_REQUEST_QUERIED
import static com.webbfontaine.ephyto.constants.Operations.OI_REQUEST_STORED
import static com.webbfontaine.ephyto.constants.Operations.OP_REQUEST
import com.webbfontaine.ephyto.WebRequestUtils

class EphytoTreatmentBusinessLogicRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(EphytoTreatmentBusinessLogicRule.class);

    def grailsApplication

    private static final def COMMON_RULES = [
      new EphytoTreatmentMaxLengthRule()
    ]
    private static final REQUEST_RULES = [
       new CheckTreatmentDateRule()
    ]

    private static final def SETTER_RULES = [
            new ApplicatorTreatmentRule('applicatorCode', 'applicatorAgreement','applicatorNameAddress','agreement',Applicator),
            new CheckTreatmentApplicatorRule(),
            new CheckMaximumTreatmentRule()
    ]

    @Override
    void apply(RuleContext ruleContext) {
        EphytoTreatment ephytoTreatment = ruleContext.getTargetAs(EphytoTreatment)

        try {
            def params = ((GrailsWebRequest) RequestContextHolder.currentRequestAttributes()).getParams()
            def conversationId = params.conversationId

            LOGGER.debug("id = {}, cid = {}. Started. ", ephytoTreatment.id, conversationId)

            BusinessLogicUtils.executeSetOfRules(SETTER_RULES, ruleContext)
            if([OP_REQUEST,OI_REQUEST_STORED,OI_REQUEST_QUERIED].contains(commitOperation)){
                BusinessLogicUtils.executeSetOfRules(REQUEST_RULES, ruleContext)
            }
            if (params.validationRequired) {
                BusinessLogicUtils.executeSetOfRules(COMMON_RULES, ruleContext)
            }

        }catch (IllegalStateException e){
            LOGGER.debug("SKIPPING FOR INITIAL TEST DATA")
        }
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
}
