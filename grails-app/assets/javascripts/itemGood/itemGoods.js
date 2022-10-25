/**
 * Created by Sidoine BOSSO on 9/19/2016.
 */

function initModalForShow(){
    $('#dialogItemGood').css({
        "z-index": "5000",
        "overflow-y": "auto",
        "max-height": "$(window).height() * 0.7"
    });
}

function updateModalFormForNew(){
    $("#dialogItemGood #batchNumber").val("");
    $("#dialogItemGood #subBatchNumber").val("");
}

function setDefaultValues(){
    if($("#dialogItemGood #quantity").val().length==0){
        $("#dialogItemGood #quantity").val($("#goodsContainer #defaultQuantity").val());
    }
    if($("#dialogItemGood #netWeight").val().length==0){
        $("#dialogItemGood #netWeight").val($("#goodsContainer #defaultNetWeight").val())
    }
    if($("#dialogItemGood #grossWeight").val().length==0){
        $("#dialogItemGood #grossWeight").val($("#goodsContainer #defaultGrossWeight").val())
    }
}

function updateModal(data){
    $("#dialogItemGood .modal-header").html(data.headermodal);
    $("#dialogItemGood .modal-body #content").html(data.bodymodal);
    $("#dialogItemGood .modal-footer").html(data.footermodal);

    cancelEnterKey();
    applyRegexpRestrictionToListOfInputs($("#dialogItemGood [regexp], #dialogItemGood [regexpForRule]"));
    formatNumberInputOnChange($("#dialogItemGood .wf-decimal-input, .wf-monetary-input, .wf-quantity-input, .wf-exchange-rate-input, .wf-custom-input"));
    initJqueryValidationItemGoods();
    setDefaultValues()
    $("#dialogItemGood").modal({backdrop: "static"});
}

// ADDING MODAL

function showDialogItemGoods() {
    var conversationId = $("#conversationId").val()
    $.ajax({
        type: "POST",
        data: {
            conversationId: conversationId
        },
        beforeSend: function () {
            initModalForShow();
            updateModalFormForNew();
        },
        url: $("#openItemGood").val()
    }).done(function (data) {
        updateModal(data);
        updateModalFormForNew()
    });
}

function initModalForShow(){
    $('#dialogItemGood').css({
        "z-index": "5000",
        "overflow-y": "auto",
        "max-height": "$(window).height() * 0.7"
    });
}

function initClose(){
    $("#dialogItemGood").modal({backdrop: "static"});
}

function hideItemGoodUploadDialog() {
    $('#dialogItemGood').modal('hide');
}

function enableAttButtons() {
}
function renderActionResult(ctrToRemove, ctrToReplace, response) {
    $(ctrToRemove).html("");
    $(ctrToReplace).html(response);
}


function disableForm(targetForm) {
    var allItemElements = $("#dialogItemGood .wfinput")
    $.each(allItemElements, function () {
        $(this).attr("readonly", "readonly");
    });
}

function enableItemsForm(){
    var theField = ['batchNumber', 'subBatchNumber', 'quantity', 'netWeight', 'grossWeight']
    $.each(theField, function () {
        $("#dialogItemGood #"+this).attr("readonly", false);
    });
}

function initItemForm(){
    var theInitField = ['batchNumber', 'subBatchNumber', 'quantity', 'netWeight', 'grossWeight']
    $.each(theInitField, function () {
        $("#dialogItemGood #"+this).val("");
    });
}

function initForm(targetForm) {
    var allItemElements = $("#dialogItemGood .wfinput")
    $.each(allItemElements, function () {
        $(this).val("")
    });
}

function showDeleteModalItemGood(elemId) {
    var elemIdArr = elemId.split("_");
    var attNumber = Number(elemIdArr[1]);
    $("#js_itemCancelDeleteItemGoodModalBody #rank").val(attNumber);
    $("#js_deleteCancelGoodDlg").modal({backdrop: "static"});
    $('#confirmDialog').on('shown.bs.modal', function (e) {
        $("#js_itemCancelDeleteItemGoodModalBody #rank").val(elemId);
    })
    $('#confirmDialog').on('show.bs.modal', function (e) {
        $("#js_itemCancelDeleteItemGoodModalBody #rank").val(elemId);
    })
}