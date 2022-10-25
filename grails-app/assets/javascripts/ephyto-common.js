/**
 * Created by DEV22 on 4/20/2016.
 */
/**
 *Check if Ajax is running
 */
var ajaxRunning = false;
function setupAjaxListener(){
    $(document).on('ajaxStart',function(){
        ajaxRunning = true;
    });
    $(document).on('ajaxComplete',function(){
        ajaxRunning = false;
    });
}
/*Enables or disables target element
* disables or enables <a> elements click function
* */
function configureFormFields(targetElements, disableField) {
    jQuery.each( targetElements, function( i, elField ) {
        var element = $(elField);
        if ($(element).length>0) {
            jQuery.each( element, function( i, field ) {
                if (disableField == true) {
                    if (element.is('input, select')) {
                        element.attr("disabled", true);
                    } else if (element.is("a")) {
                        element.addClass("disableClick");
                        element.addClass("btn-disabled");
                    }else if (element.is("button")){
                        element.addClass("disableClick");
                        element.addClass("btn-disabled");
                    }
                } else {
                    if (element.is('input, select')) {
                        element.removeAttr('disabled');
                    } else if (element.is("a")) {
                        element.removeClass("disableClick");
                        element.removeClass("btn-disabled");
                        // added to remove disable attribute on cancel and import button after import xml file
                        if(element.is('[disabled=disabled]')){
                            element.removeAttr("disabled");
                        }
                    }else if (element.is("button")){
                        element.removeClass("disableClick");
                        element.removeClass("btn-disabled");
                    }
                }
            });
        }
    });
}

function hideTargetElement(targetElements, hideField) {
    jQuery.each( targetElements, function( i, field ) {
        if (hideField == true) {
            $(field).addClass("visibility-hidden")
        } else {
            $(field).removeClass("visibility-hidden")
        }
    });
}

function configureMainButtons(mainContainer, formIsOpened) {
    updateClassOnOpenedForm(mainContainer, formIsOpened)
    if (formIsOpened == true) {
        configureFormFields(getMainButtonsToConfigure(), formIsOpened);
    } else {
        var isAnyFormOpened =$("#formContainer").find(".edit-form-opened").length>0
        if (isAnyFormOpened == false) {
            configureFormFields(getMainButtonsToConfigure(), formIsOpened);
        }
    }
}

function getMainButtonsToConfigure() {
    var operationButtons = $("#appMainForm").find("[name^='operation'][class~='btn']").toArray();
    var otherButtons = [".xmlImportButton",".cancel",".exportXML","#exportXMLButton",".Close",".print","#delete"]
    var mainButtons = $.merge(operationButtons, otherButtons)
    return mainButtons;
}

function updateClassOnOpenedForm(targetField, addClass) {
    if (addClass == true) {
        $(targetField).addClass("edit-form-opened")
    } else {
        $(targetField).removeClass("edit-form-opened")
    }
}

function initXmlUploadForm() {
    if ($("#importXmlDialog").attr("id") !== undefined && $("#xmlImportButton").attr("id") !== undefined) {
            initXmlUploadAjaxForm();
    }
}

function responseHasErrors(resp, errCtr) {
    var hasErrors = false
    if (resp.error == 'error') {
        $(errCtr).html(resp.payload)
        hasErrors = true
        focusOnErrorField();
        removeFieldError();
    }
    return hasErrors
}

function hasUserProperties(userProps) {
    var hasUserProperty = $(userProps).val()
    return typeof hasUserProperty!="undefined" && hasUserProperty=="true"
}

function getLocalRef(name) {
    return window[name];
}

function allowKeyPress(e) {
    var result = e.which == 8 || e.which == 46
    return result
}

function capitalizeFirstLetter(string) {
    return (string.charAt(0).toUpperCase() + string.slice(1)).toString();
}

function updateTextOrValue(element,controllerName,value){
    if(controllerName){ // autocompletion search
        if($(element)[0] === undefined || typeof $(element)[0] === "undefined"){
            $(element).text(value);
        }else{
            $(element).val(value);
        }
    }else{ // create autocompletion
        if($(element)[0].value === undefined || typeof $(element)[0].value === "undefined"){
            $(element).text(value);
        }else{
            $(element).val(value);
        }
    }
}

function updateElementNotEmpty(element,controllerName){
    if(controllerName){ // autocompletion search
        if($(element)[0] === undefined || typeof $(element)[0] === "undefined"){
            return !$(element).text();
        }else{
            return false;
        }
    }else{ // create autocompletion
        if($(element)[0].value === undefined || typeof $(element)[0].value === "undefined"){
            return !$(element).text();
        }else{
            return false;
        }
    }
}

function setupAutocomplete(){
    var controllerName = $("#controllerName").val();
    var prevVal;
    var setter = {
        setTextOrValue :
            function($element,value){
                $.each($element,function(){
                        updateTextOrValue(this,controllerName,value);
                });
            },
        elementNotEmpty :
            function($element){
                $.each($element,function(){
                    updateElementNotEmpty(this,controllerName)
                });
            }
    };
    var utils = {
        getRootId :
            function(name){
                var index = 0;
                for(var x=0;x < name.length; x++){
                    if(name[x].toUpperCase() == name[x]){
                        index = x;
                        break;
                    }
                }
                return name.substr(0,index);
            }
    };

    $(document.body).on("autocompleteselect",".ui-autocomplete-input",function(event, ui){
        if(ui){
            prevVal = ui.item.value;
        }
    });

    $(document.body).on("autocompletechange",".ui-autocomplete-input",function(event, ui){
        var $descriptionElements = [];
        if($(this).parent().next().length > 0){
            $descriptionElements.push($(this).parent().next().children());
        }else{
            $descriptionElements = $(this).parent().parent("div.control-group-sub").siblings().find("[id^='"+ utils.getRootId($(this).attr("id")) +"']");
        }

        if($descriptionElements.length > 0 && setter.elementNotEmpty($descriptionElements)){
            $(this).val("");
            $.trim($(this).val());
            setTimeout(function() {
                $(this).focus().addClass();
            }, 500);
        }
        else if(prevVal && prevVal != $(this).val()){
            $(this).val("");
            $.trim($(this).val());
            setTimeout(function() {
                $(this).focus().addClass();
            }, 500);
            if($descriptionElements) setter.setTextOrValue($descriptionElements,"");
        }else{
            if((prevVal == undefined || prevVal == 'undefined')){
                $(this).val("");
                $.trim($(this).val());
                setTimeout(function() {
                    $(this).focus().addClass();
                }, 500);
                if($descriptionElements) setter.setTextOrValue($descriptionElements,"");
            }
        }
    });
}

if(typeof String.prototype.trim !== 'function') {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g, '');
    }
}


function showValidationDialog(dialogText) {
    openMessageDialog("", dialogText)
}

function showAjaxSuccessDialog(dialogText) {
    openMessageDialog(I18N['client.successOperationTitle'], dialogText)
}

function overrideFormatNumberValidation(){
    jQuery.validator.addMethod("commaSeparatorNumber", function (value, element) {
        return this.optional(element) || /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:[\.,]\d+)?$/.test(value);
    }, I18N['client.invalidNumberFormat']);
}

function clearFields(targetElements){
    jQuery.each( targetElements, function( i, elField ) {
        var element = $(elField);
        if ($(element).val().length>0) {
            if (element.is('input, select')) {
                element.val("");
            }
        }
    });
}

function validateForm(targetFields) {
    var isFormValid = true;
    taglib.validation.assignMandatoryRuleToInput = false;
    $(targetFields).each(function () {
        isFormValid = $(this).valid() && isFormValid;  // once failed => returns false. need to do validation on all fields, thats why .valid() is before isItemValid.
    });
    return isFormValid;
}

function validateGoodsItemForm(targetFields) {
    var isItemValid = true
    $(targetFields).each(function () {
        isItemValid = $(this).valid() && isItemValid;  // once failed => returns false. need to do validation on all fields, thats why .valid() is before isItemValid.
    });
    return isItemValid;
}

function initAndActivateValidationSearch(){
    taglib.validation.assignMandatoryRuleToInput = false;
}

function updateLabelConfirmation(labelName){
    if(typeof $("#typedocument").val() != "undefined"){
        this.utilsConfirmThisAppMessage = labelName;
    }
}
//
// function updateResizeColumnsTable(tableName){
//     if(typeof $("#" + tableName).val() != "undefined"){
//         $("#" + tableName).colResizable({
//             postbackSafe: true,
//             partialRefresh: true
//         })
//     }
// }



function updateLabelConfirmation(labelName){
    if(typeof $("#typedocument").val() != "undefined"){
        this.utilsConfirmThisAppMessage = labelName;
    }
}


function focusOnField(fieldId, func) {
    if (fieldId) {
        var errorId = '#' + fieldId;
        var error =$(errorId);
        if(error.length===0){
            var crit = '.error [id^="'+fieldId+'"]';
            error = $(crit).first()
        }
        var list = $(error.parents('div.tab-pane').get().reverse());
        list.each(function(){
            var content = $(this).closest('div.tab-content');
            var ul = content.parent().find('ul.nav-tabs');
            var tabId = 'ul.nav-tabs li'+' a[href="#' + $(this).attr('id') + '"]';
            $(tabId).tab('show');
            func(error);
        });
    }
}

function isMandatoryFields(fieldName) {
    if(fieldName){
        $("#" + fieldName).prop("required", true);
        $("#" + fieldName).addClass('mandatory');
        return true;
    }else{
        return true;
    }
}

function isNotMandatoryFields(fieldName) {
    if(fieldName){
        $("#" + fieldName).prop("required", false);
        $("#" + fieldName).removeClass('mandatory');
        return false;
    }else{
        return false;
    }
}

function isContainsInArrayList(fieldName, mandatoryFields) {
    return $.inArray(fieldName, mandatoryFields) > -1
}


var errors = $("a.alert-danger");
$.each(errors, function (i) {
    $(this).click(function () {
        focusOnField($(this).attr('errorElementId'), function (targetField$) {
            setTimeout(function () {
                targetField$.focus();
            }, 200);
        });
    });
});


function initDialogCustomStyle() {
    var infoMsgCtr = $("#messageDialog").find("span#infoMessage");
    console.log("infoMsgCtr" + infoMsgCtr)
    var msgsToCheck = [I18N['client.requestNumber']]
    if(typeof infoMsgCtr != "undefined") {
        var currentMsg = infoMsgCtr.text();
        var separators = ['\\,',':']
        jQuery.each( msgsToCheck, function( i, msg ) {
            if (currentMsg.indexOf(msg) >= 0) {
                var msgList = currentMsg.split(new RegExp(separators.join('|'), 'g'));
                console.log("msgList:" + msgList)
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