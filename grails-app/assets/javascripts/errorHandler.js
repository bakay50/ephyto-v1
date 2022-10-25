/**
 * Created by DEV23 on 4/27/2016.
 */
function initErrorHandler() {
    focusOnErrorField();
    removeFieldError();
}


function focusOnErrorField() {
    var formErrors = $('.errorContainer .alert-danger');
    $.each(formErrors, function () {
        $(this).click(function () {
            var hasInnerForm = $(this).hasClass("hasInnerForm");
            var errorElementId = $(this).attr('errorElementId');
            var array = errorElementId.split("_");
            var elemIdWithError = array[0];// get id of element with error
            var rank = array[1];// get rank
            var tab = array[2];//get tab
            var tabFld = array[3];//get tab
            var fieldWithSameId = elemIdWithError + tabFld;
            var attachedDocsArg = elemIdWithError + "_" + rank;
            var parentRank = getErrorParentRank(this, rank);

            if (hasInnerForm) {
                switchTab(null, tab);
            } else {
                switchTab(errorElementId, null);
            }
            var tabHeaderId = $("[id*='TabHeader'].active").attr("id");
            var elementToFocus = evaluateErrorElement(tabHeaderId, fieldWithSameId, elemIdWithError, errorElementId);
            focusField(elementToFocus);
        });
    });
}

function evaluateErrorElement(tabHeaderId, fieldWithSameId, errorElementFromSplit, errorElementFromAttr) {
    return errorElementFromSplit;
}

function hasOpenTab() {
    return ($("#itemFormCtr,#companyFormCtr").length > 0);
}

function switchTab(elemWithError, tabWithError) {
    // without inner form
    if (elemWithError) {
        // activate the tab pane where there error is and hide others
        var elemWithErrorId = "#" + elemWithError
        $(elemWithErrorId).parents('.tab-pane').siblings().removeClass('active', 'in');
        $(elemWithErrorId).parents('.tab-pane').addClass('active in');
        // activate the header of tab
        var id = $(elemWithErrorId).parents('.tab-pane').attr('id');
        if ((id != "undefined" || id != undefined) && elemWithError != "attachments") {
            $('#' + id + 'Header').addClass('active').siblings().removeAttr('class');
        }

    } else {
        // with inner form
        var tabId = "#" + tabWithError
        $(tabId).siblings().removeClass('active', 'in');
        $(tabId).addClass('active in');
        $(tabId + 'Header').addClass('active').siblings().removeAttr('class');
    }
}

function focusField(elemIdWithError) {
    if (elemIdWithError == "attachments") {
        var elts = "attachments";
        var tabId = 'ul.nav-tabs li'+' a[href="#' + elts + '"]';
        $(tabId).tab('show');
        setTimeout(function () {
            $('#addAttDocModal').focus();
        }, 500);
    } else if (elemIdWithError == "itemContractTraffics") {
        var elts = "rates";
        var tabId = 'ul.nav-tabs li'+' a[href="#' + elts + '"]';
        $(tabId).tab('show');
        setTimeout(function () {
            $('#addItemContractTrafficLink').focus();
        }, 500);
    }
    else {
        setTimeout(function () {
            $('#' + elemIdWithError).focus();
        }, 500);
    }
}

function enableAllErrorLinks() {
    var errLinkToDisable = $(".errorContainer ").find("a");
    jQuery.each(errLinkToDisable, function (i, field) {
        $(field).parent().removeClass("disableClick");
        $(field).parent().removeClass("btn-disabled");
    });
}


function removeFieldError() {
    var containers = ["ephytoGenTab"]
    jQuery.each(containers, function (i, field) {
        var mainContainer = $("#" + field);
        var allElems = $(mainContainer).find('.wfinput');
        removeError(allElems, "errorContainer");
    });
}


function removeError(allElems, containerClassName) {
    var errorContainer = $("." + containerClassName);
    $.each(allElems, function (i, field) {
        $(field).change(function () {
            var fieldId = $(this).attr('id');
            var fieldName = $(this).attr('name');
            var errorElement = errorContainer.find("a[errorelementid^=" + fieldName + "]")
            if (errorElement.length > 0) {
                $.each(errorElement, function (i, errElId) {
                    var instErrElId = $(errElId).attr("errorelementid")
                    if (instErrElId == fieldName) {
                        $(errElId).parent().remove()
                    } else {
                        var errClass = $(errElId).attr('class').split(/\s/).filter(function (className) {
                            return className.indexOf('parentRank_') >= 0 ? className : null
                        });
                        if (errClass.length > 0) {
                            var errorParentRank = errClass[0].split("_")[1]
                            var parentRankHidden = $(".parentRank").val()
                            if (parentRankHidden == errorParentRank) {
                                $(errElId).parent().remove()
                            }
                        } else {
                            var instErrElIdArr = instErrElId.split("_");
                            if (instErrElIdArr.length == 4) {
                                var hiddenRankField = $('input.' + instErrElIdArr[3] + '[type="hidden"]').val()
                                errorContainer.find("a[errorelementid^=" + fieldName + "_" + hiddenRankField + "]").parent().remove()
                            }
                        }
                    }
                });
                removeMainErrorContainer();
            }
        });
    });
}

function removeMainErrorContainer() {
    var errorContainersToRemove = ["subInnerErrorContainer", "innerErrorContainer", "mainErrorContainer","errorContainer"]
    $.each(errorContainersToRemove, function (i, ctr) {
        var errorContainer = $("." + ctr);
        if ($("." + ctr + " ul li").length == 0) {
            errorContainer.remove();
        }
    });
}

function removeAllTabErrors(elemStr, rank) {
    var errorElement = $(".errorContainer ").find("a[errorelementid*=" + elemStr + "]");
    $.each(errorElement, function (i, errElId) {
        $(errElId).parent().remove();
        removeMainErrorContainer();
    });
}

function removeTabWithChild(tabName, mainRank, tabFld) {
    var allErrorsInTab = $(".errorContainer ").find("a[errorelementid*=" + tabName + "]")
    var elId = mainRank + "_" + tabName + "_" + tabFld
    $.each(allErrorsInTab, function (i, errElement) {
        var errElementId = $(errElement).attr("errorelementid")
        if (errElementId.indexOf(elId) > 0 || $(errElement).hasClass("parentRank_" + mainRank)) {
            $(errElement).parent().remove();
            removeMainErrorContainer();
        }
    });
}

function getErrorParentRank(errorField, rank) {
    var errClass = $(errorField).attr('class').split(/\s/).filter(function (className) {
        return className.indexOf('parentRank_') >= 0 ? className : null
    });
    var parentRank = 0
    if (errClass.length > 0) {
        var errClassArr = errClass[0].split("_")
        parentRank = errClassArr[1]
    } else {
        if (rank != null) {
            parentRank = rank
        }
    }
    return parentRank
}

function validateFormFields(targetFields) {
    if (targetFields != null) {
        var valid = true;
        var hasFormatError = false;
        jQuery.each(targetFields, function (i, field) {
            var element = $(field);
            var elValue = element.val();
            if (elValue.length == 0) {
                if (element.is('select')) {
                    $(element).addClass('error-field-select');
                } else {
                    $(element).addClass('error-field');
                }
                valid = false;
            }
        });
        if (valid == false) {
            showValidationDialog(I18N['client.fillMandatoryFields']);
        } else if (!hasFormatError) {
            return true;
        }
    }
    clearFieldErrorsOnChange(targetFields);
}

function clearFieldErrorsOnChange(targetFields) {
    jQuery.each(targetFields, function (i, field) {
        $(field).change(function () {
            if ($.trim($(field).val()).length > 0) {
                clearFieldErrors(field)
            }
        });
    });
}

function clearFieldErrorsOnClick(targetFields) {
    if (targetFields != null) {
        jQuery.each(targetFields, function (i, field) {
            clearFieldErrors(field)
        });
    }
}

function clearFieldErrors(field) {
    $(field).removeClass('error-field');
    $(field).removeClass('error-field-select');
    $(field).removeClass('attErrorClass');
}


function updateRuleOnFields(liste_fields){
    $.each(liste_fields, function () {
        if(typeof $("#" + this).val() != "undefined"){
            isValidateElement.removeRuleFor(this);
            isValidateElement.addRuleFor(this);
        }
    });
}

function removeRuleOnFields(liste_fields){
    $.each(liste_fields, function () {
        if(typeof $("#" + this).val() != "undefined"){
            isValidateElement.removeRuleFor(this);
        }
    });
}

function disableOtherErrorLinks(currentTabId, currentRank, currentTabFld, currParentRank) {
    var errLinkToDisable = $(".errorContainer ").find("a[class*=alert-error]");
    jQuery.each( errLinkToDisable, function( i, field ) {
        var errorElementId = $(field).attr("errorelementid");
        var array = errorElementId.split("_");
        var rank = array[1];// get rank
        var tab = array[2];
        var tabFld = array[3];// get rank
        var parentRank= getErrorParentRank(this, rank);

        if ((tab == currentTabId && parentRank != currParentRank) || (tab != currentTabId)) {
            $(field).parent().addClass("disableClick");
            $(field).parent().addClass("btn-disabled");
        }
    });
}
function enableAllErrorLinks() {
    var errLinkToDisable = $(".errorContainer ").find("a");
    jQuery.each( errLinkToDisable, function( i, field ) {
        $(field).parent().removeClass("disableClick");
        $(field).parent().removeClass("btn-disabled");
    });
}
