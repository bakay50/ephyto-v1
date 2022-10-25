package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.taglibs.PaginationService
import grails.util.Environment
import grails.util.Holders
import org.joda.time.LocalDate
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 25/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoGenUtils {
    static String COFFEE = 'CAFE';
    static String CACAO = 'CACAO';

    public static Map createModel(EphytoGen ephytoGen, PaginationService paginationService, boolean hasErrors = false) {
        def itemGoods = ephytoGen?.itemGoods
        def attachments = ephytoGen?.attachments
        def itemTreatments =  ephytoGen?.itemTreatments
        def model = [ephytoGenInstance: ephytoGen, itemGoods: itemGoods,
                     attachments: attachments,itemTreatments:itemTreatments, hasErrors: hasErrors]
        return model
    }

    def final static VALID_TYPES = [(String): { it }, (BigDecimal): { com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(it) }, (Boolean): {
        com.webbfontaine.ephyto.TypeCastUtils.toBoolean(it)
    }, (LocalDate): { com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(it) }, (Integer): { com.webbfontaine.ephyto.TypeCastUtils.toInteger(it) }]

    public static void setValue(def targetObject, String field, Object value) {
        def property = Holders.grailsApplication.getDomainClass(targetObject.class.name).properties
                .find { it.name == field }
                .find { VALID_TYPES[it.type] }

        if (property) {
            targetObject[property.name] = VALID_TYPES[property.type](value)
        }else{
            throw new RuntimeException("invalid xml node")
        }
    }

    public static InputStream resourceAsStream(String resource) {
        try {
            return Holders.getGrailsApplication().parentContext.getResource(resource).inputStream
        } catch (IOException e) {
            return null
        }
    }

    public static InputStream convertResourceAsStream(String resource) {
        try {
            return Environment.current == Environment.DEVELOPMENT ? new FileSystemResource(resource)?.getFile()?.newInputStream() : new ClassPathResource(resource)?.getFile()?.newInputStream()
        } catch (IOException e) {
            return null
        }
    }


}
