/**
 * Created by DEV23 on 3/28/2016.
 */



var approveOperMandatoryFields = new Array("phytosanitaryCertificateRef", "phytosanitaryCertificateDate", "filingDate", "meansOfTransport", "consigneeNameAddress","dockingPermissionDate")
var storeAndUpdateOperMandatoryFields = new Array("exporterCode", "consigneeNameAddress", "commodityCode", "productTypeCode");
var requestAndUpdateOperMandatoryFields = new Array("userReference", "dockingPermissionRef", "dockingPermissionDate", "customsClearanceOfficeCode", "declarationSerial", "declarationDate", "declarationNumber", "modeOfTransportCode", "meansOfTransport", "boardingDate", "countryOfDestinationCode", "placeOfUnloadingCode", "exporterCode", "consigneeNameAddress", "declarantCode", "applicantName", "applicantTelephone", "applicantEmail", "warehouse", "applicatorCode", "disinfectionCertificateRef", "disinfectionCertificateDate", "treatmentType", "treatmentSartDate", "treatmentSartTime", "treatmentEndDate", "treatmentEndTime", "treatmentDuration",
    "usedProducts", "concentration", "commodityCode", "productTypeCode", "harvest", "season", "countryOfOriginCode", "packageMarks", "packageCode");
var replaceOperMandatoryFields = new Array("phytosanitaryCertificateRef", "phytosanitaryCertificateDate", "filingDate", "userReference", "dockingPermissionRef", "dockingPermissionDate", "customsClearanceOfficeCode", "declarationSerial", "declarationDate", "declarationNumber", "modeOfTransportCode", "meansOfTransport", "boardingDate", "countryOfDestinationCode", "placeOfUnloadingCode", "exporterCode", "consigneeNameAddress", "declarantCode", "applicantName", "applicantTelephone", "applicantEmail", "warehouse", "applicatorCode", "disinfectionCertificateRef", "disinfectionCertificateDate", "treatmentType", "treatmentSartDate", "treatmentSartTime", "treatmentEndDate", "treatmentEndTime", "treatmentDuration",
    "usedProducts", "concentration", "commodityCode", "productTypeCode", "harvest", "season", "countryOfOriginCode", "packageMarks", "packageCode");
var registerOperMandatoryFields = new Array("code", "agreement", "nameAddress");

var queryAndRejectOperMandatoryFields = new Array("message");
var exceptionGroups = {
    declaraction: "declarationSerial declarationNumber",
    heureDebut: "treatmentSartDate",
    heureFin: "treatmentEndDate"
}

var firstGroups = {
    declaraction: "declarationSerial declarationNumber",
    heureDebut: "treatmentSartDate treatmentSartTime",
    heureFin: "treatmentEndDate treatmentEndTime"
}

var exceptionProducts = new Array(I18N['client.FRUIT'], I18N['client.BOIS,DIVERS']);
var exceptionForTime = new Array(I18N['client.BOIS'], I18N['client.DIVERS']);
var optionalFieldForTime = new Array("disinfectionCertificateRef", "disinfectionCertificateDate");

function initJqueryValidationEphyto() {
    $.validator.setDefaults({
        errorClass: 'form_error',
        errorElement: 'label'
    });
    $.extend(jQuery.validator.messages,
        {
            email: I18N['client.EMAIL_MSG'],
            required: I18N['client.required']
        })
    addValidatorMethods();
    var validator = validateEphytoForm();
    overrideFormatNumberValidation();
    var form = $("#appMainForm");
    var btns = form.find("[id^='operation'][class~='btn']");
    btns.mousedown(function (e) {
        $("#tempOper").val($(this).val());
        console.log("tempOper :" + $("#tempOper").val())
        updateCurrentOperation(this);
        form.validate();
    });
}

function validateEphytoForm() {
    $("#appMainForm").validate({
        invalidHandler: function (form, validator) {
            var elements = validator.invalidElements();
            if (elements) {
                var firstElement$ = $(elements[0]);
                if (firstElement$.is(':hidden')) {
                    focusOnField(firstElement$.attr('id'), function (targetField$) {
                        setTimeout(function () {
                            targetField$.focus();
                        }, 200);
                    });
                }
            }
        },
        ignore: [],
        rules: {
            userReference: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('userReference')
                    }
                },
                maxlength: 30
            },
            exporterCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('exporterCode')
                    }
                },
                maxlength: 17
            },
            realExportNameAddress: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('realExportNameAddress')
                    }
                }

            },

            consigneeNameAddress: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('consigneeNameAddress')
                    }
                }
            },
            commodityCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('commodityCode')
                    }
                },
                maxlength: 10
            },
            productTypeCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('productTypeCode')
                    }
                },
                maxlength: 30
            },
            dockingPermissionRef: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('dockingPermissionRef')
                    }
                },
                maxlength: 30
            },
            dockingPermissionDate: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('dockingPermissionDate')
                    }
                }
            },
            customsClearanceOfficeCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('customsClearanceOfficeCode')
                    }
                },
                maxlength: 5
            },
            declarationSerial: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('declarationSerial')
                    }
                },
                maxlength: 1
            },
            declarationDate: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('declarationDate')
                    }
                }
            },
            declarationNumber: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('declarationNumber')
                    }
                }
            },
            modeOfTransportCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('modeOfTransportCode')
                    }
                },
                maxlength: 3
            },
            meansOfTransport: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('meansOfTransport')
                    }
                },
                maxlength: 35
            },
            code: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('code')
                    }
                },
                maxlength: 10
            },
            agreement: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('agreement')
                    }
                },
                maxlength: 20
            },
            nameAddress: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('nameAddress')
                    }
                }
            },
            observations: {
                maxlength: 1000
            },
            boardingDate: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('boardingDate')
                    }
                }
            },
            countryOfDestinationCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('countryOfDestinationCode')
                    }
                },
                maxlength: 2
            },
            placeOfUnloadingCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('placeOfUnloadingCode')
                    }
                },
                maxlength: 5
            },
            declarantCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('declarantCode')
                    }
                },
                maxlength: 17
            },
            applicantName: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('applicantName')
                    }
                },
                maxlength: 35
            },
            applicantTelephone: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('applicantTelephone')
                    }
                }
                ,
                maxlength: 17
            },
            applicantEmail: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('applicantEmail')
                    }
                },
                email: true,
                maxlength: 128
            },
            warehouse: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('warehouse')
                    }
                },
                maxlength: 35
            },
            applicatorCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('applicatorCode')
                    }
                },
                maxlength: 10
            },
            disinfectionCertificateRef: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('disinfectionCertificateRef')
                    }
                },
                maxlength: 1000
            },
            disinfectionCertificateDate: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('disinfectionCertificateDate')
                    }
                }
            },
            treatmentType: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('treatmentType')
                    }
                },
                maxlength: 35
            },
            treatmentSartDate: {
                required: {
                    depends: function () {
                        if ($("#productTypeCode").val().trim() === I18N['client.DIVERS']) {
                            return allowedCheckingDateAndTime();
                        } else if ($("#productTypeCode").val().trim() === I18N['client.BOIS']) {
                            return false;
                        } else if ($("#productTypeCode").val().trim() === I18N['client.FRUIT']) {
                            return allowedCheckingDateAndTime();
                        } else if ($("#productTypeCode").val().trim() === I18N['client.MANGUE']) {
                            return false;
                        }
                        else {
                            return isMandatoryForOperation('treatmentSartDate')
                        }

                    }
                }
            },
            treatmentSartTime: {
                required: {
                    depends: function () {
                        if ($("#productTypeCode").val().trim() === I18N['client.FRUIT']) {
                            return allowedCheckingDateAndTime();
                        } else if ($.inArray($("#productTypeCode").val().trim(), exceptionForTime) > -1) {
                            return false;
                        }
                        else {
                            return isMandatoryForOperation('treatmentSartTime')
                        }
                    }
                },
                timeRegex: true
            },
            treatmentEndDate: {
                required: {
                    depends: function () {
                        if ($("#productTypeCode").val().trim() === I18N['client.DIVERS']) {
                            return allowedCheckingDateAndTime();
                        } else if ($("#productTypeCode").val().trim() === I18N['client.BOIS']) {
                            return false;
                        } else if ($("#productTypeCode").val().trim() === I18N['client.FRUIT']) {
                            return allowedCheckingDateAndTime();
                        }
                        else {
                            return isMandatoryForOperation('treatmentEndDate')
                        }

                    }
                }
            },
            treatmentEndTime: {
                required: {
                    depends: function () {
                        if ($("#productTypeCode").val().trim() === I18N['client.FRUIT']) {
                            return allowedCheckingDateAndTime();
                        } else if ($.inArray($("#productTypeCode").val().trim(), exceptionForTime) > -1) {
                            return false;
                        }
                        else {
                            return isMandatoryForOperation('treatmentEndTime')
                        }
                    }
                },
                timeRegex: true
            },
            treatmentDuration: {
                required: {
                    depends: function () {
                        if ($("#productTypeCode").val().trim() === I18N['client.BOIS']) {
                            return false;
                        } else {
                            return isMandatoryForOperation('treatmentDuration')

                        }
                    }
                },
                maxlength: 10,
                commaSeparatorNumber: true
            },
            usedProducts: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('usedProducts')
                    }
                }
            },
            concentration: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('concentration')
                    }
                }
            },
            harvest: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('harvest')
                    }
                },
                maxlength: 100
            },
            season: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('season')
                    }
                },
                maxlength: 100
            },
            countryOfOriginCode: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('countryOfOriginCode')
                    }
                },
                maxlength: 2
            },
            packageMarks: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('packageMarks')
                    }
                },
                maxlength: 50
            },
            packageCode: {
                required: {
                    depends: function () {
                        if ($("#productTypeCode").val().trim() === I18N['client.BOIS']) {
                            return false;
                        } else {
                            return isMandatoryForOperation('packageCode')
                        }
                    }
                },
                maxlength: 17
            },

            phytosanitaryCertificateRef: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('phytosanitaryCertificateRef')
                    }
                }
            },
            phytosanitaryCertificateDate: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('phytosanitaryCertificateDate')
                    }
                }
            },
            filingDate: {
                required: {
                    depends: function () {
                        return isMandatoryForOperation('filingDate')
                    }
                }
            },
            // goods fields
            quantity: {
                required: true
            },
            netWeight: {
                required: true
            },
            grossWeight: {
                required: true
            },
            volume: {
                required: {
                    depends: function () {
                        if ($("#productTypeCode").val().trim() === I18N['client.BOIS']) {
                            return isVolumeFieldsMandatory('volume');
                        } else {
                            return isNotVolumeFieldsMandatory('volume');
                        }
                    }
                }
            },
            treatment: {
                required: {
                    depends: function () {
                        var product = $("#productTypeCode").val().trim();
                        if (product && $.inArray(product, exceptionProducts) > -1) {
                            return isMandatoryFields('treatment');
                        } else {
                            return isNotMandatoryFields('treatment');
                        }
                    }
                }
            },
            botanicalName: {
                required: {
                    depends: function () {
                        return isBotanicalMandatoryForApprove('botanicalName')
                    }
                }
            },
            message: {
                required: {
                    depends: function () {
                        return isMandatoryForQueryAAndReject('message')
                    }
                },
                maxlength: 1024
            }


        },
        groups: {
            declaraction: "declarationSerial declarationNumber",
            heureDebut: "treatmentSartDate treatmentSartTime",
            heureFin: "treatmentEndDate treatmentEndTime"
        },

        messages: {}
        ,

        errorPlacement: function (error, element) {
            var fieldName = $(element).attr("name");
            if (fieldName == "treatmentSartDate" || fieldName == "treatmentSartTime") {
                error.insertAfter("#treatmentSartTime");
                $('label.form_error[for="' + fieldName + '"]').css({"margin-right": "-800px"})
            } else if (fieldName == "treatmentEndDate" || fieldName == "treatmentEndTime") {
                error.insertAfter("#treatmentEndTime");
                $('label.form_error[for="' + fieldName + '"]').css({"margin-right": "-800px"})
            } else if (fieldName == "treatmentDuration") {
                //error.insertAfter("#addtreatmentDuration");
                //error.insertAfter("#tempProtection");
                error.insertAfter(element);
                //$('label.form_error[for="' + fieldName + '"]').css({"margin-right": "-800px"})
            }
            else if (fieldName == "consigneeNameAddress") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "270px")
            } else if (fieldName == "declarationSerial" || fieldName == "declarationNumber") {
                error.insertAfter("#declarationNumber");
                $('label.form_error[for="' + fieldName + '"]').css({"margin-right": "-800px"})
            } else if (fieldName == "packageMarks") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "200px")
            }
            else if (fieldName == "agreement") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "200px")
            }
            else if (fieldName == "code") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "200px")
            }
            else if (fieldName == "disinfectionCertificateRef") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "270px")
            }
            else if (fieldName == "harvest") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "200px")
            }
            else if (fieldName == "season") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "200px")
            }
            else if (fieldName == "userReference") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "270px")
            } else if (fieldName == "volume") {
                error.insertAfter("#volumeLabel");
                $('label.form_error[for="' + fieldName + '"]').css({"margin-right": "-850px"})
            } else if (fieldName == "treatment") {
                error.insertAfter("#" + fieldName);
                var field = $("#" + fieldName + "-" + "error");
                field.css("margin-right", "360px")
            }
            else {
                error.insertAfter(element);
            }

        },

        showErrors: function (errorMap, errorList) {
            for (var i = 0; errorList[i]; i++) {
                var element = this.errorList[i].element;
                this.errorsFor(element).remove();
            }
            this.defaultShowErrors();
        }
    });
}


function isMandatoryForQueryAAndReject(fieldname) {
    var buttonsToDisable = $("#appMainForm").find("[id^='operation'][class~='.btn']");
    enableOperationButtons(buttonsToDisable);
    var operationName = $("#tempOper").val();
    console.log("operationName For Query Or Reject :" + operationName);
    if ((operationName.toUpperCase() === (I18N['client.OP_QUERY']).toUpperCase()) || (operationName.toUpperCase() === (I18N['client.OP_REJECT']).toUpperCase() || (I18N['client.OP_REGISTER']).toUpperCase())) {
        return true //$.inArray(fieldname, queryAndRejectOperMandatoryFields) > -1;
    } else {
        return false
    }
}

function isBotanicalMandatoryForApprove(fieldname) {
    var operationName = $("#tempOper").val().trim();
    var modeOfTransportCode = $("#modeOfTransportCode").val().trim();
    var productTypeCode = $("#productTypeCode").val().trim();
    if (modeOfTransportCode === "4" && operationName === I18N['client.OP_APPROVE']) {
        return isMandatoryFields(fieldname);
    } else {
        return isNotMandatoryFields(fieldname);
    }
}

function isMandatoryForOperation(fieldname) {
    var buttonsToDisable = $("#appMainForm").find("[id^='operation'][class~='.btn']");
    var StoreUpdateOperation = new Array(I18N['client.OP_STORE'], I18N['client.OP_UPDATE']);
    var approveRequestOperation = new Array(I18N['client.OP_APPROVE']);
    var fieldsListForMango = new Array("harvest", "season");
    var MANGO = "MANGUE"
    enableOperationButtons(buttonsToDisable);
    var operationName = $("#tempOper").val();
    var status_doc = $("#status_doc").val();
    var product = $("#productTypeCode").val();
    var selectedvalue = $('select#treatment').val();

    if (product === MANGO && ($.inArray(fieldname, fieldsListForMango) > -1)) {
        return false
    } else if (allowedExceptionCheckingForProduct(selectedvalue, product)) {
        return checkFieldsForExceptionProduct(product, operationName, status_doc, StoreUpdateOperation, approveRequestOperation, fieldname, selectedvalue);
    } else {
        return updateMandatoryFields(operationName, status_doc, StoreUpdateOperation, approveRequestOperation, fieldname);
    }
}

function isGeneralEphytoFieldsMandatory(fieldName) {
    if ($("#" + fieldName).val()) {
        return isNotMandatoryFields(fieldName);
    } else {
        return isMandatoryFields(fieldName);
    }
}

function enableOperationButtons(buttonsToDisable) {
    $.each(buttonsToDisable, function () {
        $(this).removeAttr('disabled');
    });
}

function addValidatorMethods() {
    var lang = $("#locale").val()
    $.validator.addMethod(
        "timeRegex",
        function (value, element) {
            if ($(element).attr("name") == "treatmentSartTime" || $(element).attr("name") == "treatmentEndTime") {
                if (value && value != "" && value != '__:__:__') {
                    return value.match(/^([0-1]?[0-9]|2[0-3]):([0-5][0-9])(:[0-5][0-9])?$/)
                }
                else if (value === "" || value === '__:__:__') {
                    return true;
                }
            }

        },
        lang == 'fr' || lang == 'fr_FR' ? "Format invalide de l'heure. Exemple : (hh:mm:ss)" : "Invalid time format. example: (hh:mm:ss)")
        , $.validator.addMethod("dateComparison", function (value, element) {
        var treatmentEndDate = $("#treatmentEndDate").val();
        var treatmentStartDate = $("#treatmentStartDate").val();
        if (treatmentEndDate && treatmentStartDate) {
            if (parseDate(treatmentStartDate) <= parseDate(treatmentEndDate)) {
                return false
            }
            return true
        } else {
            return false
        }
    }, lang == 'fr' || lang == 'fr_FR' ? "La fin du traitement ne peut être antérieur au début du traitement " : " The end of the treatment can not be prior to the beginning of treatment ")


}


function parseDate(input) {
    var parts = input.match(/(\d+)/g);
    return new Date(parts[2], parts[1] - 1, parts[0]); // months are 0-based
}


function checkFieldsForExceptionProduct(product, operationName, status_doc, StoreUpdateOperation, approveRequestOperation, fieldname, selectedvalue) {
    var headerFieldsException = new Array();
    var treatmentFieldsException = new Array();
    var goodFieldsException = new Array();
    switch (product) {
        case I18N['client.FRUIT']:
            headerFieldsException.push("dockingPermissionRef", "dockingPermissionDate");
            treatmentFieldsException.push("warehouse", "applicatorCode", "disinfectionCertificateRef", "disinfectionCertificateDate", "treatmentType", "treatmentSartDate", "treatmentSartTime", "treatmentEndDate", "treatmentEndTime", "usedProducts", "concentration", "treatmentDuration");
            goodFieldsException.push("harvest", "season");
            return updateFieldsToExclude(fieldname, headerFieldsException, treatmentFieldsException, goodFieldsException, operationName, status_doc, StoreUpdateOperation, approveRequestOperation, product, selectedvalue);
        case I18N['client.BOIS']:
            headerFieldsException.push("dockingPermissionRef", "dockingPermissionDate");
            goodFieldsException.push("harvest", "season");
            if (selectedvalue === I18N['client.YES']) {
                treatmentFieldsException.push("warehouse", "applicatorCode", "treatmentSartDate", "treatmentSartTime", "treatmentEndDate", "treatmentEndTime");
            } else {
                treatmentFieldsException.push("warehouse", "applicatorCode", "disinfectionCertificateRef", "disinfectionCertificateDate", "treatmentType", "treatmentSartDate", "treatmentSartTime", "treatmentEndDate", "treatmentEndTime", "usedProducts", "concentration", "treatmentDuration");
            }
            return updateFieldsToExclude(fieldname, headerFieldsException, treatmentFieldsException, goodFieldsException, operationName, status_doc, StoreUpdateOperation, approveRequestOperation, product, selectedvalue);
        case I18N['client.DIVERS']:
            goodFieldsException.push("harvest", "season");
            if (selectedvalue === I18N['client.YES']) {
                treatmentFieldsException.push("warehouse", "applicatorCode", "treatmentSartTime", "treatmentEndTime");
            } else {
                treatmentFieldsException.push("warehouse", "applicatorCode", "disinfectionCertificateRef", "disinfectionCertificateDate", "treatmentType", "treatmentSartDate", "treatmentSartTime", "treatmentEndDate", "treatmentEndTime", "usedProducts", "concentration", "treatmentDuration");
            }
            return updateFieldsToExclude(fieldname, headerFieldsException, treatmentFieldsException, goodFieldsException, operationName, status_doc, StoreUpdateOperation, approveRequestOperation, product, selectedvalue);
        default:
            return updateMandatoryFields(operationName, status_doc, StoreUpdateOperation, approveRequestOperation, fieldname);
            break
    }

}

function updateFieldsToExclude(fieldname, headerFields, treatmentFields, goodFields, operationName, status_doc, StoreUpdateOperation, approveRequestOperation, product, selectedvalue) {
    var exceptFields = new Array("treatmentSartDate", "treatmentEndDate")
    if ((checkIfEmptyArray(headerFields) && $.inArray(fieldname, headerFields) > -1) || (checkIfEmptyArray(treatmentFields) && $.inArray(fieldname, treatmentFields) > -1) || (checkIfEmptyArray(goodFields) && $.inArray(fieldname, goodFields) > -1)) {
        isValidateElement.removeRuleFor(fieldname);
        return false;
    } else if (product === I18N['client.DIVERS'] && selectedvalue === I18N['client.YES'] && $.inArray(fieldname, exceptFields) > -1) {
        isValidateElement.removeRuleFor(fieldname);
        isValidateElement.addRuleFor(fieldname);
        return true;
    } else if (selectedvalue == I18N['client.YES'] && product == I18N['client.BOIS'] && ($.inArray(fieldname, optionalFieldForTime) > -1)) {
        isValidateElement.removeRuleFor(fieldname);
        return false;
    } else {
        return updateMandatoryFields(operationName, status_doc, StoreUpdateOperation, approveRequestOperation, fieldname);
    }
}

function checkIfEmptyArray(arrayFields) {
    if (typeof arrayFields === 'undefined') {
        return false
    } else if (arrayFields.length > 0) {
        return true
    }
}

function removeFromArray(arrayToReduce, elementsToRemove) {
    elementsToRemove.forEach(function (element) {
            arrayToReduce.indexOf(element) !== -1 && arrayToReduce.splice(arrayToReduce.indexOf(element), 1)
        }
    )
    return arrayToReduce
}

function addToArray(initialArray, elementsToAdd) {
    elementsToAdd.forEach(function (element) {
            initialArray.indexOf(element) === -1 && initialArray.push(element)
        }
    )
    return initialArray
}

function addDockingPermissionFieldsForCreate(requiredFieldsOrNot) {
    var startedOperation = $("#startedOperation").val();
    var CREATE_OPERATION = "Create"
    if (startedOperation == CREATE_OPERATION) {
        requiredFieldsOrNot.push("dockingPermissionDate", "dockingPermissionRef");
    }
}

function updateMandatoryFields(operationName, status_doc, StoreUpdateOperation, approveRequestOperation, fieldname) {
    var modeOfTransportCode
    if($("#modeOfTransportCode").val()){
        modeOfTransportCode = $("#modeOfTransportCode").val().trim()
    }
    var requiredFieldsOrNot = new Array("declarationSerial", "declarationDate", "declarationNumber");
    if (modeOfTransportCode === "4") {
        addDockingPermissionFieldsForCreate(requiredFieldsOrNot)
        requestAndUpdateOperMandatoryFields = removeFromArray(requestAndUpdateOperMandatoryFields, requiredFieldsOrNot);
        replaceOperMandatoryFields = removeFromArray(replaceOperMandatoryFields, requiredFieldsOrNot);
    } else {
        addDockingPermissionFieldsForCreate(requiredFieldsOrNot)
        requestAndUpdateOperMandatoryFields = addToArray(requestAndUpdateOperMandatoryFields, requiredFieldsOrNot);
        replaceOperMandatoryFields = addToArray(replaceOperMandatoryFields, requiredFieldsOrNot);
    }
    if (operationName !== I18N['client.OP_REPLACE'] && (!status_doc || status_doc == I18N['client.ST_STORED']) && $.inArray(operationName, StoreUpdateOperation) > -1) {
        if ($.inArray(fieldname, storeAndUpdateOperMandatoryFields) > -1) {
            return isMandatoryFields(fieldname);
        } else {
            return isNotMandatoryFields(fieldname);
        }
    } else if (operationName !== I18N['client.OP_REPLACE'] && (status_doc == I18N['client.ST_REQUESTED'] && $.inArray(operationName, approveRequestOperation) > -1)) {
        if ($.inArray(fieldname, approveOperMandatoryFields) > -1) {
            return isMandatoryFields(fieldname);
        } else {
            return isNotMandatoryFields(fieldname);
        }
    }
    else {
        if (operationName === I18N['client.OP_DELETE'] || (status_doc == I18N['client.ST_REQUESTED'] && (operationName === I18N['client.OP_REJECT'] || operationName === I18N['client.OP_QUERY'] || operationName === I18N['client.OP_REGISTER']))) {
            return false;
        } else {
            switch (operationName) {
                case I18N['client.OP_QUERY']:
                case I18N['client.OP_UPDATE']:
                case I18N['client.OP_REQUEST']:
                    if ($.inArray(fieldname, requestAndUpdateOperMandatoryFields) > -1) {
                        return isMandatoryFields(fieldname);
                    } else {
                        return isNotMandatoryFields(fieldname);
                    }
                    break;
                case I18N['client.OP_REPLACE']:
                    if ($.inArray(fieldname, replaceOperMandatoryFields) > -1) {
                        return isMandatoryFields(fieldname);
                    } else {
                        return isNotMandatoryFields(fieldname);
                    }
                    break;
                case I18N['client.OP_REGISTER']:
                    if ($.inArray(fieldname, registerOperMandatoryFields) > -1) {
                        return isMandatoryFields(fieldname);
                    } else {
                        return isNotMandatoryFields(fieldname);
                    }
                    break;
                default:
                    if ($.inArray(fieldname, requestAndUpdateOperMandatoryFields) > -1) {
                        console.log("Operation Fields is :" + operationName);
                        console.log("Mandatory Fields is :" + fieldname);
                        return isMandatoryFields(fieldname);
                    } else {
                        return isNotMandatoryFields(fieldname);
                    }
                    break
            }
        }

    }
}

function allowedCheckingDateAndTime() {
    var selectedvalue = $('select#treatment').val().trim();
    if (selectedvalue === I18N['client.YES']) {
        return true;
    }
    return false;
}

function allowedExceptionCheckingForProduct(selectedvalue, product) {
    if ((product == I18N['client.FRUIT'] && selectedvalue === I18N['client.NO']) || ($.inArray(product, exceptionForTime) > -1)) {
        return true;
    } else {
        return false;
    }
}

var exceptFields = new Array("CACAO", "CAFE");

function updateRuleOfFields(element) {
    setBotanicalFielSize();
    var product = $(element).val();
    if (product) {
        if ($.inArray(product, exceptFields) > -1) {
            initializeSelectedValue();
            isValidateElement.removeRuleFor("treatment");
        } else {
            isValidateElement.removeRuleFor("treatment");
            isValidateElement.addRuleFor("treatment");
        }
    } else {
        initializeSelectedValue();
        isValidateElement.removeRuleFor("treatment");
    }
}

function initializeSelectedValue() {
    $("#treatment").val("");
}

function updateTreamentType(element) {
    var product = $("#productTypeCode").val();
    if ($.inArray(product, exceptFields) > -1 && $(element).val()) {
        initializeSelectedValue();
    }
}

function updateCurrentOperation(element) {
    console.log("currentCommitOperation id" + element.id)
    console.log("currentCommitOperation name" + element.name)
    $("#currentCommitOperation").val(element.id);
    $("#currentCommitOperationValue").val(element.name);
}

function isVolumeFieldsMandatory(fieldName) {
    if (fieldName) {
        $("#" + fieldName).prop("required", true);
        $("#" + fieldName).addClass('mandatory');
        return true;
    } else {
        return false;
    }
}

function isNotVolumeFieldsMandatory(fieldName) {
    if (fieldName) {
        $("#" + fieldName).prop("required", false);
        $("#" + fieldName).removeClass('mandatory');
        return false;
    } else {
        return false;
    }
}