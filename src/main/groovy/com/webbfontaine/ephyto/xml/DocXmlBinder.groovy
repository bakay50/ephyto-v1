package com.webbfontaine.ephyto.xml

import com.webbfontaine.ephyto.DataBindingHelper
import com.webbfontaine.ephyto.TypeCastUtils
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.EphytoTreatment
import com.webbfontaine.ephyto.gen.ItemGood
import com.webbfontaine.ephyto.gen.attachment.AttachedFile
import com.webbfontaine.ephyto.gen.attachment.Attachment
import grails.util.Holders
import groovy.util.slurpersupport.GPathResult
import groovy.xml.StreamingMarkupBuilder
import org.apache.commons.codec.binary.Base64
import org.joda.time.LocalDate

import static com.webbfontaine.ephyto.constants.EphytoConstants.PRODUCT_BOIS_MENTION
import static com.webbfontaine.ephyto.constants.EphytoConstants.PRODUCT_FRUIT_MENTION

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Carina Garcia
 * Date: 4/15/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class DocXmlBinder {

    public static Writable bindEphytoGenToXml(EphytoGen ephytoGenInstance) {
        def xml = new StreamingMarkupBuilder().bind { builder ->
            ephytoGen {
                buildGeneralSegmentForEphytoGen builder, ephytoGenInstance
                buildItems builder, ephytoGenInstance
                buildItemTreatments builder, ephytoGenInstance
                buildAttachments builder, ephytoGenInstance
            }
        }
        return xml
    }

    private static void buildGeneralSegmentForEphytoGen(builder, EphytoGen ephytoGenInstance) {
        DataBindingHelper.EPHYTOGEN_ACCEPTABLE_FIELDS_FOR_EXPORT.each { field ->
            builder."${field}"(formatValue(ephytoGenInstance, field))
        }
    }

    private static void buildItems(builder, EphytoGen ephytoGenInstance) {
        if (ephytoGenInstance?.itemGoods?.size() > 0) {
            builder.itemGoods {
                ephytoGenInstance?.itemGoods?.each { itemGood ->
                    builder.itemGood {
                        DataBindingHelper.ITEMGOOD_ACCEPTABLE_FIELDS_FOR_EXPORT.each { field ->
                            "${field}"(formatValue(itemGood, field))
                        }
                    }
                }
            }
        }
    }

    private static void buildItemTreatments(builder, EphytoGen ephytoGenInstance) {
        if (ephytoGenInstance?.itemTreatments?.size() > 0) {
            builder.itemTreatments {
                ephytoGenInstance?.itemTreatments?.each { itemTreatment ->
                    builder.itemTreatment {
                        DataBindingHelper.ITEMTREATMENT_ACCEPTABLE_FIELDS_FOR_EXPORT.each { field ->
                            "${field}"(formatValue(itemTreatment, field))
                        }
                    }
                }
            }
        }
    }

    private static void buildAttachments(builder, EphytoGen ephytoGenInstance) {
        if (ephytoGenInstance?.attachments?.size() > 0) {
            builder.attachments {
                ephytoGenInstance?.attachments?.each { attachment ->
                    builder.attachment {
                        DataBindingHelper.ATTACHMENT_ACCEPTABLE_FIELDS_FOR_EXPORT.each { field ->
                            "${field}"(formatValue(attachment, field))
                        }
                    }
                }
            }
        }
    }

    public static String formatValue(def targetObject, String field) {
        if (targetObject?."${field}" instanceof LocalDate) {
            TypeCastUtils.fromDate(targetObject?."${field}")
        } else if (field == "attDoc") {
            Base64.encodeBase64String(targetObject?."${field}"?.data)
        } else if (field in DataBindingHelper.DECIMAL_FIELDS_TO_REFORMAT) {
            def ret = targetObject?."${field}"
            if (ret instanceof BigDecimal) {
                ret = ret.setScale(0, BigDecimal.ROUND_HALF_UP);
            }
            ret
        } else {
            targetObject?."${field}"
        }
    }

    public static EphytoGen bindXmlToEphytoGen(GPathResult importXml) throws RuntimeException {
        EphytoGen ephytoGen = new EphytoGen()

        //TODO modfiy implementation
        if (importXml.size() != 0) {
            ephytoGen.dockingPermissionRef = getXmlString(ephytoGen, importXml, "dockingPermissionRef")
            ephytoGen.dockingPermissionDate = com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(importXml.dockingPermissionDate)
            ephytoGen.dockNumber = com.webbfontaine.ephyto.TypeCastUtils.toInteger(importXml.dockNumber)
            ephytoGen.customsClearanceOfficeCode = getXmlString(ephytoGen, importXml, "customsClearanceOfficeCode")
            ephytoGen.customsClearanceOfficeName = getXmlString(ephytoGen, importXml, "customsClearanceOfficeName")
            ephytoGen.declarationSerial = getXmlString(ephytoGen, importXml, "declarationSerial")
            ephytoGen.declarationNumber = com.webbfontaine.ephyto.TypeCastUtils.toInteger(importXml.declarationNumber)
            ephytoGen.declarationDate = com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(importXml.declarationDate)
            ephytoGen.modeOfTransportCode = getXmlString(ephytoGen, importXml, "modeOfTransportCode")
            ephytoGen.meansOfTransport = getXmlString(ephytoGen, importXml, "meansOfTransport")
            ephytoGen.boardingDate = com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(importXml.boardingDate)
            ephytoGen.countryOfDestinationCode = getXmlString(ephytoGen, importXml, "countryOfDestinationCode")
            ephytoGen.placeOfUnloadingCode = getXmlString(ephytoGen, importXml, "placeOfUnloadingCode")
            ephytoGen.otReference = getXmlString(ephytoGen, importXml, "otReference")
            ephytoGen.ptReference = getXmlString(ephytoGen, importXml, "ptReference")
            ephytoGen.productTypeCode = getXmlString(ephytoGen, importXml, "productTypeCode")
            if (ephytoGen?.productTypeCode?.toUpperCase()?.equals("FRUIT")) {
                ephytoGen.observations = PRODUCT_FRUIT_MENTION
            } else if (ephytoGen?.productTypeCode?.toUpperCase()?.equals("BOIS")) {
                ephytoGen.observations = PRODUCT_BOIS_MENTION
            } else {
                ephytoGen.observations = getXmlString(ephytoGen, importXml, "observations")
            }
            ephytoGen.userReference = getXmlString(ephytoGen, importXml, "userReference")
            // Names and Parties ----------------------------------------------
            ephytoGen.exporterCode = getXmlString(ephytoGen, importXml, "exporterCode")
            ephytoGen.exporterName = getXmlString(ephytoGen, importXml, "exporterName")
            ephytoGen.exporterAddress = getXmlString(ephytoGen, importXml, "exporterAddress")
            ephytoGen.consigneeNameAddress = getXmlString(ephytoGen, importXml, "consigneeNameAddress")
            ephytoGen.declarantCode = getXmlString(ephytoGen, importXml, "declarantCode")
            ephytoGen.declarantName = getXmlString(ephytoGen, importXml, "declarantName")
            ephytoGen.applicantName = getXmlString(ephytoGen, importXml, "applicantName")
            ephytoGen.applicantTelephone = getXmlString(ephytoGen, importXml, "applicantTelephone")
            ephytoGen.applicantEmail = getXmlString(ephytoGen, importXml, "applicantEmail")
            ephytoGen.realExportNameAddress = getXmlString(ephytoGen, importXml, "realExportNameAddress")
            // Goods ----------------------------------------------------
            ephytoGen.commodityCode = getXmlString(ephytoGen, importXml, "commodityCode")
            ephytoGen.commodityDescription = getXmlString(ephytoGen, importXml, "commodityDescription")
            ephytoGen.harvest = getXmlString(ephytoGen, importXml, "harvest")
            ephytoGen.season = getXmlString(ephytoGen, importXml, "season")
            ephytoGen.botanicalName = getXmlString(ephytoGen, importXml, "botanicalName")
            ephytoGen.commercialDescriptionGoods = getXmlString(ephytoGen, importXml, "commercialDescriptionGoods")
            ephytoGen.countryOfOriginCode = getXmlString(ephytoGen, importXml, "countryOfOriginCode")
            ephytoGen.countryOfOriginName = getXmlString(ephytoGen, importXml, "countryOfOriginName")
            ephytoGen.packageMarks = getXmlString(ephytoGen, importXml, "packageMarks")
            ephytoGen.packageCode = getXmlString(ephytoGen, importXml, "packageCode")
            ephytoGen.packageName = getXmlString(ephytoGen, importXml, "packageName")
            ephytoGen.totalQuantity = com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(importXml.totalQuantity)
            ephytoGen.totalNetWeight = com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(importXml.totalNetWeight)
            ephytoGen.totalGrossWeight = com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(importXml.totalGrossWeight)
            ephytoGen.volume = com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(importXml.volume)

            ephytoGen.totalItems = com.webbfontaine.ephyto.TypeCastUtils.toInteger(importXml.totalItems)


            // Treatment ----------------------------------------------
            int treatRank = 1
            def treatments = importXml.itemTreatments.itemTreatment.collect { item ->
                EphytoTreatment itemTreatment = new EphytoTreatment(
                        itemNumber: com.webbfontaine.ephyto.TypeCastUtils.toInteger(treatRank),
                        warehouse: getXmlString(EphytoTreatment, item, "warehouse"),
                        applicatorCode: getXmlString(EphytoTreatment, item, "applicatorCode"),
                        applicatorAgreement: getXmlString(EphytoTreatment, item, "applicatorAgreement"),
                        applicatorNameAddress: getXmlString(EphytoTreatment, item, "applicatorNameAddress"),
                        disinfectionCertificateRef: getXmlString(EphytoTreatment, item, "disinfectionCertificateRef"),
                        disinfectionCertificateDate: com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(item.disinfectionCertificateDate),
                        treatmentType: getXmlString(EphytoTreatment, item, "treatmentType"),
                        treatmentSartDate: com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(item.treatmentSartDate),
                        treatmentSartTime: getXmlString(EphytoTreatment, item, "treatmentSartTime"),
                        treatmentEndDate: com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(item.treatmentEndDate),
                        treatmentEndTime: getXmlString(EphytoTreatment, item, "treatmentEndTime"),
                        usedProducts: getXmlString(EphytoTreatment, item, "usedProducts"),
                        concentration: getXmlString(EphytoTreatment, item, "concentration"),
                        treatmentDuration: getXmlString(EphytoTreatment, item, "treatmentDuration"),
                )
                treatRank++
                itemTreatment
            }
            treatments.eachWithIndex { item, index ->
                if (index <= Holders.config.treatment.maxTreatmentConfig - 1) {
                    ephytoGen?.addToItemTreatments(item);
                }
            }

            int itemRank = 1
            def items = importXml.itemGoods.itemGood.collect { item ->
                ItemGood itemGood = new ItemGood(
                        itemRank: com.webbfontaine.ephyto.TypeCastUtils.toInteger(itemRank),
                        batchNumber: item.batchNumber as String,
                        subBatchNumber: item.subBatchNumber as String,
                        quantity: com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(item.quantity),
                        netWeight: com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(item.netWeight),
                        grossWeight: com.webbfontaine.ephyto.TypeCastUtils.toBigDecimal(item.grossWeight)
                )
                itemRank++;
                itemGood
            }
            items?.each { item ->
                ephytoGen.addToItemGoods(item);
            }

            int attRank = 1;
            def attDocs = importXml.attachments.attachment.collect { attachment ->
                AttachedFile file = new AttachedFile(
                        data: Base64.decodeBase64(attachment.attDoc.toString())
                )
                Attachment attDoc = new Attachment(
                        attNumber: attRank,
                        docType: attachment.docType as String,
                        docRef: attachment.docRef as String,
                        docDate: com.webbfontaine.ephyto.TypeCastUtils.toLocalDate(attachment.docDate),
                        fileExtension: attachment.fileExtension as String,
                        attDoc: file
                )
                attRank++;
                attDoc
            }

            attDocs?.each { attachedDoc ->
                ephytoGen.addToAttachments(attachedDoc);
            }
        }
        return ephytoGen
    }

    static def getMaxSize(domain, def field) {
        if (domain instanceof EphytoGen) {
            EphytoGen?.constrainedProperties?."${field}"?.maxSize

        }
    }

    static def getXmlString(domain, importXml, def field) {
        def fieldValue = importXml."${field}" as String
        def curMaxSize = getMaxSize(domain, field)
        if (fieldValue && curMaxSize) {
            fieldValue = (fieldValue.length() > curMaxSize) ? fieldValue.substring(0, curMaxSize) : fieldValue
        }
        fieldValue
    }

}


