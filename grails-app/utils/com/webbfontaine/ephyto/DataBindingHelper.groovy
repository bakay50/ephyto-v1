package com.webbfontaine.ephyto

import com.google.common.collect.ImmutableList
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.gen.EphytoGen

/**
 * Copyrights 2002-2015 Webb Fontaine
 * Developer: Carina Garcia
 * Date: 3/17/15
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class DataBindingHelper {


    public static
    final ImmutableList GEN_ALL_ACCEPTABLE_FIELDS = ImmutableList.of('userReference', 'otReference', 'ptReference', 'dockNumber', 'dockingPermissionRef', 'dockingPermissionDate', 'customsClearanceOfficeCode', 'customsClearanceOfficeName',
            'declarationSerial', 'declarationDate', 'declarationNumber', 'modeOfTransportCode', 'meansOfTransport',
            'boardingDate', 'countryOfDestinationCode', 'countryOfDestinationName', 'placeOfUnloadingCode', 'exporterCode', 'exporterName', 'exporterAddress',
            'consigneeNameAddress', 'declarantCode', 'applicantName', 'applicantTelephone', 'applicantEmail', 'realExportNameAddress',
            'commodityCode', 'productTypeCode', 'commercialDescriptionGoods', 'botanicalName', 'harvest', 'season', 'countryOfOriginCode',
            'packageMarks', 'packageCode', 'volume', 'phytosanitaryCertificateDate', 'phytosanitaryCertificateRef', 'filingDate', 'observations', 'defaultQuantity', 'defaultNetWeight', 'defaultGrossWeight', 'treatment')

    public static ImmutableList GEN_TREATMENT_ACCEPTABLE_FIELDS = ImmutableList.of('warehouse', 'applicatorCode', 'disinfectionCertificateRef', 'disinfectionCertificateDate', 'treatmentType',
            'treatmentSartDate', 'treatmentSartTime', 'treatmentEndDate', 'treatmentEndTime', 'usedProducts', 'concentration', 'treatmentDuration')

    public static final EPHYTOGEN_ACCEPTABLE_FIELDS_FOR_EXPORT = ['requestNumber', 'requestDate', 'userReference', 'otReference', 'ptReference',
                                                                  'dockNumber', 'dockingPermissionRef', 'dockingPermissionDate', 'customsClearanceOfficeCode', 'customsClearanceOfficeName',
                                                                  'declarationSerial', 'declarationNumber', 'declarationDate', 'modeOfTransportCode', 'meansOfTransport', 'boardingDate',
                                                                  'countryOfDestinationCode', 'placeOfUnloadingCode', 'observations',
                                                                  'exporterCode', 'exporterName', 'exporterAddress', 'consigneeNameAddress', 'realExportNameAddress',
                                                                  'declarantCode', 'declarantName', 'applicantName', 'applicantTelephone', 'applicantEmail',
                                                                  'commodityCode', 'commodityDescription', 'productTypeCode', 'commercialDescriptionGoods', 'botanicalName', 'harvest', 'season', 'countryOfOriginCode', 'countryOfOriginName',
                                                                  'packageMarks', 'packageCode', 'packageName', 'totalQuantity', 'totalNetWeight', 'totalGrossWeight', 'volume', 'totalItems']

    public static final DECIMAL_FIELDS_TO_REFORMAT = []

    public static final EPHYTO_LIST_ACCEPTABLE_FIELDS_FOR_EXPORT = ['itemGoods', 'itemGood', 'attachments', 'attachment', 'itemTreatments', 'itemTreatment']

    public static final EPHYTO_ACCEPTABLE_FIELDS_FOR_IMPORT = EPHYTOGEN_ACCEPTABLE_FIELDS_FOR_EXPORT + EPHYTO_LIST_ACCEPTABLE_FIELDS_FOR_EXPORT

    public
    static ImmutableList GEN_GOODS_ACCEPTABLE_FIELDS = ImmutableList.of('batchNumber', 'subBatchNumber', 'quantity', 'netWeight', 'grossWeight')

    public static final ITEMGOOD_ACCEPTABLE_FIELDS_FOR_EXPORT = ['itemRank', 'batchNumber', 'subBatchNumber', 'quantity', 'netWeight', 'grossWeight']

    public static final ITEMTREATMENT_ACCEPTABLE_FIELDS_FOR_EXPORT = ['itemNumber', 'warehouse', 'applicatorCode', 'applicatorAgreement', 'applicatorNameAddress', 'disinfectionCertificateRef', 'disinfectionCertificateDate', 'treatmentType',
                                                                      'treatmentSartDate', 'treatmentSartTime', 'treatmentEndDate', 'treatmentEndTime', 'usedProducts', 'concentration', 'treatmentDuration']

    public static final ATTACHMENT_ACCEPTABLE_FIELDS_FOR_EXPORT = ['attNumber', 'docType', 'docRef', 'docDate', 'attDoc']

    public static GEN_LOG_ACCEPTABLE_FIELDS = ['message']

    public static final APPLICATOR_ACCEPTABLE_FIELDS = ['code', 'agreement', 'nameAddress', 'status']


    public static Collection<String> getEphytoGenAcceptableFields(EphytoGen ephytoGen) {
        return GEN_ALL_ACCEPTABLE_FIELDS.findAll { ephytoGen?.isFieldEditable(it as String) }
    }

    public static Collection<String> getItemGoodAcceptableFields(EphytoGen ephytoGen) {
        return GEN_GOODS_ACCEPTABLE_FIELDS.findAll { ephytoGen?.isItemGoodEditable(it as String) }
    }

    public static Collection<String> getEphytoTreatmentAcceptableFields(EphytoGen ephytoGen) {
        return GEN_TREATMENT_ACCEPTABLE_FIELDS.findAll { ephytoGen?.isTreatmentEditable(it as String) }
    }

    public static boolean isXmlAcceptableField(String name, String docType) {
        boolean isXmlAcceptableFields = getXmlAcceptableFields(docType).contains(name)
        return isXmlAcceptableFields
    }

    public static Collection<String> getXmlAcceptableFields(String docType) {
        EPHYTO_ACCEPTABLE_FIELDS_FOR_IMPORT
    }


    public static Collection<String> getEphytoLogAcceptableFields(EphytoGen ephytoGen) {
        return GEN_LOG_ACCEPTABLE_FIELDS.findAll { ephytoGen?.isLogEditable(it as String) }
    }

    public static Collection<String> getApplicatorAcceptableFields(Applicator applicator) {
        return APPLICATOR_ACCEPTABLE_FIELDS.findAll { applicator?.isFieldEditable(it as String) }
    }


}
