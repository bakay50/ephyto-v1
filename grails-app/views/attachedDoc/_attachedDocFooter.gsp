<g:if test="${ephytoGenInstance?.addAttachedDocs}">
    <input type="submit" id="startUploadModal"
           title="${message(code: 'attDoc.Upload_Plus_Addmodal.label', default: 'Add')}"
           value="${message(code: 'attDoc.Upload_Plus_Addmodal.label', default: 'Add')}"
           class="btn btn-success"
           name="startUpload">
</g:if>
<g:elseif test="${ephytoGenInstance?.editAttachedDocs}">
    <input type="submit" id="startUploadModal"
           title="${message(code: 'attDoc.Upload_Plus_Modifymodal.label', default: 'Modify')}"
           value="${message(code: 'attDoc.Upload_Plus_Modifymodal.label', default: 'Modify')}"
           class="btn btn-success"
           name="startUpload">
</g:elseif>
<a id="cancel" data-dismiss="modal" name="cancel"
   title="${message(code: 'default.button.cancel_att.label', default: 'Cancel')}"
   class="btn btn-sm btn-default">${message(code: 'default.button.cancel_att.label', default: 'Cancel')}</a>
