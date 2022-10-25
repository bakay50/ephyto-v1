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

<bootstrapdiv id="attachedErrors"></bootstrapdiv>

<bootstrapdiv id="alertInfo"></bootstrapdiv>


<bootstrapdiv id="attachedDocSaveAlertInfoTemplate" style="display: none"><div
        class="wf-alert-scrolable alert alert-block alert-info"><a class="close"
                                                                   data-dismiss="alert">×</a>

    <p><g:message code="attDoc.The_attachment_has_been_recorded.label"
                  default="The attachment has been recorded"/></p></div>
</bootstrapdiv>

<bootstrapdiv id="attachedDocAddedRemoveInfoTemplate" style="display: none"><div
        class="wf-alert-scrolable alert alert-block alert-danger"><a class="close"
                                                                     data-dismiss="alert">×</a>

    <p><g:message code="attDoc.The_attachment_has_been_deleted.label"
                  default="The attachment has been deleted"/></p></div>
</bootstrapdiv>

<bootstrapdiv id="attachedDocAddedUpdateInfoTemplate" style="display: none"><div
        class="wf-alert-scrolable alert alert-block alert-info"><a class="close"
                                                                   data-dismiss="alert">×</a>

    <p><g:message code="attDoc.The_attachment_has_been_update.label"
                  default="The attachment has been deleted"/></p></div>
</bootstrapdiv>

<table class="table table-bordered table-striped tableNoWrap attachmentTable">
    <thead>
    <tr id="allheaderTable">
        <g:render template="/attachedDoc/attachedDocAction"
                  model="['instance': instance, 'controller': controller, addAttachedDocs: addAttachedDocs, editAttachedDocs: editAttachedDocs]"/>
    </tr>
    </thead>
    <tbody id="attachmentTableBody">
    <g:render template="/attachedDoc/attachedDocList"
              model="['instance': instance, 'controller': controller, addAttachedDocs: addAttachedDocs, editAttachedDocs: editAttachedDocs]"/>
    </tbody>
</table>
