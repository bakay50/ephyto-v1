<g:set var="itemGoodsToRender" value="${ephytoGenInstance ? ephytoGenInstance?.itemGoods : itemGoods}"/>
<g:if test="${itemGoodsToRender?.size() > 0}">
    <g:each in="${itemGoodsToRender}" var="item">
        <g:render template="/itemGoods/utils/itemGoodRecord" model="[
                editItemGoods    : ephytoGenInstance?.editItemGoods,
                addItemGoods     : ephytoGenInstance?.addItemGoods,
                item             : item,
                ephytoGenInstance: ephytoGenInstance]"/>
    </g:each>
</g:if>
<g:else>
    <tr>
        <td class="nav-pills21 actions-th21" style="text-align: center" colspan="15">
            <g:message code="itemGood.noItemGood.label" default="Aucune Marchandise"/>
        </td>
    </tr>
</g:else>
<wf:hiddenField name="totalItems" id="totalItems" disabled="" value="${totalItems}"/>
<wf:hiddenField name="totalGrossWeight" id="totalGrossWeight" disabled="" value="${totalGrossWeight}"/>
<wf:hiddenField name="totalNetWeight" id="totalNetWeight" disabled="" value="${totalNetWeight}"/>
<wf:hiddenField name="totalQuantity" id="totalQuantity" disabled="" value="${totalQuantity}"/>
