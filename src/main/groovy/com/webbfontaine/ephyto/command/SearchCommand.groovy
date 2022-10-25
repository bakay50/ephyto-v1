package com.webbfontaine.ephyto.command

import com.webbfontaine.ephyto.TypeCastUtils

import com.webbfontaine.grails.plugins.search.annotations.CriteriaField
import com.webbfontaine.grails.plugins.search.annotations.ResultField
import com.webbfontaine.grails.plugins.search.SearchResultOptions
import com.webbfontaine.grails.plugins.search.SearchUtils
import grails.databinding.BindingFormat
import grails.validation.Validateable
import org.joda.time.LocalDate

import java.lang.reflect.Field

/**
 * Copyrights 1002-1010 Webb Fontaine
 * Developer: Y. SYLLA
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class SearchCommand implements Validateable,SearchResultOptions {

    static ArrayList<Field> resultFields
    static HashMap<String, ResultField> results

    static {
        results = SearchUtils.getResults(this)
        resultFields = SearchUtils.getResultFields(this)
    }

    static constraints = {
        def nullableFields = ['status','requestNumber','requestDate','requestDateTo','op_requestDate','customsClearanceOfficeCode','exporterCode','applicatorCode','declarantCode','disinfectionCertificateRef','disinfectionCertificateDate','disinfectionCertificateDateTo','dockingPermissionRef',
        'dockingPermissionDate','dockingPermissionDateTo','op_dockingPermissionDate','phytosanitaryCertificateRef',
        'phytosanitaryCertificateDate','phytosanitaryCertificateDateTo','op_phytosanitaryCertificateDate','userReference']
    
        nullableFields.each {
            "$it" (nullable: true)
        }
    }

    public SearchCommand() {}

    public SearchCommand(Map map) {
        metaClass.setProperties(this, map.findAll { key, value -> this.hasProperty(key) })
    }

    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.status.label', pattern = 'translatable')

    String status

    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.requestNumber.label')
    String requestNumber
    
    @BindingFormat("dd/MM/yyyy")
    @CriteriaField(operator = "")
    @ResultField(width = 10, displayName = 'ephytoGen.search.requestDate.label', pattern = "date")
    LocalDate requestDate
    LocalDate requestDateTo
    String op_requestDate

    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.customsClearanceOfficeCode.label')
    String customsClearanceOfficeCode

    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.exporterCode.label')
    String exporterCode

    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.declarantCode.label')
    String declarantCode

    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.dockingPermissionRef.label') 
    String dockingPermissionRef
    
    
    @BindingFormat("dd/MM/yyyy")
    @CriteriaField(operator = "")
    @ResultField(width = 10, displayName = 'ephytoGen.search.dockingPermissionDate.label', pattern = "date")
    LocalDate dockingPermissionDate
    LocalDate dockingPermissionDateTo

    
    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.phytosanitaryCertificateRef.label')
    String phytosanitaryCertificateRef

    
    @BindingFormat("dd/MM/yyyy")
    @CriteriaField(operator = "")
    @ResultField(width = 10, displayName = 'ephytoGen.search.phytosanitaryCertificateDate.label', pattern = "date")
    LocalDate phytosanitaryCertificateDate
    LocalDate phytosanitaryCertificateDateTo


    
    @CriteriaField(operator = "equals")
    @ResultField(width = 10, displayName = 'ephytoGen.search.userReference.label') 
    String userReference


    String op_phytosanitaryCertificateDate
    String op_dockingPermissionDate
    String op_disinfectionCertificateDate
    LocalDate disinfectionCertificateDate
    LocalDate disinfectionCertificateDateTo
    String disinfectionCertificateRef
    String applicatorCode
           
    @Override
    Map getSearchParams(Object inputBean) {
        Map searchParams = [:]
        inputBean.status ? searchParams.put('status', inputBean.status) : ''
        inputBean.requestNumber ? searchParams.put('requestNumber', inputBean.requestNumber) : ''
        inputBean.requestDate ? searchParams.put('requestDate', TypeCastUtils.formatDate(inputBean.requestDate)) : ''
        inputBean.requestDateTo ? searchParams.put('requestDateTo', TypeCastUtils.formatDate(inputBean.requestDateTo)) : ''
        inputBean.op_requestDate ? searchParams.put('op_requestDate', inputBean.op_requestDate) : ''

        inputBean.customsClearanceOfficeCode ? searchParams.put('customsClearanceOfficeCode', inputBean.customsClearanceOfficeCode) : ''
        inputBean.exporterCode ? searchParams.put('exporterCode', inputBean.exporterCode) : ''
        inputBean.applicatorCode ? searchParams.put('applicatorCode', inputBean.applicatorCode) : ''
        inputBean.declarantCode ? searchParams.put('declarantCode', inputBean.declarantCode) : ''

        inputBean.disinfectionCertificateRef ? searchParams.put('disinfectionCertificateRef', inputBean.disinfectionCertificateRef) : ''
        inputBean.disinfectionCertificateDate ? searchParams.put('disinfectionCertificateDate', TypeCastUtils.formatDate(inputBean.disinfectionCertificateDate)) : ''
        inputBean.disinfectionCertificateDateTo ? searchParams.put('disinfectionCertificateDateTo', TypeCastUtils.formatDate(inputBean.disinfectionCertificateDateTo)) : ''
        inputBean.op_disinfectionCertificateDate ? searchParams.put('op_disinfectionCertificateDate', inputBean.op_disinfectionCertificateDate) : ''

        inputBean.dockingPermissionRef ? searchParams.put('dockingPermissionRef', inputBean.dockingPermissionRef) : ''
        inputBean.dockingPermissionDate ? searchParams.put('dockingPermissionDate', TypeCastUtils.formatDate(inputBean.dockingPermissionDate)) : ''
        inputBean.dockingPermissionDateTo ? searchParams.put('dockingPermissionDateTo', TypeCastUtils.formatDate(inputBean.dockingPermissionDateTo)) : ''
        inputBean.op_dockingPermissionDate ? searchParams.put('op_dockingPermissionDate', inputBean.op_dockingPermissionDate) : ''
        inputBean.phytosanitaryCertificateRef ? searchParams.put('phytosanitaryCertificateRef', inputBean.phytosanitaryCertificateRef) : ''
        inputBean.phytosanitaryCertificateDate ? searchParams.put('phytosanitaryCertificateDate', TypeCastUtils.formatDate(inputBean.phytosanitaryCertificateDate)) : ''
        inputBean.phytosanitaryCertificateDateTo ? searchParams.put('phytosanitaryCertificateDateTo', TypeCastUtils.formatDate(inputBean.phytosanitaryCertificateDateTo)) : ''
        inputBean.op_phytosanitaryCertificateDate ? searchParams.put('op_phytosanitaryCertificateDate', inputBean.op_phytosanitaryCertificateDate) : ''
        inputBean.userReference ? searchParams.put('userReference', inputBean.userReference) : ''
        
        return searchParams
    }

    @Override
    ArrayList<Field> getResultFields() {
        if (!resultFields) {
            resultFields = SearchUtils.getResultFields(this)
        }
        return resultFields
    }

    @Override
    HashMap<String, ResultField> getResults() {
        if (!results) {
            results = SearchUtils.getResults(this)
        }
        
        return results
    }
}
