<g:set var="attachmentsToRender" value="${instance ? instance?.attachments : attachments}"/>
<g:if test="${attachmentsToRender?.size() > 0}">
    <g:each in="${attachmentsToRender}" var="att">
        <g:render template="/attachedDoc/attachedDocRecord" model="[
                editAttachedDocs: instance?.editAttachedDocs,
                addAttachedDocs : instance?.addAttachedDocs,
                att             : att,
                instance        : instance,
                controller      : controller]"/>
    </g:each>
</g:if>
<g:else>
    <tr>
        <td class="nav-pills21 actions-th21" style="text-align: center" colspan="5">
            <g:message code="noattacheddoc.docType.label" default="Aucun Fichier Joint"/>
        </td>
    </tr>
</g:else>