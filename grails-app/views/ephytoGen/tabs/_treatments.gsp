<%@ page import="com.webbfontaine.grails.plugins.taglibs.FormattingUtils; grails.util.Holders" %>
<bootstrap:div style="clear:both; margin-left:10px;" id="treatmentListContainer">
    <h3><g:message code="ephytoGen.itemList.label"/></h3>
    <g:set value="${ephytoGenInstance?.itemTreatments?.size() == null ? 0 : ephytoGenInstance?.itemTreatments?.size()}"
           var="nbre_treatment"></g:set>
    <div id="treatmentErrors"></div>
    <table class="table table-bordered table-striped tableNoWrap" id="itemsTable">
        <thead>
        <tr>
            <th style="width:30px;border-left:1px solid transparent !important;border-top:1px solid transparent !important;border-bottom:1px solid transparent !important;border-right:0px solid !important;border-bottom-right : 1px !important;" class="nav-pills2 actions-th2">
                <g:if test="${ephytoGenInstance?.isDocumentEditable}">
                    <g:if test="${grailsApplication.config.treatment.maxTreatmentConfig > nbre_treatment && grailsApplication.config.treatment.enabled == true}">
                        <bootstrap:linkButton id="addItemLink" class="btn btn-mini addButton treatmentListBtn"
                                              title="${message(code: 'ephyto.add.item.label')}" noLink="#"
                                              onclick="EphytoTreatmentController.addTreatment()" icon="plus"/>
                    </g:if>
                </g:if>
            </th>
            <th style="width:20px;"><g:message code="ephytoGen.item.num.label"/></th>
            <th style="width:30px"><g:message code="ephytoGen.applicatorCodes.label"/></th>
            <th style="width:50px"><g:message code="ephytoGen.disinfectionCertificateRefs.label"/></th>
            <th style="width:50px"><g:message code="ephytoGen.disinfectionCertificateDates.label"/></th>
            <th style="width:50px"><g:message code="ephytoGen.treatmentType.label"/></th>
            <th style="width:30px"><g:message code="ephytoGen.treatmentSartDate.label"/></th>
            <th style="width:30px"><g:message code="ephytoGen.treatmentEndDate.label"/></th>
            <th style="width:60px"><g:message code="ephytoGen.usedProducts.label"/></th>
            <th style="width:60px"><g:message code="ephytoGen.concentration.label"/></th>
            <th style="width:35px"><g:message code="ephytoGen.treatmentDuration.label"/></th>
        </tr>
        </thead>
        <tbody id="itemsTableBody">
        <tr>
            <th class="actions-th" style="margin-left:2px !important;width:10vh !important;border-left:1px solid transparent !important;border-top:1px solid transparent !important;border-right:0px solid !important;border-bottom-right : 1px !important;"></th>
            <td class="nav-pills2" style="height: 15px !important;">${nbre_treatment}</td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
            <td class="nav-pills2" style="height: 15px !important;"></td>
        </tr>

        <g:if test="${nbre_treatment > 0}">
            <tr style="height:1px !important;"></tr>
        </g:if>
        <g:render template="/ephytoGen/treatment/treatmentList"
                  model="['ephytoGenInstance': ephytoGenInstance, 'itemTreatments': ephytoGenInstance?.itemTreatments]"/>
        </tbody>
    </table>
</bootstrap:div>