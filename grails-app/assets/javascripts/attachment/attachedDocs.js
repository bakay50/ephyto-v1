/**
 * Created by DEV23 on 3/30/2016.
 */
var isUploadStarted = false;
var isEnable = true;

/*
 $(document).ready(function () {
 if ($("#uploadDialog").attr("id") !== undefined) {
 initAttDoc();
 }
 });*/

function initAttDoc() {
    resetAttData();
    makeAutocomplete("docType", "HT_ATT", "description", attDocCodeSetter, "250");
    if ($('#docDate').length > 0) {
        if ($('#docDate').hasClass('generate-datepicker')) {
            makeDatepicker($('#docDate'));
        }
    }

    $('#docDate').datetimepicker({
        widgetPositioning: {
            horizontal: 'right',
            vertical: 'bottom'
        }
    });

    $("#addAttDoc").click(function () {
        addAttachedDoc();
    });

    $(".attachmentTable .wfinput").change(function () {
        validateAttDocs(false);
    });

    $("#docType").focusout(function () {
        if ($("#docType").val() == '') {
            $("#docCode").val('');
            //  $("#addUploadFile").addClass('disabled');
            $("#addUploadFile").addClass('disableClick');
            $("#addUploadFile").addClass('btn-disabled');
        } else if ($("#currentEditableAttDocNumber").val() == "") {
            // $("#addUploadFile").removeClass('disabled');
            $("#addUploadFile").removeClass('disableClick');
            $("#addUploadFile").removeClass('btn-disabled');
        }
    });

    initAttUploadAjaxForm();
}

function resetAttData() {
    $("#docType").val("");
    $("#docRef").val("");
    $("#docDate").val("");
    $("#fileExtension").val("");
    $(".attachmentTable td:eq(5)").html($("#attDocTr").html());
    $("#attDoc").val("");
    // $("#addUploadFile").addClass('disabled');
    $("#addUploadFile").addClass('disableClick');
    $("#addUploadFile").addClass('btn-disabled');
}

function removeAttDoc(elemId) {
    var arr = elemId.split('_');
    var attDocNumber = Number(arr[1]);
    clearFieldErrorsOnClick(getAttachmentFields())
    openConfirmDialog(I18N['client.deleteAttachmentConfirm'],
        function () {
            $("#del_" + attDocNumber).attr("disabled");
            if (isEnable == true) {
                isEnable = false;
                $.ajax({
                    type: "POST",
                    data: {
                        attNumber: attDocNumber
                    },
                    url: $("#removeAttURL").val(),

                    success: function (data, textStatus) {
                        $("#attachmentTableBody").html(data);
                        if ($('#docDate').hasClass('generate-datepicker')) {
                            makeDatepicker($('#docDate'));
                        }
                        isEnable = true;
                        var elStr = attDocNumber + "_attachmentsTab"
                        removeAllTabErrors("attachmentsTab")
                    }
                });
            }
        }, // no button handler
        function () {
        }
    )
}

function validateAttDocs(btnTriggered) {
    var docTyp = $("#docType").val();
    var docRef = $("#docRef").val();
    var docDat = $("#docDate").val();
    var flag = "valid";
    var reg = new RegExp("(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])/20\\d\\d$");
    if (docRef.length == 0) {
        $("#docRef").addClass('attErrorClass');
        flag = "invalid";
    }

    if (docTyp.length == 0) {
        $("#docType").addClass('attErrorClass');
        flag = "invalid";
    }

    if (docDat.length == 0 || !reg.test(docDat)) {
        $("#docDate").addClass('attErrorClass');
        flag = "invalid";
    }

    if (flag != "invalid") {
        clearErrors();
        return true;
    } else {
        if (btnTriggered == true) {
            showValidationDialog(I18N['client.fillMandatoryFields'])
        }
    }
}

function clearErrors() {
    $("#docType").removeClass('attErrorClass');
    $("#docRef").removeClass('attErrorClass');
    $("#docDate").removeClass('attErrorClass');
}

function hideErrorsDiv() {
    $("#attachedErrors").html("<div></div>");
}

function addAttachedDoc() {
    var docTyp = $("#docType").val();
    var docRef = $("#docRef").val();
    var docDat = $("#docDate").val();
    var attNum = Number($("#attachments").find(".attRow").length + 1);
    var fileExt = $("#fileExtension").val();
    if (validateAttDocs(true)) {
        $("#addAttDoc").attr('disabled', 'disabled');
        if (isEnable == true) {
            isEnable = false;
            $.ajax({
                type: 'POST',
                data: {
                    docType: docTyp,
                    docRef: docRef,
                    docDate: docDat,
                    attNumber: attNum,
                    fileExtension: fileExt
                },
                url: $("#addAttURL").val(),
                success: function (data, textStatus) {
                    if (data.indexOf("Error") != -1) {
                        $("#alertInfo").empty()
                        $("#attachedErrors").html(data);
                    } else {
                        hideErrorsDiv();
                        $("#attachmentTableBody").html(data);
                        resetAttData();
                        displayAttDocAddAlertMessage();
                    }
                    clearAttErrorMessage();
                    if ($('#docDate').hasClass('generate-datepicker')) {
                        makeDatepicker($('#docDate'));
                    }
                    // $("#addAttDoc").removeAttr('disabled');
                    $("#addAttDoc").removeAttr('btn-disabled');
                    $("#addAttDoc").removeAttr('disableClick');
                    enableAttachDocForm();
                    isEnable = true;
                }
            });
        }
    }
}

function clearAttErrorMessage() {
    var errorContainer = $(".errorContainer");
    errorContainer.find("a[errorelementid='attachments']").parent().remove();
    if ($(".errorContainer ul li").length == 0) {
        errorContainer.remove();
    }
}

function attDocCodeSetter(sourceId, item) {
    var d = $("#docCode");
    d.val(item ? item['code'] : '');
}

function attDocModalCodeSetter(sourceId, item) {
    if(item){
        $("#docCode").val(item["code"])
        $("#docType").val(item["description"])
    }
}

function editAttDocCodeSetter(sourceId, item) {
    var d = $("#hidDocCode");
    d.val(item ? item['code'] : '');
}

function editAttDoc(elemId) {
    if ($("#currentEditableAttDocNumber").val() == "") {
        var buttonsToDisable = $("#appMainForm").find("[name^='operation'][class~='btn']");
        var AdditionnalButtonsToDisable = [".xmlImportButton", ".exportXML", ".Close", ".print", "#delete"]
        $("#currentEditableAttDocNumber").val(elemId);

        disableAttachDocForm();
        configureFormFields(buttonsToDisable, true);
        configureFormFields(AdditionnalButtonsToDisable, true);
        clearFieldErrorsOnClick(getAttachmentFields())
        var arr = elemId.split('_');
        var attDocNumber = Number(arr[1]);
        var editedRow = $("#editHeader").clone(true);
        var docType = $.trim($("#docType_" + attDocNumber).text());
        var docCode = $("#docCode_" + attDocNumber).text();
        var docRef = $("#docRef_" + attDocNumber).text();
        var docDate = $("#docDate_" + attDocNumber).text();

        $(".attachmentRow_" + attDocNumber).replaceWith(editedRow);
        $("#edDocDate").datepicker({
            dateFormat: "dd/mm/yy",
            maxDate: 0,
            changeMonth: true,
            changeYear: true
        });
        $("#edDocDate").val(docDate);
        $("#hidDocDate").val(docDate);
        $("#edDocType").val(docType);
        $("#edDocRef").val(docRef);
        makeAutocomplete("edDocType", "HT_ATT", "description", editAttDocCodeSetter, "250");
        $("#hidDocType").val(docType);
        $("#hidDocCode").val(docCode);
        $("#hidDocRef").val(docRef);
        $("#hidDocRank").val(attDocNumber);

        $("#saveAtt").click(function () {
            docType = $("#edDocType").val();
            docRef = $("#edDocRef").val();
            docDate = $("#edDocDate").val();
            fileExt = $("#fileExtension").val();
            var allFieldsAreFilled = docType.length > 0 && docRef.length > 0;
            allFieldsAreFilled = allFieldsAreFilled && (docDate.length > 0);
            var fieldsToConfigure = ["#edDocType", "#edDocRef", "#edDocDate"]
            if (validateFormFields(fieldsToConfigure)) {
                if (isEnable == true) {
                    isEnable = false;
                    $.ajax({
                        type: 'POST',
                        data: {
                            docType: docType,
                            docRef: docRef,
                            docDate: docDate,
                            attNumber: attDocNumber,
                            fileExtension: fileExt
                        },
                        url: $("#updateAttURL").val(),

                        success: function (data, textStatus) {
                            if (data.indexOf("alert-error errorContainer") != -1) {
                                $("#attachedErrors").html(data);
                            } else {
                                $("#attachedErrors").html("<div></div>");
                                $("#attachmentTableBody").html(data);
                                if ($('#docDate').hasClass('generate-datepicker')) {
                                    makeDatepicker($('#docDate'));
                                }
                                resetAttData();
                                displayAttDocSaveAlertMessage();
                                enableAllErrorLinks();
                            }
                            clearAttErrorMessage();
                            enableAttachDocForm();
                            configureFormFields(buttonsToDisable, false);
                            configureFormFields(AdditionnalButtonsToDisable, false);
                            removeFieldError();
                            isEnable = true;
                        }
                    });
                }
            }
        });
        disableOtherErrorLinks("attachmentsTab", null, "attFld", attDocNumber);
    }
}

function displayAttDocSaveAlertMessage() {
    $("#alertInfo").html($("#attachedDocSaveAlertInfoTemplate").clone(true));
    $("#alertInfo").find("#attachedDocSaveAlertInfoTemplate").css("display", "block");
}

function displayAttDocAddAlertMessage() {
    $("#alertInfo").html($("#attachedDocAddedAlertInfoTemplate").clone(true));
    $("#alertInfo").find("#attachedDocAddedAlertInfoTemplate").css("display", "block");
}

function cancelEdit() {
    if (isEnable == true) {
        isEnable = false;
        $.ajax({
            type: 'POST',
            url: $("#cancelEditURL").val(),
            success: function (data, textStatus) {
                $("#alertInfo").empty();
                $("#attachmentTableBody").html(data);
                makeDatepicker($('#docDate'));
                resetAttData();
                $("#attachedErrors").html("<div></div>");
                isEnable = true;
                enableAttachDocForm();
                var buttonsToDisable = $("#appMainForm").find("[name^='operation'][class~='btn']");
                configureFormFields(buttonsToDisable, false);
                var AdditionnalButtonsToDisable = [".xmlImportButton", ".exportXML", ".Close", ".print", "#delete"]
                configureFormFields(AdditionnalButtonsToDisable, false);
                enableAllErrorLinks();
            }
        });
    }
}

function openEditUploadDialog() {
    if (isEnable == true) {
        isEnable = false;
    }
    if (!$("#editUploadFile").hasClass('btn-disabled')) {
        $("#alertInfo").empty();
        // $("#editUploadFile").addClass('disabled');
        $("#editUploadFile").addClass('btn-disabled');
        $("#editUploadFile").addClass('disableClick');
        $("#uploadDialog #startUpload").val($("#uploadAndSaveMessage").val());
        $("#uploadDialog").modal({backdrop: "static"});
    }
}

function openAddUploadDialog() {
    if (isEnable == true) {
        isEnable = false;
    }
    if (!$("#addUploadFile").hasClass('btn-disabled')) {
        $("#alertInfo").empty();
        // $("#addUploadFile").addClass('disabled');
        $("#addUploadFile").addClass('btn-disabled');
        $("#addUploadFile").addClass('disableClick');
        $("#uploadDialog #startUpload").val($("#uploadAndAddMessage").val());
        $("#uploadDialog").modal({backdrop: "static"});
    }
}

function onUploadComplete(serverResponse) {
    hideErrorsDiv();
    $("#uploadDialog").modal('hide');
    // $("#addAttDoc").removeAttr('disabled');
    $("#addAttDoc").removeAttr('btn-disabled');
    $("#addAttDoc").removeAttr('disableClick');
    enableAttButtons();
    if (serverResponse == "OK") {
        fireAttDocSaveOrAddActions();
    } else {
        $("#attachedErrors").html(serverResponse);
    }
}

function fireAttDocSaveOrAddActions() {
    if ($("#currentEditableAttDocNumber").val() != "") {
        $("#attachmentTableBody #saveAtt").click();
    } else {
        $("#addAttDoc").click();
    }
}

function hideAttDocUploadDialog() {
    $('#uploadDialog').modal('hide');
    enableAttButtons();
}

function enableAttButtons() {
    if ($("#currentEditableAttDocNumber").val() == "") {
        //  $("#addUploadFile").removeClass('disabled');
        $("#addUploadFile").removeClass('btn-disabled');
        $("#addUploadFile").removeClass('disableClick');
    }
    //   $("#editUploadFile").removeClass('disabled');
    $("#editUploadFile").removeClass('btn-disabled');
    $("#editUploadFile").removeClass('disableClick');
    $("#addAttDoc").removeAttr('disabled');
    isEnable = true;
}


function initAttUploadAjaxForm() {
    var uploadDialogObject = {
        uploadDialogId: "uploadDialog",
        fileInputId: "attDoc",

        onUploadCompleteFunction: function (xhr) {
            onUploadComplete(xhr.responseText);
        },
        hideUploadDialogFunction: function () {
            enableAttButtons();
        }
    };

    var submitButtonObject = {
        uploadDialogId: "uploadDialog",
        submitButtonId: "startUpload",
        fileInputId: "attDoc",
        errorMessage:I18N['extensionAlertMsg'],
        acceptedFormatsArrayInLowerCase: $("#attachmentAcceptedFormats").val().split(','),

        onClickFunction: function (extension) {
            $("#fileExtension").val(extension);
            $("#preAttNumber").val(Number($("#attachments .attRow").length + 1));
        }
    };

    onStartUploadButtonClick(submitButtonObject);
    onUploadDialogFireButtonClick("uploadDialog", "addUploadFile", openAddUploadDialog);
    onUploadDialogFireButtonClick("uploadDialog", "editUploadFile", openEditUploadDialog);
    initUploadDialog(uploadDialogObject);
}

function disableAttachDocForm() {
    var attDocElementToDisable = ["#addAttDoc", ".editAttButton", ".deleteAttButton", "#docType", "#docRef", "#docDate", ".attDocLink", "#addUploadFile"]
    configureFormFields(attDocElementToDisable, true)
}

function enableAttachDocForm() {
    var attDocElementToDisable = ["#addAttDoc", ".editAttButton", ".deleteAttButton", "#docType", "#docRef", "#docDate", ".attDocLink"]
    configureFormFields(attDocElementToDisable, false)
}

function getAttachmentFields() {
    var attachmentFields = ["#docType", "#docRef", "#docDate"]
    return attachmentFields
}

// ADDING MODAL

function initModalForShow() {
    $('#uploadDialogbis').css({
        "z-index": "5000",
        "overflow-y": "auto",
        "max-height": "$(window).height() * 0.7"
    });
    $('#docType').css({"z-index":"10000"});
    $("#uploadDialogbis #fileExtension").val('');
    $("#uploadDialogbis #fileLabel").text('');
}

function initModalForEdit() {
    $('#uploadDialogbis').css({
        "z-index": "5000",
        "overflow-y": "auto",
        "max-height": "$(window).height() * 0.7"
    });
}

function initattDocFile() {
    $("#uploadDialogbis #attDocFile").val("")
}

function updateModalAttDocs(data) {
    $("#uploadDialogbis .modal-body #content").html("");
    $("#uploadDialogbis .modal-header").html("");
    $("#uploadDialogbis .modal-footer").html("");
    $("#uploadDialogbis .modal-header").html(data.headermodal);
    $("#uploadDialogbis .modal-body #content").html(data.bodymodal);
    $("#uploadDialogbis .modal-footer").html(data.footermodal);
    var attachmentFields = ["docType", "docRef", "docDate"]
    $.each(attachmentFields, function () {
        if (typeof $("#" + this).val() != "undefined") {
            $("#" + this).removeAttr("disabled", "disabled");
            $("#" + this).removeAttr("readonly", "readonly");
        }
    });
}

function showModal() {
    if (typeof $("#docType").val() != "undefined") {
        //makeAutocomplete("docType", "HT_ATT", "description", attDocCodeSetter, "250");
        makeAjaxAutocomplete("docType", "HT_ATT", "description,code", "code,description", "code,description", attDocModalCodeSetter, "70", null, null, null, null, "70");
        $("#docType").autocomplete({
            minLength: 1
        })
    }
    if (typeof $("#docDate").val() != "undefined") {
        if ($('#docDate').length > 0) {
            if ($('#docDate').hasClass('generate-datepicker')) {
                makeDatepicker($('#docDate'));
            }
        }
    }
    $("#uploadDialogbis").modal({backdrop: "static"});
}

function showAttDoc() {
    var conversationId = $("#conversationId").val()
    $.ajax({
        type: "POST",
        data: {
            conversationId: conversationId
        },
        beforeSend: function () {
            initModalForShow();
            updateModalForm();
        },
        url: $("#openAttDoc").val()
    }).done(function (data) {
        updateModalAttDocs(data);
        showModal();
    });
}

function showEditDocModal(elemId) {
    var elemIdArr = elemId.split("_");
    var attNumber = Number(elemIdArr[1]);
    var conversationId = $("#conversationId").val()
    $.ajax({
        type: "POST",
        data: {
            conversationId: conversationId,
            attNumber: attNumber
        },
        beforeSend: function () {
            initModalForEdit();
        },
        url: $("#openAttDocForEdit").val()
    }).done(function (data) {
        updateModalAttDocs(data);
        showModal();
    });
}

function onUploadCompleteModal() {
    console.log("isUploadStarted:" + isUploadStarted)
    if (isUploadStarted) {
        var iframe = document.getElementById("hidden-upload-frames");
        var contents = iframe.contentWindow || iframe.contentDocument;
        var jsonResult = contents.document.body.innerHTML;
        if (jsonResult && jsonResult.match(/^<pre/i)) {
            jsonResult = contents.document.body.firstChild.firstChild.nodeValue;
        }
        console.log("jsonResult:" + jsonResult)
        if (jsonResult) {
            $.each(JSON.parse(jsonResult), function (index, valeur) {
                if (valeur.resultat == "Error") {
                    console.log("error")
                    displayAttDocMaxSize();
                    initializeInput();
                    isUploadStarted = false
                } else {
                    console.log("go to see the controller")
                    fireAttDocSaveOrAddActionsModal();
                    isUploadStarted = false
                }
            });
        }
    }
}

function displayAttDocMaxSize() {
    $("#uploadDialogbis #alertInfoModal").html($("#incorrectFileSizeMessageTemplate").clone(true));
    $("#uploadDialogbis #alertInfoModal").find("#incorrectFileSizeMessageTemplate").css("display", "block");
}


function initializeInput() {
    $('#uploadDialogbis').on('shown.bs.modal', function (e) {
        $("#attDocFile").val("");
    })
    $('#uploadDialogbis').on('show.bs.modal', function (e) {
        $("#attDocFile").val("");
    })
}

function fireAttDocSaveOrAddActionsModal() {
    var flg = $("#uploadDialogbis #flagdocsnumber").val();
    if (flg.trim() !== "") {
        // edit bouton
        editAttDocModal();
    }
    else {
        // add bouton
        addAttDocModal();
    }
}


function editAttDocModal() {
    var attNumber = $("#uploadDialogbis #attNumber").val();
    var docType = $("#uploadDialogbis #docType").val().trim();
    var docRef = $("#uploadDialogbis #docRef").val().trim();
    var docDate = $("#uploadDialogbis #docDate").val().trim();
    var fileExtension = $("#uploadDialogbis #fileExtension").val()
    var conversationId = $("#conversationId").val();
    var docCode = $("#uploadDialogbis #docCode").val().trim();
    var fieldsToValidate = ["docRef", "docDate"];
    validateDocTypeModal(fieldsToValidate);
    var allFieldsAreFilled = docType.length > 0 && docRef.length > 0 && docDate.length > 0;
    if (allFieldsAreFilled) {
        $.ajax({
            type: "POST",
            url: $("#updateAttURL").val(),
            data: {
                attNumber: attNumber,
                docType: docType,
                docCode: docCode,
                docRef: docRef,
                docDate: docDate,
                fileExtension: fileExtension,
                conversationId: conversationId
            },
            success: function (data, textStatus) {
                if (data.error == 'error') {
                    initModalError(data);
                } else {
                    removeAttDocErrors();
                    resetAttachedDocsModal();
                    displayAttDocAddAlertUpdateMessage();
                    renderData(data);
                    initFileExtension();
                }

            },
            complete: function (jqXHR, textStatus) {
                closeModal();
            }
        });
    }
}

function addAttDocModal() {
    var docType = $("#uploadDialogbis #docType").val().trim();
    var docRef = $("#uploadDialogbis #docRef").val().trim();
    var docDate = $("#uploadDialogbis #docDate").val().trim();
    var extensionDoc = $("#uploadDialogbis #fileExtension").val();
    var conversationId = $("#conversationId").val();
    var docCode = $("#uploadDialogbis #docCode").val().trim();
    var fieldsToValidate = ["docType", "docRef", "docDate"];
    validateDocType(fieldsToValidate);
    var allFieldsAreFilled = docType.length > 0 && docRef.length > 0 && docDate.length > 0;
    if (allFieldsAreFilled) {
        $.ajax({
            type: "POST",
            url: $("#addAttURL").val(),
            data: {
                docType: docType,
                docRef: docRef,
                docDate: docDate,
                fileExtension: extensionDoc,
                docCode: docCode,
                conversationId: conversationId
            },
            success: function (data, textStatus) {

                if (data.error == 'error') {
                    initModalError(data)
                } else {
                    removeAttDocErrors();
                    resetAttachedDocsModal();
                    displayAttDocAddAlertMessage();
                    renderData(data);
                    updateModalForm()
                }
            },
            complete: function (jqXHR, textStatus) {
                closeModal();
            }
        });
    }
}


function displayAttDocAddAlertUpdateMessage() {
    $("#alertInfo").html($("#attachedDocAddedUpdateInfoTemplate").clone(true));
    $("#alertInfo").find("#attachedDocAddedUpdateInfoTemplate").css("display", "block");
}

var attachedDocsMainFields = ["docType", "docRef", "docDate"];
var attachedDocsMainBtns = ["addUpload", "addAttDocModal"];

function resetAttachedDocs() {
    $.each(attachedDocsMainFields, function () {
        var attachedDocElement = $("#" + this);
        attachedDocElement.val("");
        attachedDocElement.removeAttr('disabled', 'disabled');
    });

    $.each(attachedDocsMainBtns, function () {
        var attachedDocElement = $("#" + this);
        attachedDocElement.removeAttr('disabled', 'disabled');
        attachedDocElement.removeClass("disabled");
        if ($("#" + this).attr('id') == 'addAttDocModal') {
            attachedDocElement.attr("onclick", "addAttDoc()");
        }
        if ($("#" + this).attr('id') == 'addUpload') {
            attachedDocElement.attr("onclick", "openAddUploadDialog()");
        }

    })
    $("#fileExtension").val("");
    $("#flagdocsnumber").val(" ");
    $("#addUpload").addClass("disabled");
}

function resetAttachedDocsModal() {
    $.each(attachedDocsMainFields, function () {
        var attachedDocElement = $("#" + this);
        attachedDocElement.val("");
        attachedDocElement.removeAttr('disabled', 'disabled');
    });

    $.each(attachedDocsMainBtns, function () {
        var attachedDocElement = $("#" + this);
        attachedDocElement.removeAttr('disabled', 'disabled');
        attachedDocElement.removeClass("disabled");
        if ($("#" + this).attr('id') == 'addUpload') {
            attachedDocElement.attr("onclick", "openAddUploadDialog()");
        }

    })
    $("#fileExtension").val("");
    $("#flagdocsnumber").val(" ");
    $("#addUpload").addClass("disabled");
}

function removeAttDocErrors() {
    $("#attDocListContainer").find("#attDocListErrors .errorContainer").remove();
}

function setFocusOnAttDocErrorField(errorElements) {
    $.each(errorElements, function (i) {
        $(this).click(function () {
            var fieldId = $(this).attr('errorElementId').split("_")[0];
            $("#" + fieldId).focus();
        });
    });
}

function renderData(data) {
    $("#attachmentTableBody").html(data.responseData);
    $("#allheaderTable").html(data.actionAtt);
    $("#ephytoGenTab #numberAttDoc").html(data.numberAttDoc);
}

function closeModal() {
    $("#uploadDialogbis").modal("hide");
}

function initModalError(data) {
    $("#alertInfo").empty();
    $("#attachedErrors").html(data);
}

function showDeleteModalAttDoc(elemId) {
    var elemIdArr = elemId.split("_");
    var attNumber = Number(elemIdArr[1]);
    $("#js_itemCancelDeleteItemModalBody #rank").val(attNumber);
    $("#js_deleteCancelFileDlg").modal({backdrop: "static"});
    $('#confirmDialog').on('shown.bs.modal', function (e) {
        $("#js_itemCancelDeleteItemModalBody #rank").val(elemId);
    })
    $('#confirmDialog').on('show.bs.modal', function (e) {
        $("#js_itemCancelDeleteItemModalBody #rank").val(elemId);
    })
}


function initFileExtension() {
    $("#uploadDialogbis #fileExtension").val("");
}

function setFileAttName(value) {
    $("#fileLabel").text(value);
}

$(document).on("click", "#startUploadModal", function (e) {
    initializeInput();
    clearAllInfo();
    var docType = $("#uploadDialogbis #docType").val().trim();
    var docRef = $("#uploadDialogbis #docRef").val().trim();
    var docDate = $("#uploadDialogbis #docDate").val().trim();
    var fileExtension = $("#uploadDialogbis #fileExtension").val();
    var conversationId = $("#conversationId").val()
    var docCode = $("#uploadDialogbis #docCode").val().trim();
    var fieldsToValidate = ["docType", "docRef", "docDate"];
    validateDocType(fieldsToValidate);
    var allFieldsAreFilled = docType.length > 0 && docRef.length > 0 && docDate.length > 0;
    if (allFieldsAreFilled) {
        var fileName = $("#uploadDialogbis #fileLabel").html();
        if (isEnable === true) {
            isEnable = false;
        }
        if (fileName === null || fileName === "") {
            displayAttDocChooseFileMessage();
            return false;
        }
        var extensionsArray = $("#attachmentAcceptedFormats").val().split(",");
        var extension = fileName.substr((fileName.lastIndexOf(".") + 1)).toLowerCase();
        if ($.inArray($.trim(extension), extensionsArray) === -1 && (fileName != null || fileName != "")) {
            displayAttDocAddIncorrectExtensionMessage();
            return false;
        }
        $("#fileExtension").val(extension);
        isUploadStarted = true;
    }

});

function clearAllInfo() {
    $("#uploadDialogbis #alertInfoModal").empty();
}


function validateDocType(fieldsToValidate) {
    $.each(fieldsToValidate, function (index, value) {
        if ($("#" + value).val().length == 0) {
            $("#" + value).addClass("error-border");
        }
    });
}

function displayAttDocAddAlertUpdateMessage() {
    $("#alertInfo").html($("#attachedDocAddedUpdateInfoTemplate").clone(true));
    $("#alertInfo").find("#attachedDocAddedUpdateInfoTemplate").css("display", "block");
}

function displayAttDocAddIncorrectExtensionMessage() {
    $("#uploadDialogbis #alertInfoModal").html($("#incorrectFileTypeMessageTemplate").clone(true));
    $("#uploadDialogbis #alertInfoModal").find("#incorrectFileTypeMessageTemplate").css("display", "block");
}

function displayAttDocChooseFileMessage() {
    $("#uploadDialogbis #alertInfoModal").html($("#chooseFileMessageTemplate").clone(true));
    $("#uploadDialogbis #alertInfoModal").find("#chooseFileMessageTemplate").css("display", "block");
}


function removeAttDocModal() {
    var conversationId = $("#conversationId").val();
    var attNumber = $("#js_itemCancelDeleteItemModalBody #rank").val();
    $.ajax({
        type: "POST",
        data: {
            attNumber: attNumber,
            conversationId: conversationId
        },
        url: $("#removeAttURL").val(),
        success: function (data, textStatus) {
            if (data.error == 'error') {
                initModalError(data);
                resetAttachedDocsModal();
            } else {
                removeAttDocErrors();
                resetAttachedDocsModal();
                renderData(data);
                initInfoAlerte();
                hideErrorsDiv();
                displayAttDocAddAlertRemoveMessage();
            }
        }
        ,
        complete: function (jqXHR, textStatus) {
            closeModalDelete();
            updateModalForm();
        }
    });

}

function initInfoAlerte() {
    $("#alertInfo").empty();
}

function closeModalDelete() {
    $("#js_deleteCancelFileDlg").modal("hide");
}

function updateModalForm() {
    $("#uploadDialogbis #docType").val("");
    $("#uploadDialogbis #docRef").val("");
    $("#uploadDialogbis #docDate").val("");
    $("#uploadDialogbis #fileExtension").val("")
    $("#uploadDialogbis #fileLabel").html("")
}

function displayAttDocAddAlertRemoveMessage() {
    $("#alertInfo").html($("#attachedDocAddedRemoveInfoTemplate").clone(true));
    $("#alertInfo").find("#attachedDocAddedRemoveInfoTemplate").css("display", "block");
}

function validateDocTypeModal(fieldsToValidate) {
    $.each(fieldsToValidate, function (index, value) {
        if ($("#uploadDialogbis #" + value).val().length == 0) {
            $("#uploadDialogbis #" + value).addClass("error-border");
        }
    });
}

function hideAttDocUploadDialog() {
    $("#uploadDialogbis").modal("hide");
    enableAttButtonsForModal();
}

function enableAttButtonsForModal() {
    if ($("#flagdocsnumber").val() === " ") {
        $("#addUpload").removeClass("disabled");
    }
    $("#editUploadFile").removeClass("disabled"); // edit bouton
    isEnable = true;
}

function validateFieldOnKeyPressModal(element) {
    var id = $(element).attr("id");
    var value = $("#uploadDialogbis #" + id).val().trim();
    var fieldHasValue = value.length;
    if (fieldHasValue > 0) {
        $("#uploadDialogbis #" + id).removeClass("error-border");
    } else {
        $("#uploadDialogbis #" + id).addClass("error-border");
    }
}


function resetFieldsOnChangeModal(element) {
    var id = $(element).attr("id");
    var value = $("#uploadDialogbis #" + id).val().trim();
    if (id == "docType") {
        if (id == "docType") {
            if (!$("#uploadDialogbis #docCode").val()) {
                $("#uploadDialogbis #docType").val("");
                $("#uploadDialogbis #oldValue").val("");
                $("#uploadDialogbis #docType").addClass("error-border");
            } else {
                if ($("#uploadDialogbis #oldValue").val().trim() === value) {
                    $("#" + id).removeClass("error-border");
                } else {
                    $("#uploadDialogbis #docType").val("");
                    $("#uploadDialogbis #oldValue").val("");
                    $("#uploadDialogbis #docType").addClass("error-border");
                }

            }
        }

    }

}

// END MODAL