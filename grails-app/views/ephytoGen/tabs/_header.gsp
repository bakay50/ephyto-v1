<%@ page import="com.webbfontaine.ephyto.BusinessLogicService; com.webbfontaine.ephyto.BusinessLogicUtils; com.webbfontaine.ephyto.TypeCastUtils;com.webbfontaine.ephyto.WfTagLib;com.webbfontaine.ephyto.constants.EphytoConstants" %>

<bootstrap:div id="headerContainer">

    <bootstrap:formSection labelCode="ephytoGen.requestIdentification.title.label">
        <bootstrap:genericInput bean="${ephytoGenInstance}" field="requestNumber" id="requestNumber"
                                name="requestNumber"
                                labelCode="ephytoGen.requestNumber.label" maxlength="13" span="2"
                                disabled="true"/>

        <bootstrap:formGroup labelCode="ephytoGen.requestDate.label">
            <bootstrap:formInput span="2">
                <wf:genericInput bean="${ephytoGenInstance}" field="requestDate" id="requestDate"
                                 maxDate="'0'"
                                 name="requestDate"
                                 value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.requestDate)}"
                                 disabled="true"
                                 onkeydown="return allowKeyPress(event);" onPaste="return false;"
                                 dateformat="'dd/mm/yy'"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
    </bootstrap:formSection>
    <bootstrap:formSection>
        <bootstrap:genericInput bean="${ephytoGenInstance}" inputSpan="3" field="userReference" id="userReference"
                                name="userReference" onchange="updateForUTF8('userReference')"
                                labelCode="ephytoGen.userReference.label" maxlength="30" span="3"
                                disabled="${!ephytoGenInstance?.isFieldEditable('userReference')}"/>

        <bootstrap:genericInput bean="${ephytoGenInstance}" inputSpan="3" field="otReference" id="otReference"
                                name="otReference" onchange="updateForUTF8('otReference')"
                                labelCode="ephytoGen.otReference.label" maxlength="30" span="3"
                                disabled="${!ephytoGenInstance?.isFieldEditable('otReference')}"/>

        <bootstrap:genericInput bean="${ephytoGenInstance}" field="ptReference" id="ptReference"
                                name="ptReference" onchange="updateForUTF8('ptReference')"
                                labelCode="ephytoGen.ptReference.label" maxlength="30" span="2"
                                disabled="${!ephytoGenInstance?.isFieldEditable('ptReference')}"/>


        <bootstrap:formGroup labelCode="ephytoGen.productTypeCode.label">
            <bootstrap:formInput>
                <wf:genericInput name="productTypeCode" id="productTypeCode" bean="${ephytoGenInstance}"
                                 value="${ephytoGenInstance?.productTypeCode}"
                                 field="productTypeCode" onchange="updateRuleOfFields(this)"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('productTypeCode')}"/>
            </bootstrap:formInput>
            <bootstrap:div class="form-group-labels">
                <bootstrap:label id="productTypeName">${ephytoGenInstance?.productTypeName}</bootstrap:label>
            </bootstrap:div>
            <wf:hiddenField name="defaultQuantity" id="defaultQuantity" value="${ephytoGenInstance.defaultQuantity}"/>
            <wf:hiddenField name="defaultNetWeight" id="defaultNetWeight"
                            value="${ephytoGenInstance.defaultNetWeight}"/>
            <wf:hiddenField name="defaultGrossWeight" id="defaultGrossWeight"
                            value="${ephytoGenInstance.defaultGrossWeight}"/>
            <wf:hiddenField name="startedOperation" id="startedOperation" value="${ephytoGenInstance?.startedOperation}"/>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="ephytoGen.Headertreatment.label">
            <bootstrap:formInput span="2">
                <wf:select class="span2"
                           name="treatment"
                           id="treatment"
                           value="${ephytoGenInstance?.treatment}"
                           optionKey="key" onchange="updateTreamentType(this)"
                           optionValue="value"
                           disabled="${!ephytoGenInstance?.isFieldEditable('treatment')}"
                           from="${[
                                   [key: EphytoConstants.YES, value: message(code: "ephyto.treatment." + EphytoConstants.YES)],
                                   [key: EphytoConstants.NO, value: message(code: "ephyto.treatment." + EphytoConstants.NO)]
                           ]}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
    </bootstrap:formSection>

    <bootstrap:formSection labelCode="ephytoGen.dockingPermission.title.label">
        <bootstrap:formGroup labelCode="ephytoGen.dockNumber.label">
            <bootstrap:formInput span="2">
                <wf:genericInput name="dockNumber" id="dockNumber" bean="${ephytoGenInstance}"
                                 value="${ephytoGenInstance?.dockNumber}" maxlength="3"
                                 field="dockNumber" regexp="${"[0-9,.\\s]"}" regexpforrule="${"[0-9]"}"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('dockNumber')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup labelCode="ephytoGen.dockingPermissionRef.label">
            <bootstrap:formInput span="2">
                <wf:genericInput name="dockingPermissionRef" id="dockingPermissionRef" bean="${ephytoGenInstance}"
                                 value="${ephytoGenInstance?.dockingPermissionRef}" maxlength="30"
                                 field="dockingPermissionRef" onchange="updateForUTF8('dockingPermissionRef')"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('dockingPermissionRef')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="ephytoGen.dockingPermissionDate.label">
            <bootstrap:formInput span="2">
                <wf:datepicker id="dockingPermissionDate" class="updateWitd"
                               name="dockingPermissionDate" autocomplete="off"
                               value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.dockingPermissionDate)}"
                               readonly="${!ephytoGenInstance?.isFieldEditable('dockingPermissionDate') && BusinessLogicUtils.isApprovedByGovAgent(ephytoGenInstance)}"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

    </bootstrap:formSection>

    <bootstrap:div style="display: ${ephytoGenInstance?.isVisibleReference() ? 'none' : 'block'}">
        <bootstrap:formSection labelCode="ephytoGen.phytosanitaryCertificate.title.label">
            <bootstrap:formGroup labelCode="ephytoGen.phytosanitaryCertificateRef.label">
                <bootstrap:formInput span="2">
                    <wf:genericInput name="phytosanitaryCertificateRef" id="phytosanitaryCertificateRef"
                                     bean="${ephytoGenInstance}"
                                     value="${ephytoGenInstance?.phytosanitaryCertificateRef}" maxlength="30"
                                     field="phytosanitaryCertificateRef"
                                     onchange="updateForUTF8('phytosanitaryCertificateRef')"
                                     readonly="${!(ephytoGenInstance?.phytosanitaryCertificateRef == null || "".equals(ephytoGenInstance?.phytosanitaryCertificateRef))}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>

            <bootstrap:formGroup labelCode="ephytoGen.phytosanitaryCertificateDate.label">
                <bootstrap:formInput span="2">
                    <wf:datepicker id="phytosanitaryCertificateDate"
                                   name="phytosanitaryCertificateDate" autocomplete="off" class="updateWitd"
                                   value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.phytosanitaryCertificateDate)}"
                                   readonly="${!ephytoGenInstance?.isFieldEditable('phytosanitaryCertificateDate')}"
                                   onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>

            <bootstrap:formGroup labelCode="ephytoGen.filingDate.label">
                <bootstrap:formInput span="2">
                    <wf:datepicker id="filingDate" maxDate="0"
                                   name="filingDate" autocomplete="off" class="updateWitd"
                                   value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.filingDate)}"
                                   disabled="${!(ephytoGenInstance?.filingDate == null || "".equals(ephytoGenInstance?.filingDate))}"
                                   onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>

            <bootstrap:formGroup labelCode="ephytoGen.signatureDate.label">
                <bootstrap:formInput span="2">
                    <wf:datepicker id="signatureDate"
                                   name="signatureDate" autocomplete="off" class="updateWitd"
                                   value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.signatureDate)}"
                                   readonly="${!ephytoGenInstance?.isFieldEditable('signatureDate')}"
                                   onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>

        </bootstrap:formSection>
    </bootstrap:div>

    <bootstrap:formSection labelCode="ephytoGen.declaration.title.label">
        <bootstrap:formGroup labelCode="ephytoGen.customsClearanceOfficeCode.label">
            <bootstrap:formInput span="2">
                <wf:genericInput name="customsClearanceOfficeCode" id="customsClearanceOfficeCode"
                                 bean="${ephytoGenInstance}"
                                 value="${ephytoGenInstance?.customsClearanceOfficeCode}"
                                 field="customsClearanceOfficeCode"
                                 readonly="${!ephytoGenInstance?.isFieldEditable('customsClearanceOfficeCode') || defOfficeAccessProperty ? true : false}"/>
            </bootstrap:formInput>
            <bootstrap:div class="form-group-labels">
                <bootstrap:label
                        id="customsClearanceOfficeName">${ephytoGenInstance?.customsClearanceOfficeName}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>
        <bootstrap:formGroup offset="1" labelCode="ephytoGen.declarationNumber.label">
            <bootstrap:formInput span="1">
                <wf:genericInput bean="${ephytoGenInstance}" style="width:36px !important;" id="declarationSerial" name="declarationSerial"
                                 readonly="${!ephytoGenInstance?.isFieldEditable('declarationSerial')}"
                                 field="declarationSerial" maxlength="1"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:digitInput bean="${ephytoGenInstance}" style="width:51px !important;" regexp="${'[0-9 \\s]'}"
                               disabled="${!ephytoGenInstance?.isFieldEditable('declarationNumber')}" regexpForRule="${"[0-9]"}"
                               field="declarationNumber" maxlength="10" id="declarationNumber" name="declarationNumber"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup labelCode="ephytoGen.declarationDate.label">
            <bootstrap:formInput span="2">
                <wf:datepicker id="declarationDate" class="updateWitd"
                               name="declarationDate" autocomplete="off"
                               value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.declarationDate)}"
                               readonly="${!ephytoGenInstance?.isFieldEditable('declarationDate')}"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
    </bootstrap:formSection>

    <bootstrap:formSection>
        <bootstrap:formGroup labelCode="ephytoGen.modeOfTransportCode.label">
            <bootstrap:formInput span="2">
                <wf:genericInput name="modeOfTransportCode" id="modeOfTransportCode" field="modeOfTransportCode"
                                 value="${ephytoGenInstance?.modeOfTransportCode}" maxlength="3"
                                 bean="${ephytoGenInstance}" onchange="setBotanicalFielSize()"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('modeOfTransportCode')}"/>
            </bootstrap:formInput>
            <bootstrap:div class="form-group-labels">
                <bootstrap:label id="modeOfTransportName">${ephytoGenInstance?.modeOfTransportName}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="ephytoGen.meansOfTransport.label">
            <bootstrap:formInput span="2">
                <wf:genericInput name="meansOfTransport" id="meansOfTransport" field="meansOfTransport"
                                 value="${ephytoGenInstance?.meansOfTransport}" maxlength="35"
                                 bean="${ephytoGenInstance}" onchange="updateForUTF8('meansOfTransport')"
                                 readonly="${!ephytoGenInstance?.isEditableForApprove('meansOfTransport')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup labelCode="ephytoGen.boardingDate.label">
            <bootstrap:formInput span="2">
                <wf:datepicker id="boardingDate" class="updateWitd"
                               name="boardingDate" autocomplete="off"
                               value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.boardingDate)}"
                               readonly="${!ephytoGenInstance?.isFieldEditable('boardingDate')}"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="ephytoGen.countryOfDestinationCode.label">
            <bootstrap:formInput span="2">
                <wf:genericInput name="countryOfDestinationCode" id="countryOfDestinationCode"
                                 field="countryOfDestinationCode"
                                 value="${ephytoGenInstance?.countryOfDestinationCode}" maxlength="2"
                                 bean="${ephytoGenInstance}"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('countryOfDestinationCode')}"/>
            </bootstrap:formInput>
            <bootstrap:div class="form-group-labels">
                <bootstrap:label
                        id="countryOfDestinationName">${ephytoGenInstance?.countryOfDestinationName}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="ephytoGen.placeOfUnloadingCode.label">
            <bootstrap:formInput span="2">
                <wf:genericInput name="placeOfUnloadingCode" id="placeOfUnloadingCode" field="placeOfUnloadingCode"
                                 value="${ephytoGenInstance?.placeOfUnloadingCode}" maxlength="5"
                                 bean="${ephytoGenInstance}"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('placeOfUnloadingCode')}"/>
            </bootstrap:formInput>
            <bootstrap:div class="form-group-labels">
                <bootstrap:label id="placeOfUnloadingName">${ephytoGenInstance?.placeOfUnloadingName}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>
    </bootstrap:formSection>
    <bootstrap:div style="display: ${ephytoGenInstance?.isHidden('proposedOperationDate') ? 'none' : 'block'}">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.proposedOperationDate.label">
                <bootstrap:formInput span="2">
                    <wf:datepicker id="proposedOperationDate" class="updateWitd"
                                   name="proposedOperationDate" autocomplete="off"
                                   value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.proposedOperationDate)}"
                                   readonly="${!ephytoGenInstance?.isFieldEditable('proposedOperationDate')}"
                                   onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
    </bootstrap:div>
    <bootstrap:div style="display: ${ephytoGenInstance?.isHidden('proposedOperationTime') ? 'none' : 'block'}">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.proposedOperationTime.label">
                <bootstrap:formInput span="2">
                    <wf:genericInput name="proposedOperationTime" id="proposedOperationTime"
                                     field="proposedOperationTime"
                                     value="${ephytoGenInstance?.proposedOperationTime}" maxlength="41"
                                     bean="${ephytoGenInstance}"
                                     readonly="${!ephytoGenInstance?.isFieldEditable('proposedOperationTime')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
    </bootstrap:div>
    <bootstrap:div style="display: ${ephytoGenInstance?.isHidden('operDate') ? 'none' : 'block'}">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.operDate.label">
                <bootstrap:formInput span="2">
                    <wf:datepicker id="operDate"
                                   name="operDate" autocomplete="off" class="updateWitd"
                                   value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(ephytoGenInstance?.operDate)}"
                                   readonly="${!ephytoGenInstance?.isFieldEditable('operDate')}"
                                   onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
    </bootstrap:div>
    <bootstrap:div style="display: ${ephytoGenInstance?.isHidden('operTime') ? 'none' : 'block'}">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.operTime.label">
                <bootstrap:formInput span="2">
                    <wf:genericInput name="operTime" id="operTime" field="operTime"
                                     value="${ephytoGenInstance?.operTime}"
                                     bean="${ephytoGenInstance}"
                                     readonly="${!ephytoGenInstance?.isFieldEditable('operTime')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
    </bootstrap:div>
    <bootstrap:div style="display: ${ephytoGenInstance?.isHidden('nameAndSurnameMinistryAgent') ? 'none' : 'block'}">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.nameAndSurnameMinistryAgent.label">
                <bootstrap:formInput span="2">
                    <wf:genericInput name="nameAndSurnameMinistryAgent" id="nameAndSurnameMinistryAgent"
                                     field="nameAndSurnameMinistryAgent"
                                     value="${ephytoGenInstance?.nameAndSurnameMinistryAgent}" maxlength="25"
                                     bean="${ephytoGenInstance}"
                                     readonly="${!ephytoGenInstance?.isFieldEditable('nameAndSurnameMinistryAgent')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
    </bootstrap:div>
    <bootstrap:div style="display: ${ephytoGenInstance?.isHidden('titleMinistryAgent') ? 'none' : 'block'}">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.titleMinistryAgent.label">
                <bootstrap:formInput span="2">
                    <wf:genericInput name="titleMinistryAgent" id="titleMinistryAgent" field="titleMinistryAgent"
                                     value="${ephytoGenInstance?.titleMinistryAgent}" maxlength="15"
                                     bean="${ephytoGenInstance}"
                                     readonly="${!ephytoGenInstance?.isFieldEditable('titleMinistryAgent')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
    </bootstrap:div>
    <g:if test="${ephytoGenInstance?.status}">
        <bootstrap:formGroup labelCode="ephytoGen.status.label">
            <bootstrap:formInput span="2">
                <wf:genericInput field="status" disabled="true"
                                 id="status" name="status"
                                 value="${message(code: "ephytoGen.status.${ephytoGenInstance?.status}", default: "${ephytoGenInstance?.status}")}"
                                 labelCode="ephytoGen.status.label" maxlength="15" span="2"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
    </g:if>
    <bootstrap:div style="display: ${ephytoGenInstance?.isDocRefSet() ? 'block' : 'none'}">
        <bootstrap:div style="display: ${(ephytoGenInstance?.nextDocRef!=null) ? 'block':'none' }">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.nextDocRef.label">
                <bootstrap:formInput span="2">
                    <wf:genericInput name="nextDocRef" id="nextDocRef" field="nextDocRef"
                                     value="${ephytoGenInstance?.nextDocRef}" maxlength="13"
                                     bean="${ephytoGenInstance}"
                                     readonly="${!ephytoGenInstance?.isFieldEditable('nextDocRef')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
        </bootstrap:div>
        <bootstrap:div style="display: ${(ephytoGenInstance?.previousDocRef != null) ? 'block' : 'none'}">
        <bootstrap:formSection>
            <bootstrap:formGroup labelCode="ephytoGen.previousDocRef.label">
                <bootstrap:formInput span="2">
                    <wf:genericInput name="previousDocRef" id="previousDocRef" field="previousDocRef"
                                     value="${ephytoGenInstance?.previousDocRef}" maxlength="13"
                                     bean="${ephytoGenInstance}"
                                     readonly="${!ephytoGenInstance?.isFieldEditable('previousDocRef')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:formSection>
        </bootstrap:div>
    </bootstrap:div>

    <bootstrap:formSection>
        <bootstrap:genericInput id="observations" name="observations" labelCode="ephytoGen.observations.label"
                                maxlength="4000"  row="12" type="area"
                                cols="50" field="observations" class="span4"
                                bean="${ephytoGenInstance}"  inputSpan="3"
                                onchange="updateObservationsForUTF8(this,'observations')"
                                value="${ephytoGenInstance?.observations}" style="height:50px !important;"
                                readonly="${!ephytoGenInstance?.isEditableForApprove('observations')}"/>
    </bootstrap:formSection>

</bootstrap:div>
