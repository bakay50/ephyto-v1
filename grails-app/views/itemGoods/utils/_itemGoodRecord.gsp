<g:set var="i" value="${item?.itemRank}"/>
<tr class="itemGoodRow_${i} attRow">
    <td class="nav-pills2 actions-th2">
        <bootstrap:div style="margin-left:2px !important;width:7vh !important;">
            <g:if test="${ephytoGenInstance?.isDocumentEditable}">
                <a href="javascript:void(0)" title="${message(code: 'itemGood.edit.label', default: 'Edit')}" class="editItemGoodBtn linkBtns"
                   onclick="goodItemController.showEditItem(${item?.itemRank})" id="editItemGood_${i}">
                    <bootstrap:icon name='pencil'/>
                </a>
                <a href="javascript:void(0)" title="${message(code: 'itemGood.delete.label', default: 'Delete')}" class="delItemGoodBtn linkBtns"
                   onclick="goodItemController.deleteItem(${item?.itemRank})" id="delItemGood_${i}">
                    <bootstrap:icon name='trash'/>
                </a>
            </g:if>
            <a href="javascript:void(0)" title="${message(code: 'itemGood.show.label', default: 'Show')}" class="showItemGoodBtn linkBtns"
               onclick="goodItemController.showItem(${item?.itemRank})" id="showItemGood_${i}">
                <bootstrap:icon name='eye-open'/>
            </a>
        </bootstrap:div>

    </td>
    <g:if test="${!ephytoGenInstance?.isDocumentEditable && ephytoGenInstance?.addItemGoods}">
        <td>
            <div></div>
        </td>
    </g:if>
    <td id="itemRank_${i}">${i}</td>
    <td id="previousDocumentReference_${i}" title="${item?.previousDocumentReference}">${item?.previousDocumentReference}</td>
    <td id="previousDocumentItem_${i}" title="${fieldValue(bean: item, field: "previousDocumentItem")}">${fieldValue(bean: item, field: "previousDocumentItem")}</td>
    <td id="batchNumber_${i}" title="${fieldValue(bean: item, field: "batchNumber")}">${fieldValue(bean: item, field: "batchNumber")}</td>
    <td id="subBatchNumber_${i}" title="${fieldValue(bean: item, field: "subBatchNumber")}">${fieldValue(bean: item, field: "subBatchNumber")}</td>
    <td id="quantity_${i}" title="${fieldValue(bean: item, field: "quantity")}">${fieldValue(bean: item, field: "quantity")}</td>
    <td id="remainingQuantiy_${i}" title="${fieldValue(bean: item, field: "remainingQuantiy")}">${fieldValue(bean: item, field: "remainingQuantiy")}</td>
    <td id="netWeight_${i}" title="${fieldValue(bean: item, field: "netWeight")}">${fieldValue(bean: item, field: "netWeight")}</td>
    <td id="remainingNetWeight_${i}" title="${fieldValue(bean: item, field: "remainingNetWeight")}">${fieldValue(bean: item, field: "remainingNetWeight")}</td>
    <td id="grossWeight_${i}" title="${fieldValue(bean: item, field: "grossWeight")}">${fieldValue(bean: item, field: "grossWeight")}</td>
    <td id="remainingGrossWeight_${i}" title="${fieldValue(bean: item, field: "remainingGrossWeight")}">${fieldValue(bean: item, field: "remainingGrossWeight")}</td>
    <td id="analysisResult_${i}" title="${fieldValue(bean: item, field: "analysisResult")}">${fieldValue(bean: item, field: "analysisResult")}</td>
    <td id="potted_${i}" title="${fieldValue(bean: item, field: "potted")}">${fieldValue(bean: item, field: "potted")}</td>
    <td id="observations_${i}" title="${fieldValue(bean: item, field: "observations")}">${fieldValue(bean: item, field: "observations")}</td>
</tr>