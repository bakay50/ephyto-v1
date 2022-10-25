<%@ page import="com.webbfontaine.ephyto.TypeCastUtils" %>

<bootstrap:div id="attachmentsContent">
    <bootstrap:div id="chooseFileMessageTemplate" style="display: none"><div
            class="wf-alert-scrolable alert alert-blockatt alert-danger"><a class="close" data-dismiss="alert">×</a>

        <p><g:message code="attDoc.Choose_a_file.labelle"
                      default="Please chosse a file"/></p></div>
    </bootstrap:div>

    <bootstrap:div id="incorrectFileTypeMessageTemplate" style="display: none"><div
            class="wf-alert-scrolable alert alert-blockatt alert-danger"><a class="close" data-dismiss="alert">×</a>

        <p><g:message code="attDoc.incorrectFile_a_file.labelle"
                      default="Incorrect file type"/></p></div>
    </bootstrap:div>

    <bootstrap:div id="incorrectFileSizeMessageTemplate" style="display: none"><div
            class="wf-alert-scrolable alert alert-blockatt alert-danger"><a class="close" data-dismiss="alert">×</a>

        <p><g:message code="error.scanned.maxsize"
                      default="Maximum size of PDF document 2 MB"/></p></div>
    </bootstrap:div>

    <bootstrap:div id="alertInfoModal"></bootstrap:div>

    <bootstrap:grid grid='12(3-3-3)'>
        <bootstrap:formGroup labelCode="attacheddoc.docType.label">
            <bootstrap:formInput span="5">
                <wf:textInput id="docType" name="docType" value="${attdocInstance?.docType}" class="span5"
                              disabled="${!ephytoGenInstance?.isAttachmentEditable('docType')}"
                              onchange="validateFieldOnKeyPressModal(this)"
                              onkeydown="validateFieldOnKeyPressModal(this)"
                              maxlength="100"/>

            </bootstrap:formInput>
        </bootstrap:formGroup>

        <bootstrap:formGroup labelCode="attacheddoc.docRef.label">
            <bootstrap:formInput span="5">
                <wf:textInput id="docRef" name="docRef" value="${attdocInstance?.docRef}" class="span2" maxlength="35"
                              disabled="${!ephytoGenInstance?.isAttachmentEditable('docRef')}"
                              onchange="validateFieldOnKeyPressModal(this)" autocomplete="off"
                              onkeydown="validateFieldOnKeyPressModal(this)"/>

            </bootstrap:formInput>
        </bootstrap:formGroup>


        <bootstrap:formGroup labelCode="attacheddoc.docDate.label">
            <bootstrap:formInput span="3">
                <wf:datepicker name="docDate" id="docDate" maxDate="0" autocomplete="off"
                               disabled="${!ephytoGenInstance?.isAttachmentEditable('docDate')}"
                               value="${com.webbfontaine.ephyto.TypeCastUtils.fromDate(attdocInstance?.docDate)}"
                               class="span1" onchange="validateFieldOnKeyPressModal(this)"
                               onkeydown="validateFieldOnKeyPressModal(this)"
                               maxdate="0"/>

            </bootstrap:formInput>
        </bootstrap:formGroup>

        <span class="file-wrapper">
            <input type="file" style="word-wrap:break-word;"
                   title="${message(code: 'attDoc.Accepted_format_for_attachments.label', default: 'Accepted format for attachments :')}"
                   onchange="setFileAttName(this.value)"
                   name="attDocFile" id="attDocFile"
                   value="${attdocInstance?.docType}.${attdocInstance?.fileExtension}"
                   class="fileName" accept="application/pdf,.pdf"/>
            <span class="btn btn-success"><i class="glyphicon glyphicon-file icon-white"></i><g:message
                    code="attDoc.Choose_a_file.label"
                    default="Choose a file"/>
            </span>
        </span>

        <g:if test="${attdocInstance?.docType && attdocInstance?.fileExtension}">
            <g:set var="filenamevalue" value="${attdocInstance?.docType}.${attdocInstance?.fileExtension}"></g:set>
            <label style="display: block; width: 250px;margin-left:150px"
                   id="fileLabel">${filenamevalue == null ? "" : filenamevalue}</label>
        </g:if>
        <g:else>
            <label style="display: block; width: 250px;margin-left:150px"
                   id="fileLabel"></label>
        </g:else>

        <input type="hidden" id="fileExtension" value="${attdocInstance?.fileExtension}" name="fileExtension">
        <input type="hidden" style="display:none" name="oldValue" id="oldValue" value="">
        <input type="hidden" disabled="disabled" value="${attdocInstance?.attNumber}" id="flagdocsnumber"
               style="display:none"
               name="flagdocsnumber">
        <input type="hidden" id="docCode" value="${attdocInstance?.docCode}" name="docCode">
        <input type="hidden" id="attNumber" value="${attdocInstance?.attNumber}" name="attNumber">
        <input type="hidden" id="conversationId" value="${params?.conversationId ?: ''}"
               name="conversationId">
    </bootstrap:grid>
</bootstrap:div>