package com.webbfontaine.ephyto.applicator

import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import groovy.util.logging.Slf4j

@Slf4j("LOGGER")
class CheckCodeApplicatorRule implements Rule{
    @Override
    void apply(RuleContext ruleContext) {
        LOGGER.debug("In apply of ${CheckCodeApplicatorRule}")
        Applicator applicator = ruleContext.getTargetAs(Applicator) as Applicator
        checkCodeApplicator(applicator)
    }

    static def checkCodeApplicator(applicator){
        Applicator foundApplicator = Applicator.findByCode(applicator?.code)
        if(foundApplicator && foundApplicator?.id != applicator?.id){
            applicator.errors.rejectValue('code', 'applicator.code.error', 'The Applicator code already exists.')
        }
    }
}
