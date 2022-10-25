package com.webbfontaine.ephyto.utils


import com.webbfontaine.ephyto.gen.EphytoGen


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Allan Apor Jr.
 * Date: 13/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class TestUtils {

    public static createEphytoGen(String TransportCode) {
        Map params = new HashMap()
        params.modeOfTransportCode = TransportCode
        params.meansOfTransport = "me"
        params.countryOfDestinationCode = "ca"
        params.exporterCode = "0002849F"
        params.consigneeNameAddress = "BOOLORE ABIDJAN 0120"
        params.commodityCode="0000011111"
        params.otReference="45120"
        params.ptReference="poe120"
        params.dockingPermissionRef="ref0012"
        params.dockingPermissionDate="01/04/2016"
        params.customsClearanceOfficeCode="CIABA"
        params.declarationSerial=2
        params.declarationNumber=3
        params.declarationDate="01/04/2016"
        params.modeOfTransportCode=2
        params.meansOfTransport="SE"
        params.boardingDate="01/04/2016"
        params.countryOfDestinationCode="SE"
        params.placeOfUnloadingCode="ALGOK"
        params.exporterCode="0001818J"
        params.consigneeNameAddress="AB ABIDJAN 120"
        params.declarantCode="00005L"
        params.applicantName="AB ABIDJAN 120"
        params.applicantTelephone="01/04/2016"
        params.applicantEmail="test@yahoo.fr"
        params.warehouse="WARE001"
        params.applicatorCode="AP11"
        params.disinfectionCertificateRef="disin001"
        params.disinfectionCertificateDate="01/04/2016"
        params.treatmentType="TY001"
        params.treatmentSartDate="01/04/2016"
        params.treatmentSartTime="10:20:25"
        params.treatmentEndDate="01/04/2016"
        params.treatmentEndTime="10:20:25"
        params.usedProducts="usedProd001"
        params.concentration="cent001"
        params.commodityCode="3606100000"
        params.productTypeCode="P010"
        params.season="2016"
        params.countryOfOriginCode="BA"
        params.packageMarks="prod"
        params.packageCode="pack"
        return new EphytoGen(params)
    }

}
