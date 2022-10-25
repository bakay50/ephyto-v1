<g:set var="attachmentsToRender" value="${instance ? instance.attachments : attachments}"/>

<wf:hiddenField name="currentEditableAttDocNumber" disabled="true"/>
<g:if test="${attachmentsToRender?.size() > 0}">
    <g:each in="${attachmentsToRender}" var="att">
        <g:render template="/attachments/utils/attachmentRecord" model="[
                editAttachedDocs: editAttachedDocs,
                addAttachedDocs : addAttachedDocs,
                att             : att,
                instance        : instance,
                controller      : controller]"/>
    </g:each>
</g:if>
<g:else>
    <tr>
        <td class="nav-pillsnofile actions-thnofile" style="text-align: center" colspan="5">
            <g:message code="noattacheddoc.docType.label" default="Aucun Fichier Joint"/>
        </td>
    </tr>
</g:else>

