<%@ page import="grails.util.Holders;com.webbfontaine.ephyto.TypeCastUtils;java.net.URLEncoder;java.net.URLDecoder;java.nio.charset.StandardCharsets" %>

<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.userReference == null ? "" : ephytoGenInstance?.userReference ,StandardCharsets.UTF_8.name())}" id="userReferenceUTF" name="userReferenceUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.otReference == null ? "" : ephytoGenInstance?.otReference,StandardCharsets.UTF_8.name())}" id="otReferenceUTF" name="otReferenceUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.ptReference == null ? "" : ephytoGenInstance?.ptReference,StandardCharsets.UTF_8.name())}" id="ptReferenceUTF" name="ptReferenceUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.dockingPermissionRef == null ? "" :  ephytoGenInstance?.dockingPermissionRef ,StandardCharsets.UTF_8.name())}" id="dockingPermissionRefUTF" name="dockingPermissionRefUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.meansOfTransport == null ? "" : ephytoGenInstance?.meansOfTransport ,StandardCharsets.UTF_8.name())}" id="meansOfTransportUTF" name="meansOfTransportUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.observations == null ? "" : ephytoGenInstance?.observations  ,StandardCharsets.UTF_8.name())}" id="observationsUTF" name="observationsUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.consigneeNameAddress == null ? "" : ephytoGenInstance?.consigneeNameAddress ,StandardCharsets.UTF_8.name())}" id="consigneeNameAddressUTF" name="consigneeNameAddressUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.realExportNameAddress == null ? "" : ephytoGenInstance?.realExportNameAddress ,StandardCharsets.UTF_8.name())}" id="realExportNameAddressUTF" name="realExportNameAddressUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.commercialDescriptionGoods == null ? "" :  ephytoGenInstance?.commercialDescriptionGoods ,StandardCharsets.UTF_8.name())}" id="commercialDescriptionGoodsUTF" name="commercialDescriptionGoodsUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.botanicalName == null ? "" :  ephytoGenInstance?.botanicalName ,StandardCharsets.UTF_8.name())}" id="botanicalNameUTF" name="botanicalNameUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.harvest == null ? "" : ephytoGenInstance?.harvest  ,StandardCharsets.UTF_8.name())}" id="harvestUTF" name="harvestUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.season == null ? "" : ephytoGenInstance?.season ,StandardCharsets.UTF_8.name())}" id="seasonUTF" name="seasonUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.packageMarks == null ? "" :  ephytoGenInstance?.packageMarks ,StandardCharsets.UTF_8.name())}" id="packageMarksUTF" name="packageMarksUTF" />
<wf:hiddenField value="${URLEncoder.encode(ephytoGenInstance?.phytosanitaryCertificateRef == null ? "" : ephytoGenInstance?.phytosanitaryCertificateRef ,StandardCharsets.UTF_8.name())}" id="phytosanitaryCertificateRefUTF" name="phytosanitaryCertificateRefUTF" />
<wf:hiddenField value="" id="messageUTF" name="messageUTF" />
<wf:hiddenField name="decimalNumberFormat"  value="${Holders.grailsApplication.config.quantityNumberFormat}"/>
