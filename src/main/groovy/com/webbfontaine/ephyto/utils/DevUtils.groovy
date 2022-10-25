package com.webbfontaine.ephyto.utils

import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.erimm.Product
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.EphytoTreatment
import grails.util.Holders
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Carina Garcia
 * Date: 4/6/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class DevUtils {

    static void initTestData() {
        DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy")
        if (Holders.config.dataSource.dbCreate in ['create-drop', 'create']) {
            for (data in 0..100) {
                def instance = new EphytoGen()
                instance.requestNumber = "0$data"
                instance.dockingPermissionRef = "Docking - ${data}"
                instance.phytosanitaryCertificateRef = "CERT${data}"
                instance.dockNumber =12
                instance.customsClearanceOfficeCode = "CIAB1"     
                instance.customsClearanceOfficeName  = "Abidjan Port"    
                instance.declarationSerial ="C"             
                instance.declarationNumber =123 
               
                //instance.flow = "IM"
                instance.countryOfDestinationCode = "FR"
                instance.countryOfDestinationName = "France"
                instance.countryOfOriginCode="CI"
                instance.countryOfOriginName="Côte d'Ivoire"
                instance.modeOfTransportCode="MT"
                instance.meansOfTransport ="e"
                instance.placeOfUnloadingCode = "ee"
                instance.nameAndSurnameMinistryAgent="ee"
                instance.titleMinistryAgent="ee"
                instance.exporterCode="ee"
                instance.consigneeNameAddress=""
                instance.applicantName="ee"
                instance.applicantTelephone="e"
                instance.applicantEmail="e@yahoo.fr"
                instance.productTypeCode="pp"
                instance.packageMarks="e"
                instance.packageCode ="e"
                instance.packageName  ="e"
                instance.commodityCode ="e"
                instance.commodityDescription="e"
                instance.proposedOperationTime="12:20:12"
                instance.operTime="12:20:12"
                instance.consigneeNameAddress="CI Abi"
                instance.userReference="user-${data}"
                instance.ownerUser = 'declarant'
                instance.ownerGroup = 'ownerGroup'
                instance.itemTreatments = new ArrayList<EphytoTreatment>()
                EphytoTreatment  ephytoTreatment = new EphytoTreatment()
                ephytoTreatment.itemNumber = data
                ephytoTreatment.warehouse = "warehouse--${data}"
                ephytoTreatment.applicatorCode= "00${data}"
                ephytoTreatment.applicatorAgreement= "NC"
                ephytoTreatment.applicatorNameAddress= "UNIVERSAL PHYTO PLUS${data}"
                ephytoTreatment.disinfectionCertificateRef= "REF-${data}"
                ephytoTreatment.disinfectionCertificateDate= LocalDate.parse("20/12/2016",format)
                ephytoTreatment.treatmentType= "long/sous bâche"
                ephytoTreatment.treatmentSartDate= LocalDate.parse("17/10/2016",format)
                ephytoTreatment.treatmentSartTime= "12:20:12"
                ephytoTreatment.treatmentEndDate= LocalDate.parse("17/12/2016",format)
                ephytoTreatment.treatmentEndTime= "12:20:12"
                ephytoTreatment.usedProducts= "used--${data}"
                ephytoTreatment.concentration= "concen--${data}"
                ephytoTreatment.treatmentDuration= 250
                ephytoTreatment.setEphytoGen(instance)
                instance.itemTreatments.add(ephytoTreatment)
                instance.status = 'Stored'
                instance.save(flush: true, failOnError: true)
            }
        }
    }

   static void initApplicatorTestData() {
        if (Holders.config.dataSource.dbCreate in ['create-drop', 'create']) {
            for (data in 0..100) {
                def instance = new Applicator()
                instance.id = data
                instance.agreement = "NC"
                instance.code ="AP${data}"
                instance.nameAddress="UNIVERSAL PHYTO PLUS${data}"
                instance.save(flush: true, failOnError: true)
            }
        }
    }

    static void initProducTestData() {
        if (Holders.config.dataSource.dbCreate in ['create-drop', 'create']) {
            for (data in 0..100) {
                def instance = new Product()
                instance.id = data
                instance.code = "P0${data}"
                instance.name ="CACAO EN FEVES DE CI GOOD FERMENTED REC 2015/2016 CAMP 2015/2016"
                instance.botanicalName =  "CACAO EN FEVES"+ data
                instance.defaultQuantity = data + 2
                instance.defaultNetWeight= data + 3
                instance.defaultGrossWeight= data + 4
                instance.save(flush: true, failOnError: true)
            }
        }
    }


}
