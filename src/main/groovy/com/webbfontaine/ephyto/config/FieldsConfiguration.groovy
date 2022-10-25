/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webbfontaine.ephyto.config

import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.erimm.Applicator

import static com.webbfontaine.ephyto.workflow.Operation.REGISTER
import static com.webbfontaine.ephyto.workflow.Operation.ACTIVATE
import static com.webbfontaine.ephyto.workflow.Operation.CANCEL_VALID

import static  com.webbfontaine.ephyto.constants.EphytoConstants.CREATE
import com.webbfontaine.ephyto.gen.EphytoGen

import grails.util.Holders
import static com.webbfontaine.ephyto.config.Config.*
import static com.webbfontaine.ephyto.constants.Statuses.ST_APPROVED
import static com.webbfontaine.ephyto.constants.Statuses.ST_CANCELED
import static com.webbfontaine.ephyto.constants.Statuses.ST_REJECTED
import static com.webbfontaine.ephyto.constants.Statuses.ST_QUERIED
import static com.webbfontaine.ephyto.constants.Statuses.ST_REPLACED
import static com.webbfontaine.ephyto.constants.Statuses.ST_REQUESTED
import static com.webbfontaine.ephyto.constants.Statuses.ST_SIGNED
import static com.webbfontaine.ephyto.constants.Statuses.ST_STORED

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Y. SYLLA.
 * Date: 15/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class FieldsConfiguration {
    
  private static int indexOfStatus(status) {
        //  ['Null','Stored', 'Requested', 'Queried', 'Canceled', 'Rejected', 'Approved', 'Replaced']
        def result
        switch (status) {
            case null:
                result = 0
                break
            case ST_STORED:
                result = 1
                break
            case ST_REQUESTED:
                result = 2
                break
            case ST_QUERIED:
                result = 3
                break
            case ST_CANCELED:
                result = 4
                break
            case ST_REJECTED:
                result = 5
                break
            case ST_APPROVED:
                result = 6
                break
            case ST_REPLACED:
                result =7
                break
            case ST_SIGNED:
                result =8
                break

            default:
                result = -1
        }
        return result
    }

    static Integer indexOfApplicatorOperation(def startedOperation){
        def index
        switch (startedOperation){
            case null:
            case CREATE:
            case REGISTER:
                index = 0
                break
            case Operations.OC_EDIT_VALID:
            case Operations.OC_EDIT_INVALID:
                index = 1
                break
            case EphytoConstants.SHOW:
            case Operations.OP_CANCEL_VALID:
            case ACTIVATE:
                index = 2
                break
            default:
                index = -1
        }

        return index
    }


    public static boolean isGenFieldMandatory(String fieldName, EphytoGen ephytoGen) {
        MANDATORY.isConform(getFieldConfigForGenDoc(fieldName, ephytoGen))
    }

    public static boolean isGenFieldProhibited(String fieldName,EphytoGen ephytoGen) {
        PROHIBITED.isConform(getFieldConfigForGenDoc(fieldName, ephytoGen))
    }

    public static boolean isTreatmentFieldProhibited(String fieldName,EphytoGen ephytoGen) {
        PROHIBITED.isConform(getFieldConfigForGenTreatmentDoc(fieldName, ephytoGen))

    }

    public static boolean isTreatmentFieldMandatory(String fieldName,EphytoGen ephytoGen) {
        MANDATORY.isConform(getFieldConfigForGenTreatmentDoc(fieldName, ephytoGen))
    }

    public static boolean isEphytoLogProhibited(String fieldName, EphytoGen ephytoGen) {
        PROHIBITED.isConform(getFieldConfigForLog(fieldName, ephytoGen))
    }

    public static boolean isAttachmentProhibited(String fieldName, EphytoGen ephytoGen) {
        PROHIBITED.isConform(getFieldConfigForAttachmentDoc(fieldName, ephytoGen))
    }

    public static boolean isItemGoodProhibited(String fieldName, EphytoGen ephytoGen) {
        PROHIBITED.isConform(getFieldConfigForItemGood(fieldName, ephytoGen))
    }

    public static boolean isItemGoodHidden(String fieldName, EphytoGen ephytoGen) {
        HIDDEN.isConform(getFieldConfigForItemGood(fieldName, ephytoGen))
    }
    public static boolean isGenFieldHidden(String fieldName,EphytoGen ephytoGen) {
        HIDDEN.isConform(getFieldConfigForGenDoc(fieldName, ephytoGen))
    }
    public static boolean isApplicatorFieldMandatory(String fieldName, Applicator applicator) {
        MANDATORY.isConform(getFieldConfigForApplicator(fieldName, applicator))
    }
    public static boolean isApplicatorFieldProhibited(String fieldName,Applicator applicator) {
        PROHIBITED.isConform(getFieldConfigForApplicator(fieldName, applicator))
    }
    public static boolean isApplicatorFieldHidden(String fieldName,Applicator applicator) {
        HIDDEN.isConform(getFieldConfigForApplicator(fieldName, applicator))
    }
    private static getFieldConfigByStatus(ArrayList<String> fieldConfig, EphytoGen ephytoGen, String fieldName) {
        if (fieldConfig) {

            fieldConfig.get(indexOfStatus(ephytoGen.status))
        } else {
            throw new FieldsConfigurationException("no configuration found for field ${fieldName}")
        }
    }

    public static String getFieldConfigForGenDoc(String fieldName, EphytoGen ephytoGen) {
        def fieldConfig = getFieldConfigForGen(fieldName)
        getFieldConfigByStatus(fieldConfig, ephytoGen, fieldName)
    }

    public static String getFieldConfigForGenTreatmentDoc(String fieldName, EphytoGen ephytoGen) {
        def fieldConfig = getFieldConfigForGenTreatment(fieldName)
        getFieldConfigByStatus(fieldConfig, ephytoGen, fieldName)
    }

    private static String getFieldConfigForLog(String fieldName, EphytoGen ephytoGen) {
        def fieldConfig = getFieldConfigForEphytoLog(fieldName)
        getFieldConfigByStatus(fieldConfig, ephytoGen, fieldName)
    }

    private static String getFieldConfigForAttachmentDoc(String fieldName, EphytoGen ephytoGen) {
        def fieldConfig = getFieldConfigForAttachment(fieldName)
        getFieldConfigByStatus(fieldConfig, ephytoGen, fieldName)
    }

    private static String getFieldConfigForItemGood(String fieldName, EphytoGen ephytoGen) {
        def fieldConfig = getFieldConfigForItemGood(fieldName)
        getFieldConfigByStatus(fieldConfig, ephytoGen, fieldName)
    }

    private static ArrayList<String> getFieldConfigForEphytoLog(String fieldName) {
        return getEphytoLogFieldConfig()."${fieldName}" as ArrayList<String>
    }

    private static ArrayList<String> getFieldConfigForGen(String fieldName) {
        return getEphytoGenConfig()."${fieldName}" as ArrayList<String>
    }

    public static String getFieldConfigForApplicator(String fieldName, Applicator applicator) {
        def fieldConfig = getFieldConfigApplicator(fieldName)
        getFieldConfigByOperation(fieldConfig, applicator, fieldName)
    }

    private static getApplicatorConfig() {
        getAppFieldsConfig().ephyto.applicator
    }

    private static getFieldConfigByOperation(ArrayList<String> fieldConfig, Applicator applicator, String fieldName) {
        if (fieldConfig) {
            fieldConfig.get(indexOfApplicatorOperation(applicator.startedOperation))
        } else {
            throw new FieldsConfigurationException("no configuration found for field ${fieldName}")
        }
    }

    private static getEphytoGenConfig() {
        getAppFieldsConfig().ephyto.gen
    }

    private static ArrayList<String> getFieldConfigForAttachment(String fieldName) {
        return getAttachmentFieldConfig()."${fieldName}" as ArrayList<String>
    }

    private static ArrayList<String> getFieldConfigForItemGood(String fieldName) {
        return getItemGoodFieldConfig()."${fieldName}" as ArrayList<String>
    }

    private static ArrayList<String> getFieldConfigForGenTreatment(String fieldName) {
        return getEphytoTreatmentFieldConfig()."${fieldName}" as ArrayList<String>
    }
    private static ArrayList<String> getFieldConfigApplicator(String fieldName) {
        return getApplicatorConfig()."${fieldName}" as ArrayList<String>
    }

    private static getEphytoTreatmentFieldConfig() {
        getAppFieldsConfig().ephyto.itemTreatment
    }

    private static getEphytoLogFieldConfig() {
        getAppFieldsConfig().ephyto.ephytoLog
    }

    private static getAttachmentFieldConfig() {
        getAppFieldsConfig().ephyto.attachment
    }

    private static getItemGoodFieldConfig() {
        getAppFieldsConfig().ephyto.itemGood
    }

    private static getAppFieldsConfig() {
        Holders.config.com.webbfontaine.ephyto.fieldsConfig
    }

    public static boolean isEphytoLogFieldMandatory(String fieldName, EphytoGen ephytoGen) {
        return MANDATORY.isConform(getFieldConfigForLog(fieldName, ephytoGen))
    }
}

