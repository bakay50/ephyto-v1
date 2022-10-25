<bootstrap:div id="nameAndPartiesContainer">
    <bootstrap:formSection labelCode="ephytoGen.exporterDetails.label">
        <bootstrap:genericInput id="exporterCode" name="exporterCode" maxlength="17" bean="${exporterCode}"
                                value="${ephytoGenInstance?.exporterCode}" labelCode="ephytoGen.exporterCode.label"
                                field="exporterCode"
                                disabled="${!ephytoGenInstance?.isFieldEditable('exporterCode') || defTinProperty ? true : false}"/>

        <bootstrap:formGroup labelCode="ephytoGen.exporterName.label">
            <bootstrap:div class="form-group-labels">
                <bootstrap:label id="exporterName">${ephytoGenInstance?.exporterName}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="ephytoGen.exporterAddress.label">
            <bootstrap:div class="form-group-labels">
                <bootstrap:label id="exporterAddress">${ephytoGenInstance?.exporterAddress}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>
    </bootstrap:formSection>
    <bootstrap:formSection labelCode="ephytoGen.realExporterDetails.label">
        <bootstrap:genericInput type="area" inputSpan="4" labelCode="ephytoGen.realExportNameAddress.label"
                                id="realExportNameAddress" name="realExportNameAddress" row="15" cols="150"
                                class="span4" onchange="updateForUTF8('realExportNameAddress')"
                                value="${ephytoGenInstance?.realExportNameAddress}"
                                disabled="${!ephytoGenInstance?.isFieldEditable('realExportNameAddress')}"/>
    </bootstrap:formSection>
    <bootstrap:formSection labelCode="ephytoGen.consigneeDetails.label">
        <bootstrap:genericInput type="area" inputSpan="4" labelCode="ephytoGen.nameAndAddress.label"
                                id="consigneeNameAddress" name="consigneeNameAddress" row="8" cols="100" class="span3"
                                onchange="updateForUTF8('consigneeNameAddress')"
                                value="${ephytoGenInstance?.consigneeNameAddress}"
                                disabled="${!ephytoGenInstance?.isEditableForApprove('consigneeNameAddress')}"/>
    </bootstrap:formSection>

    <bootstrap:formSection labelCode="ephyto.declarantDetails.label">
        <bootstrap:genericInput id="declarantCode" name="declarantCode" maxlength="17" bean="${declarantCode}"
                                value="${ephytoGenInstance?.declarantCode}" labelCode="ephytoGen.code.label"
                                field="declarantCode"
                                disabled="${!ephytoGenInstance?.isFieldEditable('declarantCode') || defDecProperty ? true : false}"/>

        <bootstrap:formGroup labelCode="ephytoGen.declarantName.label">
            <bootstrap:div class="form-group-labels">
                <bootstrap:label id="declarantName">${ephytoGenInstance?.declarantName}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="ephytoGen.declarantAddress.label">
            <bootstrap:div class="form-group-labels">
                <bootstrap:label id="declarantAddress">${ephytoGenInstance?.declarantAddress}</bootstrap:label>
            </bootstrap:div>
        </bootstrap:formGroup>
    </bootstrap:formSection>

    <bootstrap:formSection labelCode="ephyto.applicantDetails.label">
        <bootstrap:formGroup labelCode="ephytoGen.name.label">
            <bootstrap:formInput span="3">
                <wf:genericInput name="applicantName" field="applicantName" id="applicantName"
                                 value="${ephytoGenInstance?.applicantName}" maxlength="35"
                                 class="span2" onchange="updateForUTF8('applicantName')"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('applicantName')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup labelCode="ephytoGen.telephone.label">
            <bootstrap:formInput span="3">
                <wf:genericInput name="applicantTelephone" field="applicantTelephone" id="applicantTelephone"
                                 value="${ephytoGenInstance?.applicantTelephone}" maxlength="17"
                                 class="span2"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('applicantTelephone')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup labelCode="ephytoGen.applicantEmail.label">
            <bootstrap:formInput span="6">
                <wf:genericInput name="applicantEmail" field="applicantEmail" id="applicantEmail"
                                 value="${ephytoGenInstance?.applicantEmail}" maxlength="128"
                                 class="span4"
                                 disabled="${!ephytoGenInstance?.isFieldEditable('applicantEmail')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
    </bootstrap:formSection>

</bootstrap:div>

