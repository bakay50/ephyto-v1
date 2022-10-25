package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.*

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Yacouba SYLLA
 * Date: 5/18/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class LogMessageRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogMessageRule.class)

    @Override
    void apply(RuleContext ruleContext) {
        EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)
        def startTime = System.currentTimeMillis()
        LOGGER.debug("id = {}, cid = {}. Started. ", ephytoGen.id, conversationId)

        checkIfHasQueryMessage(ruleContext, ephytoGen)

        LOGGER.info("id = {}, cid = {}. Execution took {}ms", ephytoGen.id, conversationId, (System.currentTimeMillis() - startTime))
    }

    private static void checkIfHasQueryMessage(RuleContext ruleContext, EphytoGen ephytoGen) {
        if (!params.message) {
            ruleContext.rejectValue("message", "ephytoGen.message.mandatory", "Message is mandatory!")
        } else {
            ephytoGen?.message = null
        }
    }
}
