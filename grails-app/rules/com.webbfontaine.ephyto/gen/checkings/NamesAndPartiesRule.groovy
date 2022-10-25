package com.webbfontaine.ephyto.gen.checkings


import com.webbfontaine.ephyto.BusinessLogicUtils
import com.webbfontaine.ephyto.constants.UserPropertyConstants
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
 * Developer: Bakayoko Abdoulaye
 * Date: 07/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class NamesAndPartiesRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(NamesAndPartiesRule.class);

    private String codeField
    private String nameField
    private String addrField
    private String partyNameField
    protected Class domainClass
    protected boolean useQueryCache
    protected String userPropName

    NamesAndPartiesRule() {
    }

    NamesAndPartiesRule(String codeField, String nameField, String addrField, String partyNameField, Class domainClass, boolean useQueryCache = true, String userPropName) {
        this.codeField = codeField
        this.nameField = nameField
        this.addrField = addrField
        this.partyNameField = partyNameField
        this.domainClass = domainClass
        this.useQueryCache = useQueryCache
        this.userPropName = userPropName
    }

    @Override
    void apply(RuleContext ruleContext) {
        EphytoGen ephytoGen = ruleContext.getTargetAs(EphytoGen)
        setDefaultProperty(ephytoGen)

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
                    ruleContext.rejectValue(codeField, "ephytoGen.invalid.${codeField}")
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
        def address1 = htObject['address1'] ?: ""
        def address2 = htObject['address2'] ?: ""
        def address3 = htObject['address3'] ?: ""
        def address4 = htObject['address4'] ?: ""
        return "$address1 $address2 $address3 $address4"
    }

    private static Date getWorkingDate(domainInstance) {
        def workingDate = LocalDate.now()
        def dateOnly = workingDate.toDate().clearTime()
        return dateOnly
    }



    def getHtObject(domainInstance,codeFieldVal) {
        def criteriaClosure = {
            eq("code", codeFieldVal)
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

    private def setDefaultProperty(EphytoGen ephytoGen) {
        if (userPropName) {
            List userPropertyValues = BusinessLogicUtils.getUserPropertyValueByBeanAlias(userPropName)
            if (userPropertyValues && !(UserPropertyConstants.ALL in userPropertyValues)
                    && userPropertyValues.size() == 1) {
                ephytoGen[codeField] = userPropertyValues?.get(0)
            }
        }
    }
}
