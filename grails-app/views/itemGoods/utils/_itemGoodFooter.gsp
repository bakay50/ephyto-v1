<g:if test="${ephytoGenInstance.addItemGoods}">
    <a id="addGoodModalItemGood" tabindex="0"
       title="${message(code: 'attDoc.Upload_Plus_Addmodal.label', default: 'Add')}"
       class="btn btn-success"
       onclick="goodItemController.addItem()"
       name="addGoodModalItemGood">${message(code: 'attDoc.Upload_Plus_Addmodal.label', default: 'Add')}</a>
</g:if>
<g:elseif test="${ephytoGenInstance.editItemGoods}">
    <a id="modifyGoodModalItemGood" onclick="goodItemController.updateItem()" tabindex="0"
       title="${message(code: 'attDoc.Upload_Plus_Modifymodal.label', default: 'Modify')}"
       class="btn btn-success"
       name="modifyGoodModalItemGood">${message(code: 'attDoc.Upload_Plus_Modifymodal.label', default: 'Modify')}</a>
</g:elseif>
<a id="cancel" data-dismiss="modal" name="cancel" tabindex="0"
   title="${message(code: 'default.button.cancel_att.label', default: 'Cancel')}"
   class="btn btn-sm btn-default">${message(code: 'default.button.cancel_att.label', default: 'Cancel')}</a>
