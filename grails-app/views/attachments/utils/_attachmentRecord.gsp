
<g:set var="i" value="${att.attNumber}"/>
<tr class="attachmentRow_${i} attRow">
    <g:if test="${editAttachedDocs}">
        <td>
            <div class="actions">
                <a title="${message(code: 'attachedDocs.editLink', default: 'Edit')}"
                   onclick="editAttDoc(this.id)" id="edit_${i}" class="editAttButton">
                    <i class="icon-pencil"></i>
                </a>
                <a title="${message(code: 'attachedDocs.deleteLink', default: 'Delete')}"
                   onclick="removeAttDoc(this.id)" id="del_${i}" class="deleteAttButton">
                    <i class="icon-trash"></i>
                </a>
            </div>
        </td>
    </g:if>
    <g:elseif test="${addAttachedDocs}">
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
        <g:if test="${att.attDoc}">
            <g:set var="attNumber" value="${i}"/>
            <g:set var="fileExtension" value="${att?.fileExtension}"/>
            <g:set var="attId" value="${att?.id}"/>
            <wfConversation:link action="downloadFile" controller="${controller}"
                         params="[attNumber: attNumber, fileExtension: fileExtension, documentId: instance?.id]" class="attDocLink">
                ${message(code: 'attachedDocs.downloadFile.label', default: 'Click to download file')}
            </wfConversation:link>
        </g:if>
    </td>
</tr>