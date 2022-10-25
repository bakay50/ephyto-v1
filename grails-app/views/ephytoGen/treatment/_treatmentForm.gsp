<%@ page import="com.webbfontaine.ephyto.TypeCastUtils; com.webbfontaine.ephyto.config.FieldsConfiguration;grails.util.Holders;com.webbfontaine.ephyto.TypeCastUtils;java.net.URLEncoder;java.net.URLDecoder;java.nio.charset.StandardCharsets" %>

<bootstrap:grid class="f_itemContainer" labelSpan="3" inputSpan="2" labelPrefix="item"
                id="treatmentFormCtr">
    <bootstrap:div class="f_itemContainer" id="treatmentFormCtr">
        <bootstrap:div class="mainTreatmentForm">
            <bootstrap:div id="treatmentErrors"></bootstrap:div>
            <wf:hiddenField value="${treatmentInstance?.itemNumber}" name="fieldRank" class="itemFld"/>
            <bootstrap:div class="control-group itemErrorPanel"
                           style="display: none; width: 100%; height: 15px; text-align: center">
                <label style="color: red;"><strong>Fill required fields.</strong></label>
            </bootstrap:div>

            <bootstrap:formSection>
                <bootstrap:genericInput bean="${treatmentInstance}" field="itemNumber" id="itemNumber" name="itemNumber"
                                        value="${treatmentInstance?.itemNumber}"
                                        disabled="${!ephytoGenInstance?.isTreatmentEditable("itemNumber")}"
                                        labelCode="ephyto.item.number.label"
                                        maxlength='50'/>

                <bootstrap:formGroup labelCode="ephytoGen.warehouse.label">
                    <bootstrap:formInput span="3">
                        <wf:textInput bean="${treatmentInstance}" field="warehouse" id="warehouse"
                                      name="warehouse" onchange="updateForUTF8('warehouse')"
                                      value="${treatmentInstance?.warehouse}" maxlength="35" class="span2"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('warehouse')}"/>
                    </bootstrap:formInput>
                </bootstrap:formGroup>

            </bootstrap:formSection>

            <bootstrap:formSection>
                <bootstrap:formGroup labelCode="ephytoGen.applicatorDetails.label">
                    <bootstrap:formInput span="3">
                        <wf:textInput id="applicatorCode" bean="${treatmentInstance}"
                                      name="applicatorCode" maxlength="10" field="applicatorCode"
                                      value="${treatmentInstance?.applicatorCode}"
                                      class="span2" labelCode="ephytoGen.applicatorCode.label"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('applicatorCode')}"/>
                    </bootstrap:formInput>
                </bootstrap:formGroup>
                <bootstrap:formGroup labelCode="ephytoGen.applicatorAgreement.label">
                    <bootstrap:div class="form-group-labels">
                        <bootstrap:label
                                id="applicatorAgreement">${treatmentInstance?.applicatorAgreement}</bootstrap:label>
                    </bootstrap:div>
                </bootstrap:formGroup>

                <bootstrap:formGroup labelCode="ephytoGen.applicatorNameAddress.label">
                    <bootstrap:div class="form-group-labels">
                        <bootstrap:label
                                id="applicatorNameAddress">${treatmentInstance?.applicatorNameAddress}</bootstrap:label>
                    </bootstrap:div>
                </bootstrap:formGroup>
            </bootstrap:formSection>

            <bootstrap:formSection labelCode="ephytoGen.desinfectionDetails.label">
                <bootstrap:formGroup labelCode="ephytoGen.disinfectionCertificateRef.label">
                    <bootstrap:formInput span="3">
                        <wf:textInput bean="${treatmentInstance}" field="disinfectionCertificateRef"
                                      id="disinfectionCertificateRef"
                                      name="disinfectionCertificateRef"
                                      span="3" onchange="updateForUTF8('disinfectionCertificateRef')"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('disinfectionCertificateRef')}"/>
                    </bootstrap:formInput>
                </bootstrap:formGroup>
                <bootstrap:formGroup labelCode="ephytoGen.disinfectionCertificateDate.label">
                    <bootstrap:formInput span="3">
                        <wf:datepicker id="disinfectionCertificateDate"
                                       autocomplete="off" class="updateWitdTreatment"
                                       name="disinfectionCertificateDate"
                                       value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(treatmentInstance?.disinfectionCertificateDate)}"
                                       disabled="${!ephytoGenInstance?.isTreatmentEditable('disinfectionCertificateDate')}"
                                       onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
                    </bootstrap:formInput>
                </bootstrap:formGroup>

            %{--<bootstrap:genericInput id="disinfectionCertificateDate" labelCode="ephytoGen.disinfectionCertificateDate.label"--}%
            %{--field="disinfectionCertificateDate" autocomplete="off" inputSpan="3"--}%
            %{--name="disinfectionCertificateDate" bean="${treatmentInstance}" type="datetime"--}%
            %{--value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(treatmentInstance?.disinfectionCertificateDate)}"--}%
            %{--readonly="${!ephytoGenInstance?.isTreatmentEditable('disinfectionCertificateDate')}"--}%
            %{--onkeydown="return allowKeyPress(event);" onPaste="return false;"/>--}%
            </bootstrap:formSection>


            <bootstrap:formSection labelCode="ephytoGen.treatment.label">

                <g:set var="initdefaultValueTypeTreatment"
                       value="${message(code: "${com.webbfontaine.ephyto.BusinessLogicUtils.initdefaultValueTypeTreatment()}", locale: "${org.springframework.web.servlet.support.RequestContextUtils.getLocale(request)}")}"></g:set>
                <g:set var="encodingTreatmentType"
                       value="${treatmentInstance?.treatmentType == null ? initdefaultValueTypeTreatment : treatmentInstance?.treatmentType}"></g:set>
                <wf:hiddenField
                        value="${URLEncoder.encode(encodingTreatmentType, StandardCharsets.UTF_8.name())}"
                        id="treatmentTypeUTF" name="treatmentTypeUTF"/>

                <bootstrap:formGroup labelCode="ephytoGen.treatmentType.label">
                    <bootstrap:formInput span="3">
                        <wf:textInput bean="${treatmentInstance}" field="treatmentType" name="treatmentType"
                                      id="treatmentType" maxlength="35"
                                      value="${encodingTreatmentType}"
                                      class="span2" onchange="updateForUTF8('treatmentType')"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('treatmentType')}"/>
                    </bootstrap:formInput>
                </bootstrap:formGroup>

                <bootstrap:formGroup offset="3" labelSpan="3"
                                     labelCode="ephytoGen.treatmentSartDate.label">
                    <bootstrap:formInput span="4">
                        <wf:datepicker id="treatmentSartDate" name="treatmentSartDate" class="updateWitdTreatment"
                                       value="${TypeCastUtils.fromDate(treatmentInstance?.treatmentSartDate)}"
                                       disabled="${!ephytoGenInstance?.isTreatmentEditable('treatmentSartDate')}"
                                       onkeydown="return allowKeyPress(event);" onPaste="return false;"
                                       autocomplete="off"
                                       onChange="applyProtectionRule()"/>

                    </bootstrap:formInput>
                    <bootstrap:label labelCode="ephytoGen.treatmentSartTime.label" span="2"/>
                    <bootstrap:formInput span="3">
                        <wf:textInput class="span1" id="treatmentSartTime" name="treatmentSartTime"
                                      value="${treatmentInstance?.treatmentSartTime}" field="treatmentSartTime"
                                      bean="${treatmentInstance}"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('treatmentSartTime')}"
                                      placeholder="00:00:00"/>
                    </bootstrap:formInput>

                </bootstrap:formGroup>


                <bootstrap:formGroup groupSpan="12" offset="3" labelSpan="3"
                                     labelCode="ephytoGen.treatmentEndDate.label">
                    <bootstrap:formInput span="4">
                        <wf:datepicker id="treatmentEndDate" name="treatmentEndDate" class="updateWitdTreatment"
                                       value="${TypeCastUtils.fromDate(treatmentInstance?.treatmentEndDate)}"
                                       disabled="${!ephytoGenInstance?.isTreatmentEditable('treatmentEndDate')}"
                                       onChange="applyProtectionRule()" autocomplete="off"
                                       onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
                    </bootstrap:formInput>
                    <bootstrap:label labelCode="ephytoGen.treatmentEndTime.label" span="2"/>
                    <bootstrap:formInput span="3">
                        <wf:textInput class="span1" id="treatmentEndTime" name="treatmentEndTime"
                                      value="${treatmentInstance?.treatmentEndTime}" bean="${treatmentInstance}"
                                      field="treatmentEndTime"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('treatmentEndTime')}"
                                      placeholder="00:00:00"/>
                    </bootstrap:formInput>

                </bootstrap:formGroup>

                <bootstrap:formGroup labelCode="ephytoGen.usedProducts.label">
                    <bootstrap:formInput span="3">
                        <wf:textInput bean="${treatmentInstance}" field="usedProducts" name="usedProducts"
                                      onchange="updateForUTF8('usedProducts')"
                                      value="${treatmentInstance?.usedProducts}" maxlength="35" class="span2"
                                      labelCode="ephytoGen.usedProducts.label"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('usedProducts')}"/>
                    </bootstrap:formInput>

                </bootstrap:formGroup>
                <bootstrap:formGroup labelCode="ephytoGen.concentration.label">
                    <bootstrap:formInput span="3">
                        <wf:textInput bean="${treatmentInstance}" field="concentration" name="concentration"
                                      onchange="updateForUTF8('concentration')"
                                      value="${treatmentInstance?.concentration}" maxlength="35" class="span2"
                                      labelCode="ephytoGen.concentration.label"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('concentration')}"/>
                    </bootstrap:formInput>

                </bootstrap:formGroup>

                <bootstrap:formGroup labelCode="ephytoGen.treatmentDuration.label">
                    <bootstrap:formInput>
                        <wf:textInput name="treatmentDuration" id="treatmentDuration" regexp="${'[0-9 \\s]'}"
                                      onChange="applyProtectionRule()" bean="${treatmentInstance}"
                                      field="treatmentDuration" regexpForRule="${"[0-9]"}"
                                      value="${treatmentInstance?.treatmentDuration}" maxlength="10" class="span2"
                                      disabled="${!ephytoGenInstance?.isTreatmentEditable('treatmentDuration')}"/>

                    </bootstrap:formInput>
                    <bootstrap:formInput span="7">
                        <bootstrap:div style="margin: 2px !important;">
                            <bootstrap:label id="addtreatmentDuration" labelCode="ephyto.hours.label"
                                             name="addtreatmentDuration"
                                             title="${message(code: 'ephyto.hours.label', default: 'H')}"></bootstrap:label>
                            <bootstrap:label class="add-on error-border" id="tempProtection"
                                             labelCode="ephyto.tempProtection.label"
                                             title="${message(code: 'ephyto.tempProtection.label', default: 'Temporary Protection')}"></bootstrap:label>
                        </bootstrap:div>
                    </bootstrap:formInput>
                </bootstrap:formGroup>

            </bootstrap:formSection>

            <bootstrap:formSection>

                <bootstrap:formGroup>
                    <bootstrap:div id="itemButtons" class="control-group-sub f_itemButtons no-background">
                        <bootstrap:div class="controls">
                            <g:if test="${showItem}">
                                <bootstrap:formInput span="2">
                                    <bootstrap:linkButton noLink="#" class="btn btn-small treatmentMainBtn"
                                                          id="closeTreatment" labelCode="ephyto.button.cancel.label"
                                                          href="javascript: void(0)"
                                                          onclick="EphytoTreatmentController.closeTreatment(${treatmentInstance?.itemNumber})"/>
                                </bootstrap:formInput>
                            </g:if>
                            <g:else>
                                <g:if test="${addTreatment}">
                                    <bootstrap:formInput span="2">
                                        <bootstrap:linkButton noLink="#"
                                                              class="btn btn-small btn-success treatmentMainBtn"
                                                              id="saveTreatment" labelCode="ephyto.button.add.label"
                                                              href="javascript: void(0)"
                                                              onclick="EphytoTreatmentController.saveTreatment(this, ${treatmentInstance?.itemNumber})"/>
                                    </bootstrap:formInput>
                                </g:if>
                                <g:elseif test="${editTreatment}">
                                    <bootstrap:formInput span="2">
                                        <bootstrap:linkButton noLink="#"
                                                              class="btn btn-small btn-success treatmentMainBtn"
                                                              id="updateTreatment"
                                                              labelCode="ephyto.button.modify.label"
                                                              href="javascript: void(0)"
                                                              onclick="EphytoTreatmentController.updateTreatment(this, ${treatmentInstance?.itemNumber})"/>
                                    </bootstrap:formInput>

                                </g:elseif>
                                <g:elseif test="${deleteTreatment}">
                                    <bootstrap:formInput span="2">
                                        <bootstrap:linkButton noLink="#"
                                                              class="btn btn-small btn-success treatmentMainBtn"
                                                              id="updateItem" labelCode="ephyto.button.delete.label"
                                                              href="javascript: void(0)"
                                                              onclick="EphytoTreatmentController.removeTreatment(this, ${treatmentInstance?.itemNumber})"/>
                                    </bootstrap:formInput>

                                </g:elseif>
                                <bootstrap:formInput span="2">
                                    <bootstrap:linkButton noLink="#" class="btn btn-small f_cancelItem treatmentMainBtn"
                                                          id="cancelTreatment" labelCode="ephyto.button.cancel.label"
                                                          href="javascript: void(0)"
                                                          onclick="EphytoTreatmentController.closeTreatment(${treatmentInstance?.itemNumber})"/>
                                </bootstrap:formInput>

                            </g:else>
                        </bootstrap:div>
                    </bootstrap:div>
                </bootstrap:formGroup>

            </bootstrap:formSection>

        </bootstrap:div>

    </bootstrap:div>
</bootstrap:grid>







