package com.webbfontaine.ephyto.gen.attachment

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.attachment.Attachment
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import org.apache.commons.codec.binary.Base64
import spock.lang.Specification

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 03/10/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class AttachFileMandatoryRuleSpec extends Specification {

    void "test when ephytoGen is null and have no AttachFile"() {
        when:
        EphytoGen ephytoGen = new EphytoGen()
        AttachFileMandatoryRule rule = new AttachFileMandatoryRule()
        AttachFileMandatoryRule.metaClass.isCommitOperationValid = { List validOperations ->
            true
        }
        RuleContext ruleContext = new RuleContext(ephytoGen, ephytoGen.errors)
        rule.apply(ruleContext)
        then:
        ephytoGen.attachments == null
        ruleContext.errorCount == 0
    }

    void "test when ephytoGen is not null and have no AttachFile"() {
        when:
        Map params = new HashMap()
        populateEphytoParams(params)
        EphytoGen ephytoGen = new EphytoGen(params)
        AttachFileMandatoryRule rule = new AttachFileMandatoryRule()
        AttachFileMandatoryRule.metaClass.isCommitOperationValid = { List validOperations ->
            true
        }
        RuleContext ruleContext = new RuleContext(ephytoGen, ephytoGen.errors)
        rule.apply(ruleContext)
        then:
        ephytoGen.attachments == null
        ruleContext.errorCount == 0
    }

    void "test when ephytoGen is no null and have AttachFile"() {
        when:
        Map params = new HashMap()
        populateEphytoParams(params)
        EphytoGen ephytoGen = new EphytoGen(params)
        Attachment attachment = new Attachment(
                id: 1,
                docCode: "code1",
                docType: "type 1"
        )
        attachment.attDoc = new AttachedFile(data: Base64.decodeBase64("azerty"))
        ephytoGen.attachments = new ArrayList<Attachment>()
        ephytoGen.attachments.add(attachment)
        AttachFileMandatoryRule rule = new AttachFileMandatoryRule()
        RuleContext ruleContext = new RuleContext(ephytoGen, ephytoGen.errors)

        AttachFileMandatoryRule.metaClass.isCommitOperationValid = { List validOperations ->
            true
        }
        rule.apply(ruleContext)
        then:
        ephytoGen.attachments != null
        ruleContext.errorCount == 0
    }


    public static populateEphytoParams(Map params) {
        params.exporterCode = "0002849F"
        params.userReference = "ref0120"
        params.consigneeNameAddress = "BOOLORE ABIDJAN 0120"
        params.commodityCode = "0000011111"
        params.otReference = "45120"
        params.ptReference = "poe120"
        params.dockingPermissionRef = "ref0012"
        params.dockingPermissionDate = Calendar.getInstance().set(2016, 4, 1)
        params.customsClearanceOfficeCode = "CIABA"
        params.declarationSerial = 2
        params.declarationNumber = 3
        params.declarationDate = Calendar.getInstance().set(2016, 4, 1)
        params.modeOfTransportCode = 2
        params.meansOfTransport = "SE"
        params.boardingDate = Calendar.getInstance().set(2016, 4, 1)
        params.countryOfDestinationCode = "SE"
        params.placeOfUnloadingCode = "ALGOK"
        params.exporterCode = "0001818J"
        params.consigneeNameAddress = "AB ABIDJAN 120"
        params.declarantCode = "00005L"
        params.applicantName = "AB ABIDJAN 120"
        params.applicantTelephone = Calendar.getInstance().set(2016, 4, 1)
        params.applicantEmail = "test@yahoo.fr"
        params.warehouse = "WARE001"
        params.applicatorCode = "AP11"
        params.disinfectionCertificateRef = "disin001"
        params.disinfectionCertificateDate = Calendar.getInstance().set(2016, 4, 1)
        params.treatmentType = "TY001"
        params.treatmentSartDate = Calendar.getInstance().set(2016, 4, 1)
        params.treatmentSartTime = "10:20:25"
        params.treatmentEndDate = Calendar.getInstance().set(2016, 4, 1)
        params.treatmentEndTime = "10:20:25"
        params.usedProducts = "usedProd001"
        params.concentration = "cent001"
        params.commodityCode = "3606100000"
        params.productTypeCode = "P010"
        params.season = "2016"
        params.countryOfOriginCode = "BA"
        params.packageMarks = "prod"
        params.packageCode = "pack"
    }


}
