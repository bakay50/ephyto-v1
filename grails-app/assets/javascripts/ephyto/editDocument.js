/**
 * Created by DEV23 on 3/28/2016.
 */

$(document).ready(function () {
    initDocument();
    setupAjaxListener();
});

function initDocument() {
    disabledAllButtons(true);
    initMandatoryFieldsForEphyto();
    setupAutocomplete();
    initEphytoGenReferences();
    initJqueryValidationEphyto();
    initModalSetup();
    initJqueryValidationItemGoods();
    initOperationDialog();
    initAttDocs();
    enableButtonDialog();
    initExportXMLFunction();
    initErrorHandler();
    disabledAllButtons(false);
    setBotanicalFielSize();
}

function initMandatoryFieldsForEphyto() {
    initAndActivateValidationSearch();
    var form_certificat = $("#appMainForm");
    var fieldsCertificatToUpdate = form_certificat.find(".wfinput");
    $.each(fieldsCertificatToUpdate, function () {
        $(this).prop("required", false);
        $(this).removeClass('mandatory');
    });
}


function disableButtons(buttonsToDisable) {
    $.each(buttonsToDisable, function () {
        $(this).attr('disabled', 'disabled');

    });
    disableLinkButton();
}

function disabledAllButtons(disableField) {
    configureFormFields(getMainButtonsToConfigure(), disableField);

}

function disableLinkButton() {
    var mainLinks = $("#appMainForm").find(".linkBtns");
    $.each(mainLinks, function () {
        $(this).addClass("disableClick");
        $(this).addClass("btn-disabled");
    });
}


function initEphytoGenDatePickers() {
    var ephytoDateFields = new Array("requestDate", "dockingPermissionDate", "phytosanitaryCertificateDate", "declarationDate", "boardingDate", "proposedOperationDate", "operationDate")
    $.each(ephytoDateFields, function () {
        var field = $("#" + this);
        if (typeof field.val() != "undefined") {
            if (field.hasClass('generate-datepicker')) {
                makeDatepicker(field);
            }
        }
    });
}

function initAttDocs() {
    if ($("#uploadDialog").attr("id") !== undefined) {
        initAttDoc();
    }
}

function initModalSetup() {
    initXmlUploadForm();
}


// Delete exemption from search , openning modal button are disable after clicking delete button
function enableButtonDialog() {
    $('#confirmDialog').on('shown.bs.modal', function (e) {
        $("#confirmDialog").find("#cancelOper").removeAttr("disabled");
        $("#confirmDialog").find("#confirmOper").removeAttr("disabled");
    })
    $('#confirmDialog').on('show.bs.modal', function (e) {
        $("#confirmDialog").find("#cancelOper").removeAttr("disabled");
        $("#confirmDialog").find("#confirmOper").removeAttr("disabled");
    })
}

function showAjaxSuccessDialog(dialogText) {
    openMessageDialog(I18N['client.successOperationTitle'], dialogText)
}

function updateObservationsForUTF8(elementfields, elts) {
    console.log("Fields :" + elementfields.name + "  value : " + $(elementfields).val());
    if ($(elementfields).val()) {
        $("#formContents #" + elts + "UTF").val($(elementfields).val());
        console.log("UTF value :" + $("#formContents #" + elts + "UTF").val());
    }
}

function updateForUTF8(elementfields) {
    console.log("Fields :" + elementfields + "  value : " + $("#formContents #" + elementfields).val());
    if ($("#formContents #" + elementfields).val()) {
        $("#formContents #" + elementfields + "UTF").val($("#formContents #" + elementfields).serialize().split("=")[1]);
        console.log("UTF value :" + $("#formContents #" + elementfields + "UTF").val());
    }
}

function setBotanicalFielSize() {
    var modeOfTransportCode = $("#modeOfTransportCode").val();
    if (modeOfTransportCode === "4") {
        $("#botanicalName").attr('maxlength', '825');
    } else {
        $("#botanicalName").attr('maxlength', '100');
    }
}
