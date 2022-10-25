package com.webbfontaine.ephyto.command

import com.webbfontaine.grails.plugins.search.SearchResultOptions
import com.webbfontaine.grails.plugins.search.SearchUtils
import com.webbfontaine.grails.plugins.search.annotations.CriteriaField
import com.webbfontaine.grails.plugins.search.annotations.ResultField
import grails.validation.Validateable

import java.lang.reflect.Field

class ApplicatorSearchCommand implements Validateable, SearchResultOptions{
    static ArrayList<Field> resultFields
    static HashMap<String, ResultField> results
    static {
        results = SearchUtils.getResults(this)
        resultFields = SearchUtils.getResultFields(this)
    }

    @CriteriaField(operator = "equals")
    @ResultField(displayName = "applicator.status.label", pattern = "translatable")
    String status

    @CriteriaField(operator = "equals")
    @ResultField(displayName = "applicator.agreement.label")
    String agreement

    @CriteriaField(operator = "equals")
    @ResultField(displayName = "applicator.code.label")
    String code

    @CriteriaField(operator = "equals")
    @ResultField(displayName = "applicator.address.label")
    String nameAddress

    @Override
    Map getSearchParams(Object inputBean) {
        return null
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

    Map getSearchParams() {
        return getSearchParams(this)
    }



}
