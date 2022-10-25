<bootstrap:div style="display:none;width: 600px; height:600px; left: 30%; top: 30%; z-index:5009;" class="modal fade"
               tabindex="-1"
               title="Delete File" data-backdrop="static" data-keyboard="false" id="js_deleteCancelFileDlg"
               aria-hidden="false">
    <bootstrap:div class="modal-dialog">
        <bootstrap:div class="modal-content">
            <bootstrap:div class="modal-header">
                <a class="close" data-dismiss="modal">x</a>

                <h3>${message(code: 'default.button.deleteatt.label', default: 'Delete File')}</h3>
            </bootstrap:div>
            <bootstrap:div class="modal-body" id="js_itemCancelDeleteItemModalBody">
                <input type="hidden" id="rank" name="rank" value="">
                <dl class="dl-horizontal">
                    <p>${message(code: 'default.deletecancelitem.warning_dialog.label', default: 'Are you sure you want to delete this file ?')}</p>
                </dl>
            </bootstrap:div>
            <bootstrap:div class="modal-footer">
                <a id="deleteItem" title="${message(code: 'default.button.deletedialog.label', default: 'Delete')}"
                   onclick="removeAttDocModal();
                   return false;"
                   class="btn btn btn-success">${message(code: 'default.button.deletedialog.label', default: 'Delete')}</a>
                <a id="cancel" data-dismiss="modal" name="cancel"
                   title="${message(code: 'default.button.cancel_att.label', default: 'Cancel')}"
                   class="btn btn-sm btn-default">${message(code: 'default.button.cancel_att.label', default: 'Cancel')}</a>
            </bootstrap:div>
        </bootstrap:div>
    </bootstrap:div>
</bootstrap:div>