package com.webbfontaine.ephyto.setters

import com.webbfontaine.grails.plugins.rimm.hist.HistorizationSupport
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.util.GrailsNameUtils
import org.joda.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getConversationId
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getValidationRequired
import static grails.util.GrailsNameUtils.getPropertyNameRepresentation

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 01/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
abstract class ValueSetterRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValueSetterRule.class);

    protected String codeField
    protected String nameField
    protected Class domainClass
    protected String htRefNameField
    protected boolean useQueryCache

    @Override
    void apply(RuleContext ruleContext) {
        Object domainInstance = ruleContext.getTarget()

        if (!isFieldEditable(domainInstance, codeField)) {
            return
        }

        if (!domainInstance[codeField]) {
            domainInstance[nameField] = null
            return
        }

        def htRefObject

        if (isHt()) {
            Date htDate = getWorkingDate(domainInstance)
            htRefObject = getHtObject(domainInstance, htDate)
        } else {
            htRefObject = getRefObject(domainInstance)
        }

        setValue(htRefObject, domainInstance, ruleContext)
    }

    private void setValue(refObject, domainInstance, RuleContext ruleContext) {
        if (refObject) {
            if (domainInstance[nameField] && domainInstance[nameField] != refObject[htRefNameField]) {
                LOGGER.info("id = {}, cid = {}. Setting {}, Old val = '{}' New Val = '{}'", domainInstance.id, conversationId, nameField, domainInstance[nameField], refObject[htRefNameField])
            }

            LOGGER.trace("id = {}, cid = {}. Setting {}, Old val = '{}' New Val = '{}'", domainInstance.id, conversationId, nameField, domainInstance[nameField], refObject[htRefNameField])
            domainInstance[nameField] = refObject[htRefNameField]
        } else {
            LOGGER.debug("{} with code '{}' not found. UseQueryCache = {}", domainClass.getSimpleName(), domainInstance[codeField], useQueryCache)
            domainInstance[nameField] = null
            if (validationRequired) {
                def value = domainInstance[codeField]
                boolean hasRankName = domainInstance.hasProperty(getRankName())
                if(hasRankName){
                    Integer rank = getRankValue(domainInstance)
                    ruleContext.rejectValue(codeField, "${getPropertyNameRepresentation(type)}.invalid.${codeField}", [rank, value] as Object[],
                            "${getPropertyNameRepresentation(type)} ${rank} : Invalid ${codeField} ${value}")
                }else{
                    ruleContext.rejectValue(codeField, "${getPropertyNameRepresentation()}.invalid.${codeField}")
                }
            }
        }
    }

    private def getRefObject(domainInstance) {
        return domainClass.findByCode(domainInstance[codeField] as String, [cache: useQueryCache])
    }

    private def getHtObject(domainInstance, Date htDate) {
        def htObject = HistorizationSupport.withHistorizedFinder(htDate) {
            domainClass.findByCode(domainInstance[codeField], [cache: useQueryCache])
        }
        return htObject
    }

    private getPropertyNameRepresentation() {
        GrailsNameUtils.getPropertyNameRepresentation(domainClass)
    }

    protected Date getWorkingDate(domainInstance) {
        def workingDate = LocalDate.now()
        def dateOnly = workingDate.toDate().clearTime()
        return dateOnly
    }

    protected abstract boolean isFieldEditable(domainInstance, String field)

    protected abstract boolean isHt()

    protected String getRankName(){
        return "rank"
    }

    protected Integer getRankValue(domainInstance){
        return domainInstance[getRankName()]?domainInstance[getRankName()]:0
    }

    protected Class getType() {
        return null
    };
}
