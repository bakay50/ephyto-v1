package com.webbfontaine.ephyto.xml.service

import com.webbfontaine.ephyto.TypeCastUtils

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 5/27/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class DocXmlBinderService {

    public static getValueInNationalCurrency(amountInFgn,currencyRate){
        BigDecimal result = BigDecimal.ZERO
        BigDecimal fgnValue = TypeCastUtils.toBigDecimal(amountInFgn)
        BigDecimal currencyValue = TypeCastUtils.toBigDecimal(currencyRate)
        if(fgnValue && currencyValue){
            result = fgnValue * currencyValue
        }
        result
    }
}
