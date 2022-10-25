package com.webbfontaine.ephyto.traits


import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static com.webbfontaine.grails.plugins.utils.TypesCastUtils.isEmpty
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getConversationId
import static grails.util.GrailsNameUtils.getPropertyNameRepresentation

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 15/02/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
trait MandatoryFieldsRule<T> implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(MandatoryFieldsRule.class);

    abstract Set<String> defineMandatoryFields()

    abstract Map<String, String> defineHumanNames()

    abstract Class<T> getType()


    public String getRankName() {
        return "rank"
    }

    public String getHumanName(String key) {
        if (defineHumanNames().containsKey(key)) {
            return defineHumanNames()[key]
        }
        return key
    }


    @Override
    void apply(RuleContext ruleContext) {
        def domainInstance = ruleContext.getTargetAs(getType())
        checkMandatoryFields(ruleContext, domainInstance)
    }

    private void checkMandatoryFields(ruleContext, T domainInstance) {
        boolean hasRankName = domainInstance.hasProperty(getRankName())
        def mandatoryFields = defineMandatoryFields()
        mandatoryFields.each {
            if (isEmpty(domainInstance[it])) {
                LOGGER.debug("id = {}, cid = {}. {} is Mandatory, Value = '{}'", domainInstance.id, conversationId, it, domainInstance[it])
                String errorMessage = "${getPropertyNameRepresentation(type)}.errors.${it}.required"
                if (hasRankName) {
                    int rank = domainInstance[getRankName()] ? domainInstance[getRankName()] : 0
                    ruleContext.errors.rejectValue(it as String, "errorMessageWithRank",[getHumanName(getPropertyNameRepresentation(type)),rank, getHumanName(it)] as Object[], "${getHumanName(getPropertyNameRepresentation(type))} ${rank} : ${getHumanName(it)} is Mandatory")
                } else {
                    ruleContext.rejectValue(it as String, "errorMessageNoRank", [getHumanName(it)] as Object[], "${getHumanName(it)} is Mandatory")
                }
            }
        }
    }
}
