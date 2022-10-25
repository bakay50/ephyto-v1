<g:set var="i" value="${att?.attNumber}"/>
<tr class="attachmentRow_${i} attRow">
    <g:if test="${instance?.editAttachedDocs}">
        <td>
            <bootstrap:div>
                <a title="${message(code: 'attDoc.edit.label', default: 'Edit')}"
                   onclick="showEditDocModal(this.id)" id="editAttDoc_${i}">
                    <bootstrap:icon name="pencil"/>
                </a>
                <a title="${message(code: 'attDoc.delete.label', default: 'Delete')}"
                   onclick="showDeleteModalAttDoc(this.id)" id="delAttDoc_${i}">
                    <bootstrap:icon name="trash"/>
                </a>
            </bootstrap:div>
        </td>
    </g:if>
    <g:elseif test="${instance?.addAttachedDocs}">
        <td>
            <div class="actions"></div>
        </td>
    </g:elseif>
    <td id="attNumber_${i}">${i}</td>
    <td id="docType_${i}">${att?.docType}</td>
    <td id="docCode_${i}" style="display: none;">${fieldValue(bean: att, field: "docCode")}</td>
    <td id="docRef_${i}">${fieldValue(bean: att, field: "docRef")}</td>
    <td id="docDate_${i}">${com.webbfontaine.ephyto.TypeCastUtils.fromDate(att?.docDate)}</td>

    <td id="attDoc_${i}" style="text-align: center">
        <g:if test="${att?.attDoc}">
            <g:set var="attNumber" value="${i}"/>
            <g:set var="fileExtension" value="${att?.fileExtension}"/>
            <g:set var="attId" value="${att?.id}"/>
            <g:set var="conversationId" value="${params?.conversationId}"/>
            <g:link action="downloadFile" controller="${controller}"
                    params="[attNumber: attNumber, fileExtension: fileExtension, documentId: instance?.id, conversationId: conversationId]"
                    class="attDocLink">
                ${message(code: 'attachedDocs.downloadFile.label', default: 'Click to download file')}
            </g:link>
        </g:if>
    </td>
</tr>