package com.webbfontaine.ephyto.gen.attachment

import com.webbfontaine.ephyto.BusinessLogicUtils

import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch

import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getConversationId

class AttachmentBusinessLogicRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBusinessLogicRule.class);

    private static def COMMON_RULES = []

    @Override
    void apply(RuleContext ruleContext) {
        Attachment attachment = ruleContext.getTargetAs(Attachment)

        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        LOGGER.debug("id = {}, cid = {}. Started. ", attachment.attNumber, conversationId)
        BusinessLogicUtils.executeSetOfRules(COMMON_RULES, ruleContext)
    }
}
