package com.webbfontaine.ephyto.sad

import com.webbfontaine.ephyto.BusinessLogicUtils
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.UserPropertyConstants
import com.webbfontaine.ephyto.sad.exceptions.DateExpiredException
import com.webbfontaine.ephyto.sad.exceptions.InvalidStatusException
import com.webbfontaine.ephyto.sad.exceptions.NoAccessPermissionException
import com.webbfontaine.ephyto.sad.exceptions.NotFoundException
import com.webbfontaine.ephyto.sad.http.SadHttpClient
import grails.gorm.transactions.Transactional
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.support.RequestContextUtils

import javax.servlet.http.HttpServletRequest
import java.text.SimpleDateFormat

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Y. SYLLA
 * Date: 10/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
@Transactional
class SadService {
    private final static Logger LOGGER = LoggerFactory.getLogger(SadService)

    def grailsApplication
    def messageSource
    def rimmLoadSadTvfService

    final String SAD_DATE_FORMAT = "yyyy-MM-dd"
    final String INVALID_STATUS = "invalid_status"
    final String INVALID_DATE = "invalid_date"
    final String sadXmlName = "declaration"
    final String NO_PERMISSION_EN = "You don't have permission to access the requested declaration"
    final String NO_PERMISSION_FR = "Vous n'avez pas les droits pour accéder à cette déclaration"
    def slurper = new JsonSlurper()


    def checkIfSadExists(String clearanceOfficeCode, String declarationSerial, String declarationNumber, String declarationDate, String companyCode, HttpServletRequest request) {
        LOGGER.debug("SAD clearanceOfficeCode: {},SAD declarationSerial: {}, SAD declarationNumber: {},SAD Date: {}", clearanceOfficeCode, declarationSerial, declarationNumber, declarationDate)
        String result = EphytoConstants.CORRECT_RESULT
        Locale locale = RequestContextUtils.getLocale(request)
        try {
            Map xmlResultMap = evaluateSadXml(clearanceOfficeCode, declarationSerial, declarationNumber, declarationDate, request, locale)
            LOGGER.debug("*** xmlResultMap : ${xmlResultMap.toString()}")
            if (xmlResultMap.success) {

                def parser = new JsonSlurper().setType(JsonParserType.LAX)
                def xml
                if (xmlResultMap.xmlString) {
                    xml = parser.parseText(xmlResultMap.xmlString?.toString())
                    result = checkSadStatus(xml, locale)
                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadTypeEX(xml, locale)
                    }
                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadTinUserProperty(xml, locale)
                    }
                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadDecUserProperty(xml, locale)
                    }

                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadExporter(xml, companyCode, locale)
                    }
                } else if (xmlResultMap.data) {
                    //xml = parser.parseText(xmlResultMap.data?.toString())
                    result = checkSadStatus1(xmlResultMap.data, locale)
                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadTypeEX1(xmlResultMap.data, locale)
                    }
                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadTinUserProperty1(xmlResultMap.data, locale)
                    }
                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadDecUserProperty1(xmlResultMap.data, locale)
                    }

                    if (result.equalsIgnoreCase(EphytoConstants.CORRECT_RESULT)) {
                        result = checkSadExporter1(xmlResultMap.data, companyCode, locale)
                    }

                }


            } else {
                result = xmlResultMap.error
            }
        } catch (e) {
            throw new RuntimeException(e)
        }
        result
    }

    private String evaluateExpirationDate(GPathResult xml, Locale locale) {
        String result = EphytoConstants.CORRECT_RESULT
        LocalDate expDate = getLocalDateFromDateString(xml?.expirationDate?.toString())
        if (expDate) {
            if (LocalDate.now().isAfter(expDate)) {
                result = messageSource.getMessage("ephytoGen.sad.expirationDate.expired", null, locale)
            }
        }
        return result
    }

    private String evaluateTin(GPathResult xml, String companyCode, Locale locale) {
        String result = EphytoConstants.CORRECT_RESULT
        String tin = xml?.impTin ? xml.impTin.toString() : (xml?.expTin ? xml.expTin.toString() : null)
        if (tin && companyCode) {
            if (!tin.equalsIgnoreCase(companyCode.trim())) {
                result = messageSource.getMessage("ephytoGen.sad.companyCode.tin.notEqual", null, locale)
            }
        }
        return result
    }


    private String checkSadExporter1(def data, String companyCode, Locale locale) {
        LOGGER.debug("** checkSadExporter **")
        String result = EphytoConstants.CORRECT_RESULT
        String tin = data?.exporter_code ? data?.exporter_code.toString() : null
        if (tin && companyCode) {
            if (!tin.equalsIgnoreCase(companyCode.trim())) {
                result = messageSource.getMessage("ephytoGen.sad.companyCode.tin.notEqual", null, locale)
            }
        }

        return result
    }

    private String checkSadExporter(def xml, String companyCode, Locale locale) {
        LOGGER.debug("** checkSadExporter **")
        String result = EphytoConstants.CORRECT_RESULT
        String tin = xml?.declaration?.exporterCode ? xml?.declaration?.exporterCode.toString() : null
        if (tin && companyCode) {
            if (!tin.equalsIgnoreCase(companyCode.trim())) {
                result = messageSource.getMessage("ephytoGen.sad.companyCode.tin.notEqual", null, locale)
            }
        }

        return result
    }


    private String checkSadTinUserProperty1(def data, Locale locale) {
        LOGGER.debug("** checkSadTinUserProperty ** data")
        String result = EphytoConstants.CORRECT_RESULT
        List userPropertyValues = BusinessLogicUtils.getUserPropertyValueByBeanAlias('TIN')
        String expCode = data?.exporter_code ? data?.exporter_code.toString() : null
        if (!(UserPropertyConstants.ALL in userPropertyValues || expCode in userPropertyValues)) {
            result = messageSource.getMessage("ephytoGen.sad.userProperty.tin.notIn", null, locale)
        }
        return result
    }

    private String checkSadTinUserProperty(def xml, Locale locale) {
        LOGGER.debug("** checkSadTinUserProperty **")
        String result = EphytoConstants.CORRECT_RESULT
        List userPropertyValues = BusinessLogicUtils.getUserPropertyValueByBeanAlias('TIN')
        String expCode = xml?.declaration?.exporterCode ? xml?.declaration?.exporterCode.toString() : null
        if (!(UserPropertyConstants.ALL in userPropertyValues || expCode in userPropertyValues)) {
            result = messageSource.getMessage("ephytoGen.sad.userProperty.tin.notIn", null, locale)
        }
        return result
    }


    private String checkSadDecUserProperty1(def data, Locale locale) {
        LOGGER.debug("** checkSadDecUserProperty ** data")
        String result = EphytoConstants.CORRECT_RESULT
        List userPropertyValues = BusinessLogicUtils.getUserPropertyValueByBeanAlias('DEC')
        String decCode = data?.declarant_code ? data?.declarant_code.toString() : null

        if (!(UserPropertyConstants.ALL in userPropertyValues || decCode in userPropertyValues)) {

            result = messageSource.getMessage("ephytoGen.sad.userProperty.dec.notIn", null, locale)
        }
        return result
    }


    private String checkSadDecUserProperty(def xml, Locale locale) {
        LOGGER.debug("** checkSadDecUserProperty **")
        String result = EphytoConstants.CORRECT_RESULT
        List userPropertyValues = BusinessLogicUtils.getUserPropertyValueByBeanAlias('DEC')
        String decCode = xml?.declaration?.declarantCode ? xml?.declaration?.declarantCode.toString() : null

        if (!(UserPropertyConstants.ALL in userPropertyValues || decCode in userPropertyValues)) {

            result = messageSource.getMessage("ephytoGen.sad.userProperty.dec.notIn", null, locale)
        }
        return result
    }


    private String checkSadTypeEX1(def data, Locale locale) {
        LOGGER.debug("** checkSadTypeEX  data **")
        String result = EphytoConstants.CORRECT_RESULT
        boolean isEX = data?.declaration_type.equalsIgnoreCase(EphytoConstants.EX) ? true : false
        if (!isEX) {
            result = messageSource.getMessage("ephytoGen.sad.typeOfDeclaration.export.notEqual", null, locale)
        }

        return result
    }

    private String checkSadTypeEX(def xml, Locale locale) {
        LOGGER.debug("** checkSadTypeEX **")
        String result = EphytoConstants.CORRECT_RESULT
        boolean isEX = xml?.declaration?.typeOfDeclaration.equalsIgnoreCase(EphytoConstants.EX) ? true : false
        if (!isEX) {
            result = messageSource.getMessage("ephytoGen.sad.typeOfDeclaration.export.notEqual", null, locale)
        }

        return result
    }

    private String checkSadStatus(def xml, Locale locale) {
        LOGGER.debug("** checkSadStatus  JSON : $xml")
        String result = EphytoConstants.CORRECT_RESULT
        if (xml?.declaration?.status) {
            if (!(xml?.declaration?.status.toString().toUpperCase() in EphytoConstants.SAD_VALID_STATUSES)) {
                result = messageSource.getMessage("ephytoGen.sad.invalid", null, locale)
            }
        }
        return result
    }

    private String checkSadStatus1(def data, Locale locale) {
        LOGGER.debug("** checkSadStatus  data JSON : $data")
        String result = EphytoConstants.CORRECT_RESULT
        if (data?.status) {
            if (!(data?.status.toString().toUpperCase() in EphytoConstants.SAD_VALID_STATUSES)) {
                result = messageSource.getMessage("ephytoGen.sad.invalid", null, locale)
            }
        }
        return result
    }


    private Map evaluateSadXml(String clearanceOfficeCode, String declarationSerial, String declarationNumber, String declarationDate, HttpServletRequest request, Locale locale) throws RuntimeException {
        Map resultMap = [success: false]
        String xmlString = null
        def data = null
        String url = grailsApplication.config.rest.sad?.url
        def useWebService = grailsApplication.config.rest?.useWebService
        LOGGER.debug(" useWebService = $useWebService")
        LOGGER.debug("evaluateSadXml SAD Url : $url")

        try {

            if (useWebService == "N") {
                data = rimmLoadSadTvfService.retrieveOnesad(clearanceOfficeCode, LocalDate.parse(declarationDate as String, DateTimeFormat.forPattern("dd/MM/yyyy")), declarationSerial, declarationNumber)
                if (!data) {
                    throw new NotFoundException(messageSource.getMessage("ephytoGen.sad.inexistent", null, locale))

                }

            } else if (useWebService == "Y") {
                xmlString = SadHttpClient.getSadXmlString(url, clearanceOfficeCode, declarationSerial, declarationNumber, declarationDate, request)
                if (!xmlString) {
                    throw new NotFoundException(messageSource.getMessage("ephytoGen.sad.inexistent", null, locale))
                }
            }


            /*   else if(xmlString.toString().trim().equalsIgnoreCase(NO_PERMISSION_EN) || xmlString.toString().trim().equalsIgnoreCase(NO_PERMISSION_FR)){
                   throw new NoAccessPermissionException(messageSource.getMessage("ephytoGen.sad.noAccessPermission",null,locale))
               } else if(xmlString.equalsIgnoreCase(INVALID_STATUS)){
                   throw new InvalidStatusException(messageSource.getMessage("ephytoGen.sad.invalid",null,locale))
               }else if(xmlString.equalsIgnoreCase(INVALID_DATE)){
                   throw new DateExpiredException(messageSource.getMessage("ephytoGen.sad.expirationDate.expired",null,locale))
               }else if(!xmlString.contains(sadXmlName)){
                   throw new NotFoundException(messageSource.getMessage("ephytoGen.sad.inexistent",null,locale))
               }*/
            resultMap.success = true
            resultMap.xmlString = xmlString
            resultMap.data = data
        } catch (NotFoundException | InvalidStatusException | DateExpiredException | NoAccessPermissionException ex) {
            resultMap.error = ex.message
        } catch (e) {
            throw new RuntimeException(e)
        }
        resultMap
    }

    private LocalDate getLocalDateFromDateString(String date) {
        def result = null
        if (date) {
            result = new LocalDate().fromDateFields(new SimpleDateFormat(SAD_DATE_FORMAT).parse(date.substring(0, date.indexOf("T"))))
        }
        result
    }
}
