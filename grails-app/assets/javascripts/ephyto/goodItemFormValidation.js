/**
 * Created by BAKAYOKO ABDOULAYE on 25/09/2016.
 */



var gooditemMandatoryFields = new Array("quantity", "netWeight", "grossWeight")

function initJqueryValidationItemGoods() {
    $.validator.setDefaults({errorClass: "form_error", errorElement: "label"});
    $.extend(jQuery.validator.messages,
        {
            number: I18N['client.NUMBER_DIGITS'],
            digits: I18N['client.NUMBER_DIGITS']
        })
    validateGoodItemForm();
    var form_GoodItem = $("#dialogItemGood #appformitemgood");
    var btnsgooditem = form_GoodItem.find("[id^='operation'][class~='btn']");
    btnsgooditem.mousedown(function (e) {
        form_GoodItem.validate();
    });
}


function validateGoodItemForm() {
    var validation_items = $("#dialogItemGood #appformitemgood").validate({
        invalidHandler: function (form, validator) {
            var elements = validator.invalidElements();
            if (elements) {
                var firstElement$ = $(elements[0]);
                if (firstElement$.is(":hidden")) {
                    focusOnField(firstElement$.attr("id"), function (targetField$) {
                        setTimeout(function () {
                            targetField$.focus()
                        }, 200)
                    })
                } else {
                    setTimeout(function () {
                        firstElement$.focus();
                    }, 200)
                }
            }
        },
        ignore: [],
        rules: {
            batchNumber: {
                required: {
                    depends: function () {
                        return isGoodsFieldsMandatory('batchNumber');
                    }
                }
            },
            subBatchNumber: {
                required: {
                    depends: function () {
                        return isGoodsFieldsMandatory('subBatchNumber');
                    }
                }
            },
            packageMarks: {
                maxlength: 50
            },
            observations: {
                maxlength: 200
            },
            quantity: {
                required: {
                    depends: function () {
                        return isGoodsFieldsMandatory('quantity');
                    }
                }
            },
            netWeight: {
                required: {
                    depends: function () {
                        return isGoodsFieldsMandatory('netWeight');
                    }
                }
            },
            grossWeight: {
                required: {
                    depends: function () {
                        return isGoodsFieldsMandatory('grossWeight');
                    }
                }
            }
        },
        errorPlacement: function (error, element) {
            var fieldName = $(element).attr("name")
            // if (fieldName == "netWeight") {
            //     error.insertAfter("#addonnetWeight");
            //     var id = "fieldName-" + "error"
            //     var field = $("#" + id)
            //     field.css("margin-right", "0px")
            // } else if (fieldName == "grossWeight") {
            //     error.insertAfter("#addongrossWeight");
            //     var id = "fieldName-" + "error"
            //     var field = $("#" + id)
            //     field.css("margin-right", "0px")
            // } else {
                error.insertAfter(element)
            //}

        },
        showErrors: function (errorMap, errorList) {
            for (var i = 0; errorList[i]; i++) {
                var element = this.errorList[i].element;
                this.errorsFor(element).remove();
            }
            this.defaultShowErrors();
        },
        onkeyup: function (element) {
            $(element).valid();
        },
        onfocusout: function (element) {
            $(element).valid();
        },
        submitHandler: function (form) {
            form.submit();
        }

    });
}

function isGoodsFieldsMandatory(fieldName) {
    switch (fieldName) {
        case "batchNumber":
        case "subBatchNumber":
        case "quantity":
        case "netWeight":
        case "grossWeight":
            if ($.inArray(fieldName, gooditemMandatoryFields) > -1) {
                return isMandatoryFields(fieldName);
            } else {
                return isNotMandatoryFields(fieldName);
            }
            break;
        default:
            return false;
            break
    }

}
