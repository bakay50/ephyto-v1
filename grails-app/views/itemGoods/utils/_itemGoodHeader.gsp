<a onclick="hideItemGoodUploadDialog();" class="close">×</a>
<g:if test="${ephytoGenInstance?.addItemGoods}">
    <h3><g:message code="ephytoGen.addGood.label" default="Add Good"/></h3>
</g:if>
<g:elseif test="${ephytoGenInstance?.editItemGoods}">
    <h3><g:message code="ephytoGen.modifyGood.label" default="Modify Good"/></h3>
</g:elseif>
<g:else>
    <h3><g:message code="ephytoGen.viewGood.label" default="View Good"/></h3>
</g:else>