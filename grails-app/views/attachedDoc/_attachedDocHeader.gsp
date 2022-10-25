<g:if test="${ephytoGenInstance?.addAttachedDocs}">
    <a onclick="hideAttDocUploadDialog();" class="close">×</a>

    <h3><bootstrap:label labelCode="attDoc.Upload_Filemodal.label"/></h3>
</g:if>
<g:elseif test="${ephytoGenInstance?.editAttachedDocs}">
    <a onclick="hideAttDocUploadDialog();" class="close">×</a>

    <h3><bootstrap:label labelCode="attDoc.Upload_FilemodalModify.label"/></h3>
</g:elseif>