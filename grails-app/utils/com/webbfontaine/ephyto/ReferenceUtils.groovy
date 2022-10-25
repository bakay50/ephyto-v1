package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.constants.ReferenceConstants
import com.webbfontaine.grails.plugins.rimm.cmp.Company
import com.webbfontaine.grails.plugins.rimm.cuo.CustomsOffice
import com.webbfontaine.grails.plugins.rimm.dec.Declarant
import com.webbfontaine.grails.plugins.rimm.hist.HistorizationSupport


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Y. SYLLA
 * Date: 4/18/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class ReferenceUtils {

    public static getCompanyFields(userPropValues) {

        return getFieldsPerUserProp(Company, userPropValues, ReferenceConstants.EXPORTER_SELECT_FIELDS)
    }
    public static getDeclarantFields(userPropValues) {

        return getFieldsPerUserProp(Declarant, userPropValues, ReferenceConstants.DECLARANT_SELECT_FIELDS)
    }
    public static getOfficeAccessFields(userPropValues) {

        return getFieldsPerUserProp(CustomsOffice, userPropValues, ReferenceConstants.DECLARANT_SELECT_FIELDS)
    }

    public static getFieldsPerUserProp(domain, def userPropValues, fieldsMap) {

        List newDataList = getFieldsFromRimmData(domain, userPropValues)
        convertToMaps(newDataList, fieldsMap)
    }

    static getFieldsFromRimmData(domain, def userPropValues) {
        List newDataList
        domain.withNewSession {
            newDataList = HistorizationSupport.withHistorizedFinder(getHtDate()) {
                domain.findAllByCodeInList(userPropValues)
            }
        }
        newDataList
    }


    static List convertToMaps(List source, String[] mapElements) {
        List retVal = new ArrayList(source.size())
        for (int i = 0; i < source.size(); i++) {
            Object sourceObject = source.get(i)

            Map dataMap = convertToMap(sourceObject, mapElements)
            retVal.add(dataMap);
        }
        return retVal
    }

    static Map convertToMap(sourceObject, String[] mapElements) {
        Map dataMap = new HashMap()
        for (int j = 0; j < mapElements.length; j++) {
            String mapElement = mapElements[j]

            def sourceObjectFieldValue = mapElement.contains(".") ?
                    sourceObject[mapElement.split("\\.")[0]][[mapElement.split("\\.")[1]]] : sourceObject[mapElement]
            dataMap.put(mapElement, sourceObjectFieldValue)
        }
        dataMap
    }
    private static getHtDate() {
        return new Date().clearTime()
    }
}
