/**
 * Created by DEV23 on 3/28/2016.
 */

var params = {}
var goodItemController = {
    /**
     * @return {boolean}
     */
    CRUD: function (data) {
        $.ajax({
            type: data.type,
            url: data.url,
            data: data.params,
            success: function (response) {
                var $batchNumber = $("#batchNumber");
                var $goodsContainer = $("#goodsContainer")
                var $subBatchNumber = $("#subBatchNumber");
                var $quantity = $("#quantity");
                var $netWeight = $("#netWeight");
                var $grossWeight = $("#grossWeight");
                var $uploadDialogItemGood = $('#dialogItemGood')
                switch (data.action) {
                    case "addItem":
                        goodItemController.renderInnerForm(response);
                        goodItemController.updateTotal();
                        $uploadDialogItemGood.modal('hide');
                        break;
                    case "showItem":
                        goodItemController.initShowModalGoodsForm(response,"showItem");
                        break;
                    case "showEditItem":
                        goodItemController.initShowModalGoodsForm(response,"showEditItem");
                        break;
                    case "deleteItem":
                        goodItemController.renderInnerForm(response);
                        goodItemController.updateTotal();
                        break;
                    case "updateItem":
                        goodItemController.renderInnerForm(response);
                        goodItemController.updateTotal();
                        $uploadDialogItemGood.modal('hide');
                        break;
                }
            },
            error: function (response) {
            }
        });
        return false;
    },

    addItem: function () {
        if($("#addGoodModalItemGood").attr("disabled") != "disabled" ){
            if (goodItemController.validateGoodsItemsFields()) {
                params ={}
                params["batchNumber"] = $("#batchNumber").val();
                params["subBatchNumber"] = $("#subBatchNumber").val()
                params["quantity"] = $("#quantity").val()
                params["netWeight"] = $("#netWeight").val()
                params["grossWeight"] = $("#grossWeight").val()
                params["quantityUTF"] = $("#quantityUTF").val()
                params["netWeightUTF"] = $("#netWeightUTF").val()
                params["grossWeightUTF"] = $("#grossWeightUTF").val()
                params["itemRank"] = $("#itemRank").val()
                var lang = $("#locale").val()
                params["locale"] = lang
                params["itemRank"] = $("#itemRank").val()
                params["conversationId"] = $("#conversationId").val()
                $('#addGoodModalItemGood').attr("disabled", "disabled")
                var data = {
                    type: "GET",
                    url: $("#saveItemUrl").val(),
                    params:params,
                    action: "addItem"
                }
                goodItemController.CRUD(data);
            }
        }
    },
    showItem: function (itemRank) {
        params ={}
        params["itemRank"] = itemRank
        params["conversationId"] = $("#conversationId").val()
        var data = {
            type: "POST",
            url: $("#showItemUrl").val(),
            params: params,
            action: "showItem"
        };
        goodItemController.CRUD(data);
    },
    showEditItem: function (itemRank) {
        params ={}
        params["itemRank"] = itemRank
        params["conversationId"] = $("#conversationId").val()
        var data = {
            type: "POST",
            url: $("#showEditItemUrl").val(),
            params: params,
            action: "showEditItem"
        };
        goodItemController.CRUD(data);
    },
    deleteItem: function (itemRank) {
        params ={}
        params["conversationId"] = $("#conversationId").val()
        params["itemRank"] = itemRank

        openConfirmDialog(I18N['client.deleteItemConfirm'],
            function () {
                var data = {
                    type: "POST",
                    url: $("#deleteItemUrl").val(),
                    params: params,
                    action: "deleteItem"
                }
                goodItemController.CRUD(data);

            }, // no button handler
            function () {
            }, // close button handler
            function () {
            }
        )
    },
    updateItem: function () {
        if (goodItemController.validateGoodsItemsFields()) {
            params ={}
            params["batchNumber"] = $("#batchNumber").val();
            params["subBatchNumber"] = $("#subBatchNumber").val()
            params["quantity"] = $("#quantity").val()
            params["netWeight"] = $("#netWeight").val()
            params["grossWeight"] = $("#grossWeight").val()
            params["quantityUTF"] = $("#quantityUTF").val()
            params["netWeightUTF"] = $("#netWeightUTF").val()
            params["grossWeightUTF"] = $("#grossWeightUTF").val()
            params["conversationId"] = $("#conversationId").val()
            params["itemRank"] = $("#dialogItemGood #itemRank").val()
            var data = {
                type: "POST",
                url: $("#updateItemUrl").val(),
                params: params,
                action: "updateItem"
            };
            goodItemController.CRUD(data);
        }
    },
    initGoodsItem: function () {
        cancelEnterKey();
        applyRegexpRestrictionToListOfInputs($("#dialogItemGood [regexp], #dialogItemGood [regexpForRule]"));
        formatNumberInputOnChange($("#dialogItemGood .wf-decimal-input, .wf-monetary-input, .wf-quantity-input, .wf-exchange-rate-input, .wf-custom-input"));
    },

    validateItem: function () {
        var targetFields = $("#dialogItemGood").find(".wfinput");
        return validateForm(targetFields);
    },

    validateGoodsItemsFields:function(){
        var targetFields = $("#dialogItemGood #appformitemgood").find(".wfinput");
        return validateGoodsItemForm(targetFields);
    },

    removeItemFieldError: function () {
        var allItemElems = $("#items #itemContainer .wfinput");
        removeError(allItemElems, "itemErrorContainer");
    },

    getItemFields: function () {
        var itemFields = ["itemCode", "itemName", "itemRank", "addItemLink"]
        return itemFields;
    },

    getItemFieldsToConfigure: function (isItemForm) {
        var itemFields
        if (isItemForm == true) {
            itemFields = [".itemMainBtn"]
        } else {
            itemFields = [".itemListBtn"]
        }
        return itemFields
    },

    getErrorContainer: function () {
        var errorContainer = $("#itemFormCtr").find("#itemErrors");
        return errorContainer;
    },
    manageRowToUpdate : function (rowNumber, editedRow, rowToUpdate) {
        manageRowToUpdate(goodItemController.getItemFields(),rowNumber, editedRow, rowToUpdate)
    },

    disableItemForm: function () {
        var itemTab = $("#dialogItemGood")
        disableForm(itemTab)
    },

    renderInnerForm: function (response) {
        var ctrToRemove = $("#itemGoodListBody");
        var itemTab = $("#itemGoodListBody");
        renderActionResult(ctrToRemove, itemTab, response)
    },
    removeErrorsOnDelete: function (params) {
        var elStr = params["rank"]+"_itemsTab"
        removeAllTabErrors("itemsTab")
    },
    initShowModalGoodsForm : function(response, actionName){
        $("#dialogItemGood .modal-header").html(response.headermodal);
        $("#dialogItemGood .modal-body #content").html(response.bodymodal);
        $("#dialogItemGood .modal-footer").html(response.footermodal);
        if(actionName == "showEditItem"){
            cancelEnterKey();
            applyRegexpRestrictionToListOfInputs($("#dialogItemGood [regexp], #dialogItemGood [regexpForRule]"));
            formatNumberInputOnChange($("#dialogItemGood .wf-decimal-input, .wf-monetary-input, .wf-quantity-input, .wf-exchange-rate-input, .wf-custom-input"));
        }
        $("#dialogItemGood").modal({backdrop: "static"});
    },
    updateTotal :function(){
        $("#totalItems").val($("#itemGoodListBody #totalItems").val())
        $("#totalGrossWeight").val($("#itemGoodListBody #totalGrossWeight").val())
        $("#totalNetWeight").val($("#itemGoodListBody #totalNetWeight").val())
        $("#totalQuantity").val($("#itemGoodListBody #totalQuantity").val())
    }
}