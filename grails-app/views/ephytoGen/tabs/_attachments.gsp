<%@ page import="com.webbfontaine.ephyto.constants.Operations" %>
<bootstrap:div class="span12">
    <bootstrap:div style="clear:both; margin-left:10px;">
        <bootstrap:div id="addAttDocs" style="clear:both">
            <h3><g:message code="ephytoGen.attachment.label"/>
            <g:if test="${ephytoGenInstance?.isDocumentEditable()}">
                <g:if test="${ephytoGenInstance?.addAttachedDocs || ephytoGenInstance?.editAttachedDocs}">
                    <bootstrap:linkButton style="width: 100px" title="${message(code: 'attachedDocs.add.button.label')}"
                                          noLink="#" icon="plus" class="btn-block" id="addAttDocModal"
                                          onclick="showAttDoc()"/>
                </g:if>
            </g:if>
            </h3>
        </bootstrap:div>

        <g:render template="/attachedDoc/attachedDocs"
                  model="[
                          'instance'      : ephytoGenInstance,
                          'controller'    : 'attachment',
                          addAttachedDocs : ephytoGenInstance?.addAttachedDocs,
                          editAttachedDocs: ephytoGenInstance?.editAttachedDocs,
                          htAttIsLoaded   : (params.action in [Operations.OA_SAVE, Operations.OA_UPDATE] && !hasErrors)
                  ]"/>
    </bootstrap:div>
</bootstrap:div>