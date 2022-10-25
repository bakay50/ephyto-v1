package com.webbfontaine.ephyto.erimm

import com.webbfontaine.ephyto.config.FieldsConfiguration
import com.webbfontaine.ephyto.applicator.ApplicatorBusinessLogicRule
import com.webbfontaine.grails.plugins.taglibs.ConfigurableFields
import com.webbfontaine.grails.plugins.workflow.operations.Operation

class Applicator implements ConfigurableFields, Serializable{
    Long id
    String code
    String agreement
    String nameAddress
    String startedOperation
    String status
    LinkedHashSet<Operation> operations
    boolean isDocumentEditable = false

    static transients  = ['startedOperation', 'isDocumentEditable', 'operations']

    static rules = {
        [ApplicatorBusinessLogicRule]
    }

    static constraints = {
        code maxSize: 10 , unique: true,nullable: false
        agreement maxSize: 20,nullable: false
    }

    static mapping = {
        table('RIMM_APL')
        id column: 'ID'
        code column: 'CODE'
        agreement column: 'AGREEMENT'
        nameAddress column: 'ADDRESS', sqlType: 'CLOB'
        status column: 'STATUS'
        version(false)
    }

    def boolean isFieldEditable(String fieldName) {
        return (this.isDocumentEditable && !FieldsConfiguration.isApplicatorFieldProhibited(fieldName, this))
    }

    def boolean isFieldMandatory(String fieldName) {
        if (this.isDocumentEditable) {
            return FieldsConfiguration.isApplicatorFieldHidden(fieldName, this)
        }
        return false
    }

    void setIsDocumentEditable(boolean editable) {
        this.isDocumentEditable = editable
    }

    boolean isDocumentEditable() {
        return this.isDocumentEditable
    }

    def boolean isApplicatorMandatory(String fieldName) {
        if (this.isDocumentEditable) {
            return FieldsConfiguration.isApplicatorFieldMandatory(fieldName, this)
        }
        return false
    }

}
