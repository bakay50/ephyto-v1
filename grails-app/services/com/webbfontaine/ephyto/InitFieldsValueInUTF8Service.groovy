package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.command.SearchCommand
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.gen.EphytoGen
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.charset.StandardCharsets

import java.text.DecimalFormat
import java.text.ParsePosition
import com.webbfontaine.ephyto.gen.ItemGood
/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 16/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class InitFieldsValueInUTF8Service {
    static transactional = false
    private static final Logger LOGGER = LoggerFactory.getLogger(InitFieldsValueInUTF8Service.class)

    def initFieldsValueInUTF8(EphytoGen ephytoGenInstance, params) {
        if (ephytoGenInstance?.isDocumentEditable) {
            updateFieldsInstance(ephytoGenInstance, params, EphytoConstants.FIELDS_FOR_UTF8)
        } else if (!ephytoGenInstance?.isDocumentEditable && params.commitOperation == Operations.OI_APPROVE_REQUESTED) {
            updateFieldsInstance(ephytoGenInstance, params, EphytoConstants.APPROVE_FIELDS_FOR_UTF8)
        }
    }

    def updateFieldsInstance(EphytoGen ephytoGenInstance, params, FIELDS_FOR_UTF8) {
        ephytoGenInstance?.metaClass?.getProperties()?.each {
            def propName = it.name
            def paramsValue = params."${propName}"
            def paramsValueUTF = params."${propName}UTF"
            if (paramsValue && propName in FIELDS_FOR_UTF8) {
                def propType = it.type
                LOGGER.info("InitFieldsValueInUTF8Service -- updateFieldsInstance Method fields propType: ${propType}")
                if (propType == String) {
                    try {
                        LOGGER.info("InitFieldsValueInUTF8Service -- updateFieldsInstance Method fields : ${propName}  Initial value : ${paramsValue} UTF8 value : ${paramsValueUTF}  decoding value : ${URLDecoder.decode(paramsValueUTF, StandardCharsets.UTF_8.name())}")
                        ephytoGenInstance."${propName}" = URLDecoder.decode(paramsValueUTF, StandardCharsets.UTF_8.name())
                    } catch (IllegalArgumentException | UnsupportedEncodingException | URISyntaxException | Exception er) {
                        LOGGER.info("InitFieldsValueInUTF8Service -- updateFieldsInstance Method Exception fields : ${propName}  Initial value : ${paramsValue}")
                        ephytoGenInstance."${propName}" = paramsValueUTF
                    }
                }
            }
        }
    }

    def updateSearchFields(SearchCommand searchCommand ,params) {
        LOGGER.info("updateSearchFields -- initial params : ${params}")
        EphytoConstants.FIELDS_SEARCH_FOR_UTF8.each{it ->
            def paramsValue = params."${it}"
            LOGGER.info("updateSearchFields -- fields  : ${it}  Initial valeur : ${paramsValue}")
            def paramsValueUTF = params."${it}UTF"
            LOGGER.info("updateSearchFields -- fields  : ${it}  Initial UTF valeur : ${paramsValueUTF}")
            if(paramsValue && paramsValueUTF){
                try{
                    LOGGER.info("updateSearchFields -- fields  : ${it} to decode valeur  ${URLDecoder.decode(paramsValueUTF, StandardCharsets.UTF_8.name())}")
                    params."${it}" = URLDecoder.decode(paramsValueUTF, StandardCharsets.UTF_8.name())
                    searchCommand."${it}" = URLDecoder.decode(paramsValueUTF, StandardCharsets.UTF_8.name())
                }
                catch (IllegalArgumentException | UnsupportedEncodingException | URISyntaxException | Exception er) {
                    params."${it}" = paramsValue
                }
            }
        }
        LOGGER.info("updateSearchFields -- final return params :  ${params}")
        params
    }

    def initGoodFieldsValueInUTF8(EphytoGen ephytoGenInstance,item, params) {
        if (ephytoGenInstance?.isDocumentEditable) {
            updateGoodFieldsInstance(item, params, EphytoConstants.GOOD_FIELDS_FOR_UTF8)
        }
    }

    def convertToBigDecimal(String decimalValue,local){
        LOGGER.debug("In ConvertToBigDecimal")
        def decimal_format = "#,##0.00"
        def convertValue
        DecimalFormat df
        DecimalFormat decimalFormat = new DecimalFormat(decimal_format);
        if(local == 'en' || local == 'EN'){
            df = (DecimalFormat) decimalFormat.getInstance(Locale.ENGLISH)
        }else {
            df = (DecimalFormat) decimalFormat.getInstance(Locale.FRANCE)
        }
        df.setParseBigDecimal(true);
        if(decimalValue) {
            convertValue = (BigDecimal) df.parse(decimalValue, new ParsePosition(0));
        }
        convertValue
    }


    def updateGoodFieldsInstance(ItemGood itemGoodInstance, params, FIELDS_FOR_UTF8) {
        LOGGER.info("In updateGoodFieldsInstance")
        String locale = params?.locale
        itemGoodInstance?.metaClass?.getProperties()?.each {
            def propName = it.name
            def paramsValue = params."${propName}"
            def paramsValueUTF = params."${propName}UTF"
            LOGGER.info("InitFieldsValueInUTF8Service field: ${propName}  Initial value UTF8: ${paramsValueUTF}")
            if (paramsValue && propName in FIELDS_FOR_UTF8) {
                def propType = it.type
                LOGGER.info("updateGoodFieldsInstance Method fields propType: ${propType}")
                if (propType == String) {
                    try {
                        LOGGER.info("InitFieldsValueInUTF8Service -- updateGoodFieldsInstance Method fields : ${propName}  Initial value : ${paramsValue} UTF8 value : ${paramsValueUTF}")
                        itemGoodInstance."${propName}" = URLDecoder.decode(paramsValueUTF, StandardCharsets.UTF_8.name())
                    } catch (IllegalArgumentException | UnsupportedEncodingException | URISyntaxException | Exception er) {
                        LOGGER.info("Error in updateGoodFieldsInstance for String")
                        itemGoodInstance."${propName}" = paramsValueUTF
                    }
                }else if (propType == BigDecimal){
                    try {
                        LOGGER.info(" updateGoodFieldsInstance -- fields : ${propName}  UTF8 value : ${paramsValueUTF}")
                        BigDecimal paramsDecimal = convertToBigDecimal(paramsValueUTF.toString(),locale)
                        itemGoodInstance."${propName}" = paramsDecimal
                    } catch (IllegalArgumentException | UnsupportedEncodingException | URISyntaxException | Exception er) {
                        LOGGER.info("Error in updateGoodFieldsInstance for BigDecimal")
                        itemGoodInstance."${propName}" = paramsValueUTF
                    }
                }
            }
        }
    }
}
