<g:if test="${addAttachedDocs || editAttachedDocs}">
    <g:if test="${!htAttIsLoaded || params.lang}">
        <wf:localRef refName="HT_ATT" selectFields="code,description" beanAlias="HT_ATT"
                     htDate="${new Date().clearTime()}"/>
    </g:if>
    <g:render template="/attachments/utils/hiddenLinks" model="['controller': controller]"/>
    <g:render template="/attachments/utils/alertInfo"/>
    <wf:hiddenField name="uploadAndAddMessage"
                    value="${message(code: 'attachedDocs.uploadDialog.upload.add', default: 'Upload + Add')}"
                    disabled="true"/>
    <wf:hiddenField name="uploadAndSaveMessage"
                    value="${message(code: 'attachedDocs.uploadDialog.upload.save', default: 'Upload + Save')}"
                    disabled="true"/>
</g:if>

<div id="attachedErrors"></div>

<div id="alertInfo"></div>


<div id="attachedDocSaveAlertInfoTemplate" style="display: none"><div
        class="wf-alert-scrolable alert alert-block alert-info"><a class="close"
                                                                   data-dismiss="alert">×</a>

    <p><g:message code="attDoc.The_attachment_has_been_recorded.label"
                  default="The attachment has been recorded"/></p></div>
</div>

<div id="attachedDocAddedRemoveInfoTemplate" style="display: none"><div
        class="wf-alert-scrolable alert alert-block alert-danger"><a class="close"
                                                                     data-dismiss="alert">×</a>

    <p><g:message code="attDoc.The_attachment_has_been_deleted.label"
                  default="The attachment has been deleted"/></p></div>
</div>

<div id="attachedDocAddedUpdateInfoTemplate" style="display: none"><div
        class="wf-alert-scrolable alert alert-block alert-info"><a class="close"
                                                                   data-dismiss="alert">×</a>

    <p><g:message code="attDoc.The_attachment_has_been_update.label"
                  default="The attachment has been deleted"/></p></div>
</div>

<table class="table table-bordered table-striped tableNoWrap attachmentTable">
    <thead>
    <tr id="allheaderTable">
        <g:if test="${instance?.addAttachedDocs || instance?.editAttachedDocs}">
            <th style="width: 60px;" class="actions-th2">&nbsp;</th>
        </g:if>
        <th style="width: 20px">#</th>
        <th style="width: 210px">${message(code: 'ephytoGen.attachedDocs.docType', default: 'Document type')}</th>
        <th style="width: 135px;">${message(code: 'ephytoGen.attachedDocs.docRef', default: 'Document Ref. No.')}</th>
        <th style="width: 90px">${message(code: 'ephytoGen.attachedDocs.docDate', default: 'Document Date')}</th>
        <th id="fileHeader" style="width: 100px">${message(code: 'attachedDocs.fileName', default: 'File Name')}</th>
    </tr>
    <g:if test="${addAttachedDocs || editAttachedDocs}">
        <tr>
            <td class="nav-pills2 actions-th2">
                <a id="addAttDoc" class="btn" title="${message(code: 'attachedDocs.add.button.label')}"><i
                        class="icon-plus"></i></a>
                <wf:hiddenField name="docCode"/>
            </td>
            <td class="nav-pills2" style="width: 30px;"></td>
            <td class="nav-pills2" style="width: 140px;">
                <wf:textInput class="span3" name="docType" value="${attachedDoc?.docType}"
                              disable="${!certificatInstance?.isAttachmentEditable("docType")}"/>
            </td>
            <td class="nav-pills2">
                <wf:textInput name="docRef" class="span2" value="${attachedDoc?.docRef}" id="docRef" maxlength="35"
                              disable="${!certificatInstance?.isAttachmentEditable("docRef")}"/>
            </td>
            <td class="nav-pills2">
                <wf:datepicker class="span1" dateformat="'dd/mm/yy'" onkeydown="return allowKeyPress(event);"
                               onPaste="return false;"
                               name="docDate" value="" id="docDate" maxDate="0" style="width: 80px !important;"
                               disable="${!certificatInstance?.isAttachmentEditable("docDate")}"/>
            </td>
            <td class="nav-pills2" style="width: 150px;">
                <a id="addUploadFile"
                   title="${message(code: 'attachedDocs.uploadFile.button', default: 'Upload file')}"
                   class="btn upload-button">
                    ${message(code: 'attachedDocs.uploadFile.button', default: 'Upload file')}
                </a>
            </td>
        </tr>
    </g:if>
    </thead>
    <tbody id="attachmentTableBody">
    <g:render template="/attachments/attachedDocsList"
              model="['instance': instance, 'controller': controller, addAttachedDocs: addAttachedDocs, editAttachedDocs: editAttachedDocs]"/>
    </tbody>
</table>

<g:if test="${addAttachedDocs || editAttachedDocs}">
    <table id="editTable" style="display: none">
        <tr id="editHeader">
            <td>
                <div class="actions" id="editTools">
                    <img src="${resource(plugin: 'wf-attached-documents', dir: 'images', file: 'ok.png')}" width="13"
                         height="16" class="okDoc"
                         id="saveAtt" title="${message(code: 'attachedDocs.confirmLink', default: 'Confirm')}"
                         style="cursor: pointer;" alt="Confirm"/>
                    <img src="${resource(plugin: 'wf-attached-documents', dir: 'images', file: 'delete.png')}"
                         width="14" height="16" class="cancelDoc"
                         id="cancelAtt" style="cursor: pointer;" onclick="cancelEdit()"
                         title="${message(code: 'attachedDocs.cancelLink', default: 'Cancel')}" alt="Cancel"/>
                    <wf:hiddenField name="hidDocRank" class="attFld"/>
                    <wf:hiddenField name="hidDocType"/>
                    <wf:hiddenField name="hidDocCode"/>
                    <wf:hiddenField name="hidDocRef"/>
                    <wf:hiddenField name="hidDocDate"/>
                </div>
            </td>
            <td class="nav-pills2" id="rankTr"></td>
            <td class="nav-pills2" id="docTypeTr">
                <wf:textInput class="span3" id="edDocType" name="docType" value="${attachedDoc?.docType}"/>
            </td>
            <td class="nav-pills2" id="docRefTr"><wf:textInput name="docRef" class="span2" value="" id="edDocRef"/></td>

            <td class="nav-pills2" id="docDateTr">
                <g:textField class="span1" style="width: 80px !important;" name="docDate" value="" id="edDocDate"
                             onkeydown="return allowKeyPress(event);" onPaste="return false;"/>
            </td>

            <td class="nav-pills2" style="width: 150px;">
                <a id="editUploadFile"
                   title="${message(code: 'attachedDocs.uploadFile.button', default: 'Upload file')}"
                   class="btn">
                    ${message(code: 'attachedDocs.uploadFile.button', default: 'Upload file')}
                </a>
            </td>
        </tr>


        <tr id="attachmentRow_" class="attachmentRow_">
            <td id="tdTools_">
                <div class="actions">
                    <a title="Edit" id="edit_">
                        <i class="icon-pencil"></i>
                    </a>
                    <a title="Delete" id="del_">
                        <i class="icon-trash"></i>
                    </a>
                </div>
            </td>
            <td id="attNumber_"></td>
            <td id="docType_"></td>
            <td id="docCode_" style="display: none"></td>
            <td id="docRef_"></td>
            <td id="docDate_"></td>
            <td id="attDoc_"></td>
        </tr>
    </table>
</g:if>
