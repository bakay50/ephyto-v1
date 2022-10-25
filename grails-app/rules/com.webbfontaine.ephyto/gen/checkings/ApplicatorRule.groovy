package com.webbfontaine.ephyto.gen.checkings


import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import com.webbfontaine.grails.plugins.utils.WebRequestUtils
import org.joda.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getConversationId
import static com.webbfontaine.grails.plugins.validation.rules.ref.utils.RefUtils.isHistorized
import static com.webbfontaine.grails.plugins.validation.rules.ref.utils.RefUtils.withHistorizedCriteria

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 07/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class ApplicatorRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicatorRule.class);

    private String codeField
    private String nameField
    private String addrField
    private String partyNameField
    protected Class domainClass
    protected boolean useQueryCache


    ApplicatorRule() {
    }

    ApplicatorRule(String codeField, String nameField, String addrField, String partyNameField, Class domainClass, boolean useQueryCache = true) {
        this.codeField = codeField
        this.nameField = nameField
        this.addrField = addrField
        this.partyNameField = partyNameField
        this.domainClass = domainClass
        this.useQueryCache = useQueryCache
    }

    @Override
    void apply(RuleContext ruleContext) {
        EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)


        if (ephytoGen.isFieldEditable(codeField)) {

            if (!ephytoGen[codeField]) {
                clearFields(ephytoGen)
                return
            }

            Date htDate = getWorkingDate(ephytoGen)
            def htObject = getHtObject(ephytoGen,ephytoGen[codeField])

            if (htObject) {
                if (ephytoGen[nameField] && ephytoGen[nameField] != htObject[partyNameField]) {
                    LOGGER.info("id = {}, cid = {}. Setting {}, Old val = '{}' New Val = '{}'", ephytoGen.id, conversationId, nameField, ephytoGen[nameField], htObject[partyNameField])
                }
                LOGGER.trace("id = {}, cid = {}. Setting {}, Old val = '{}' New Val = '{}'", ephytoGen.id, conversationId, nameField, ephytoGen[nameField], htObject[partyNameField])
                ephytoGen[nameField] = htObject[partyNameField]
                ephytoGen[addrField] = getAddress(htObject)
            } else {
                LOGGER.debug("{} with cod '{}' not found. htDate = {}. UseQueryCache = {}", domainClass.getSimpleName(), ephytoGen[codeField], htDate, useQueryCache)
                clearFields(ephytoGen)
                if (validationRequired) {
                    ruleContext.rejectValue(codeField, "ephytoGenApplicator.invalid.${codeField}")
                }
            }
        }
    }

    private getValidationRequired() {
        return WebRequestUtils.getValidationRequired()
    }

    private clearFields(EphytoGen ephytoGen) {
        ephytoGen[nameField] = null
        ephytoGen[addrField] = null
    }

    private static def getAddress(htObject) {
        def nameAddress = htObject['nameAddress'] ?: ""
        return "$nameAddress"
    }

    private static Date getWorkingDate(domainInstance) {
        def workingDate = LocalDate.now()
        def dateOnly = workingDate.toDate().clearTime()
        return dateOnly
    }

    def getHtObject(domainInstance,codeFieldVal) {
        def criteriaClosure = {
            eq(codeField, codeFieldVal)
            cache(useQueryCache)
        }

        if (isHistorized(domainClass)) {
            criteriaClosure = withHistorizedCriteria(getWorkingDate(domainInstance), criteriaClosure)
        }

        def refObject = null

        try {
            def queryResult = domainClass.createCriteria().list() {
                criteriaClosure.delegate = delegate
                criteriaClosure()
            }

            if (queryResult?.size() > 0) {
                LOGGER.debug("Total {} instances of {} were found", queryResult.size(), domainClass.getSimpleName())
                refObject = queryResult[0]
            }
        } catch (e) {
            LOGGER.error("", e)
        }

        return refObject
    }

}
