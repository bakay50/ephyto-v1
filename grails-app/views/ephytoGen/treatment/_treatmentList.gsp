<%@ page import="com.webbfontaine.grails.plugins.taglibs.FormattingUtils; grails.util.Holders" %>
<g:set var="treatmentList"
       value="${ephytoGenInstance?.itemTreatments ? ephytoGenInstance?.itemTreatments : itemTreatments}"/>
<g:each var="treatmentInstance" status="i" in="${treatmentList}">
    <tr id="item_${i}">
        <td class="nav-pills2 actions-th2">
            <g:if test="${ephytoGenInstance?.isDocumentEditable}">
                <a href="javascript:void(0)" title="${g.message(code: 'ephyto.edit.item.label', default: "Edit Item")}"
                   class="modifyItemBtn treatmentListBtn linkBtns" id="modifyTreatment_${treatmentInstance?.itemNumber}"
                   onclick='EphytoTreatmentController.editTreatment("${treatmentInstance?.itemNumber}", "${g.createLink(controller: 'ephytoTreatment', action: 'editTreatment')}")'>
                    <bootstrap:icon name='pencil'/>
                </a>
                <a href="javascript:void(0)"
                   title="${g.message(code: 'ephyto.delete.item.label', default: "Delete Item")}"
                   class="deleteItemBtn treatmentListBtn linkBtns" id="deleteTreatment_${treatmentInstance?.itemNumber}"
                   onclick='EphytoTreatmentController.deleteTreatment("${treatmentInstance?.itemNumber}", "${g.createLink(controller: 'ephytoTreatment', action: 'deleteTreatment')}")'>
                    <bootstrap:icon name='trash'/>
                </a>
            </g:if>
            <a href="javascript:void(0)" title="${g.message(code: 'ephyto.show.item.label', default: "Show Item")}"
               class="viewItemBtn treatmentListBtn linkBtns" id="viewTreatment_${treatmentInstance?.itemNumber}"
               onclick='EphytoTreatmentController.showTreatment("${treatmentInstance?.itemNumber}", "${g.createLink(controller: 'ephytoTreatment', action: 'viewTreatment')}")'>
                <bootstrap:icon name='eye-open'/>
            </a>
        </td>
        <td class="nav-pills2" id="itemNumber_${i}"
            title="${treatmentInstance?.itemNumber}">${treatmentInstance?.itemNumber}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.applicatorNameAddress}">${treatmentInstance?.applicatorNameAddress}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.disinfectionCertificateRef}">${treatmentInstance?.disinfectionCertificateRef}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.disinfectionCertificateDate}">${treatmentInstance?.disinfectionCertificateDate}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.treatmentType}">${treatmentInstance?.treatmentType}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.treatmentSartDate}">${treatmentInstance?.treatmentSartDate}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.treatmentEndDate}">${treatmentInstance?.treatmentEndDate}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.usedProducts}">${treatmentInstance?.usedProducts}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.concentration}">${treatmentInstance?.concentration}</td>
        <td class="nav-pills2" id="Treatment${i}_code"
            title="${treatmentInstance?.treatmentDuration}">${treatmentInstance?.treatmentDuration}</td>
    </tr>
</g:each>