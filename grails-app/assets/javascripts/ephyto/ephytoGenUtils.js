/**
 * Created by DEV23 on 3/22/2016.
 */

function setParamsValue(rank, rank2, fieldsToSet) {
    var conversationId = $("#conversationId").val();
    var params = {};
    jQuery.each( fieldsToSet, function( k, v ) {
        var mapVal;
        if (typeof v === 'string' || v instanceof String) {
            if (v.indexOf("Number") >= 0) {
                mapVal = rank?rank:rank2
            } else {
                var valCtr = $("#"+v);
                if (valCtr.is('label')) {
                    mapVal = valCtr.text();
                } else {
                    mapVal = valCtr.val();
                }
            }
            params["conversationId"] = conversationId
            params[k] = mapVal
        } else {
            var objectParams = setObjectParams(v, rank2);
            params[k] = objectParams
        }
    });
    return params
}

function resetData(fieldsToReset) {
    jQuery.each( fieldsToReset, function( i, field ) {
        var element = $("#"+field);
        if (element.is("input")) {
            element.val("");
        }
    });
}

function disableFormFields(targetElements) {
    jQuery.each( targetElements, function( i, field ) {
        var element = $("#"+field);
        if (element.is("input")) {
            element.attr("disabled", true);
        } else if (element.is("a")) {
            element.unbind('click').addClass("btn-disabled");
        }
    });
}

function enableFormFields(targetElements) {
    jQuery.each( targetElements, function( i, field ) {
        var element = $("#"+field);
        if (element.is("input")) {
            element.removeAttr('disabled');
        } else if (element.is("a")) {
            element.bind('click').removeClass("btn-disabled");

        }
    });
}

function manageRowToUpdate(targetElements, rowNumber, editedRow, rowToUpdate) {
    var values = {}
    jQuery.each( targetElements, function( i, field ) {
        var element = $("#"+field+"_"+rowNumber);
        if (element.is("td")) {
            var value = $.trim(element.text())
            values[field] = value
        }
    });
    $(rowToUpdate).replaceWith(editedRow)
    jQuery.each( values, function( k, v ) {
        var capField = capitalizeFirstLetter(k)
        $("#ed"+capField).val(v);
        $("#hid"+capField).val(v);
    });
}

function validateFormTreatment(targetFields) {
    var isFormValid = true;
    taglib.validation.assignMandatoryRuleToInput = false;
    $(targetFields).each(function () {
        isFormValid = $(this).valid() && isFormValid;  // once failed => returns false. need to do validation on all fields, thats why .valid() is before isItemValid.
    });
    return isFormValid;
}

function disableFormTreatment(targetForm) {
    //targetForm.children().attr("disabled","disabled");
    var allItemElements = $("#f_treatmentContainer #treatmentFormCtr .wfinput")
    $.each(allItemElements, function () {
        $(this).attr("disabled", "disabled");
    });
}

function removeReadonlyFormTreatment(targetForm) {
    var allItemElements = $("#f_treatmentContainer #treatmentFormCtr .wfinput")
    $.each(allItemElements, function () {
        $(this).attr("readonly", "false");
    });
}

function setSuccessResponse(resp, ctr) {
    $(ctr).html(resp)
}

function removeServerErrors(targetElement) {
    $(targetElement).empty();
}

function renderActionResults(ctrToRemove, ctrToReplace, response) {
    $(ctrToRemove).remove();
    $(ctrToReplace).append(response);
}

function showAjaxSuccessDialog(dialogText) {
    //I18N["client.successOperationTitle"]
    openMessageDialog(I18N['client.successOperationTitle'], dialogText)
}

function initDialogCustomStyle() {
    var infoMsgCtr = $("#messageDialog").find("span#infoMessage");
    var msgsToCheck = [I18N['client.requestNumber']];
    if(typeof infoMsgCtr != "undefined") {
        var currentMsg = infoMsgCtr.text();
        var separators = ['\\,',':']
        jQuery.each(msgsToCheck, function(i,msg) {
            if (currentMsg.indexOf(msg) >= 0) {
                var msgList = currentMsg.split(new RegExp(separators.join('|'), 'g'));
                if (msgList.length === 4) {
                    var htmlToReplace = "<table><tbody><tr><td><b>"+msgList[0]+"</b></td><td style='padding-left: 30px;'>"
                        +msgList[1]+"</td></tr><tr><td><b>"+msgList[2]+"</b></td><td style='padding-left: 30px;'>"
                        +msgList[3]+"</td></tr></tbody></table>"
                    infoMsgCtr.replaceWith(htmlToReplace)
                }
            }
        });
    }
}

function openMessageDialog(messageDialogTitle, infoMessage) {
    $("#okButton").click(function () {
        $("#messageDialog").modal('hide');
    });

    $("#messageDialog").bind("keydown", function (event) {
        if (event.which === 13) {
            $("#messageDialog").modal('hide');
        }
    });

    $("#messageDialogTitle").text(messageDialogTitle);
    $("#infoMessage").text(infoMessage);

    $("#messageDialog").modal({
        backdrop: "static"
    });

    $("#messageDialog").focus();
}