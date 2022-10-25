<g:set var="attachmentsToRender" value="${instance ? instance.attachments : attachments}"/>
<g:if test="${(instance?.addAttachedDocs || instance?.editAttachedDocs) && attachmentsToRender?.size() > 0}">
    <th style="width: 60px;" class="actions-th2">&nbsp;</th>
</g:if>
<th style="width: 20px">#</th>
<th style="width: 210px">${message(code: 'ephytoGen.attachedDocs.docType', default: 'Document type')}</th>
<th style="width: 135px;">${message(code: 'ephytoGen.attachedDocs.docRef', default: 'Document Ref. No.')}</th>
<th style="width: 90px">${message(code: 'ephytoGen.attachedDocs.docDate', default: 'Document Date')}</th>
<th id="fileHeader" style="width: 100px">${message(code: 'attachedDocs.fileName', default: 'File Name')}</th>