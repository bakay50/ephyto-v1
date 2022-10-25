<%@ page import="grails.util.Holders" %>
%{--Hidden fields for Ephyto Gen--}%
<wf:hiddenField name="beanDataLoad" value="${createLink(action: 'loadData', controller: 'beanDataLoad')}"/>

<g:hiddenField name="addTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'addTreatment')}"/>
<g:hiddenField name="editTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'editTreatment')}"/>
<g:hiddenField name="showTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'showTreatment')}"/>
<g:hiddenField name="deleteTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'deleteTreatment')}"/>
<g:hiddenField name="closeTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'closeTreatment')}"/>
<g:hiddenField name="saveTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'saveTreatment')}"/>
<g:hiddenField name="updateTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'updateTreatment')}"/>
<g:hiddenField name="removeTreatmentUrl" value="${createLink(controller: 'ephytoTreatment', action: 'removeTreatment')}"/>

<wf:hiddenField name="exchangeRateFormat" value="${Holders.grailsApplication.config.exchangeRateFormat}"/>
<wf:hiddenField name="monetaryNumberFormat" value="${Holders.grailsApplication.config.monetaryNumberFormat}"/>
<wf:hiddenField name="quantityNumberFormat" value="${Holders.grailsApplication.config.quantityNumberFormat}"/>
<wf:hiddenField name="xmlAcceptedFormats" disabled="" value="${Holders.grailsApplication.config.xmlAcceptedFormats.join(',')}"/>
<wf:hiddenField name="exportXmlUrl" value="${createLink(controller: 'xml',action: 'exportXML')}"/>
<wf:hiddenField name="fruitMention" value="${com.webbfontaine.ephyto.constants.EphytoConstants.PRODUCT_FRUIT_MENTION}"/>
<wf:hiddenField name="boisMention" value="${com.webbfontaine.ephyto.constants.EphytoConstants.PRODUCT_BOIS_MENTION}"/>
<wf:hiddenField name="mangueMention" value="${com.webbfontaine.ephyto.constants.EphytoConstants.PRODUCT_MANGUE_MENTION}"/>
<wf:hiddenField name="locale" id="locale" value="${org.springframework.context.i18n.LocaleContextHolder.getLocale()}"/>

