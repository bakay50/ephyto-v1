function initEphytoGenReferences() {
    makeAjaxAutocomplete('countryOfDestinationCode', 'REF_CTY', 'code,description', 'code,description', 'code,description', textLabelSetter, '50');
    makeAjaxAutocomplete('placeOfUnloadingCode', 'REF_LOC', 'code,description', 'code,description', 'code,description', textLabelSetter, '50');
    makeAutocomplete("modeOfTransportCode", "REF_MOT", "code,description", textLabelSetter, "35");
    $("#modeOfTransportCode").autocomplete({
        minLength: 1
    });
    loadDataFromUserProperty();
    makeAjaxAutocomplete('commodityCode', 'HT_TAR', 'code,description', 'code,description', 'code,description', setTarDescription, '90,500');
    makeAjaxAutocomplete('countryOfOriginCode', 'REF_CTY', 'code,description', 'code,description', 'code,description', textLabelSetter, '50');
    makeAutocomplete("packageCode", "RIMM_PCK_COD", "code,description", textLabelSetter, "50");
    makeAutocomplete("productTypeCode", "RIMM_PDT", "code,name", productAutoCompleteSetter, "150,300");

}

function initRefForAddTreatment() {
    makeAutocomplete("applicatorCode", "RIMM_APL", "code,agreement,nameAddress", applicatorNameAddressSetter, "45,45");
    initEphytoTimer();
}

function initEphytoTimer() {
    makeTimepickerFormat($('#treatmentSartTime'));
    makeTimepickerFormat($('#treatmentEndTime'));
}

function makeTimepickerFormat(element) {
    element.addClass("formattime");
    $('.formattime').mask("99:99:99");
}

function loadDataFromUserProperty() {
    if (hasUserProperties("#decUserProps")) {
        var autocompleteSource = constructModelForAutocomplete(getLocalRef("REF_DEC"), "code,description");
        makeAutocompleteByModel("declarantCode", autocompleteSource, declarantNameAddressSetter, "100");
    } else {
        makeAjaxAutocomplete("declarantCode", "HT_DEC", "code,description",
            "code,description,address1,address2,address3,address4,phoneNumber,fax",
            "code,description", declarantNameAddressSetter, "100");
    }

    if (hasUserProperties("#tinUserProps")) {
        var autocompleteSource = constructModelForAutocomplete(getLocalRef("REF_CMP"), "code,description");
        makeAutocompleteByModel("exporterCode", autocompleteSource, companyNameAddressSetter, "100,300");
    } else {
        makeAjaxAutocomplete("exporterCode", "HT_CMP", "code,description",
            "code,description,address1,address2,address3,address4,phoneNumber,fax,email,url",
            "code,description", companyNameAddressSetter, "100,300");
    }

    if (hasUserProperties("#officeAccessUserProps")) {
        var autocompleteSource = constructModelForAutocomplete(getLocalRef("HT_CUO"), "code,description");
        makeAutocompleteByModel("customsClearanceOfficeCode", autocompleteSource, textLabelSetter, "50");

    } else {
        makeAutocomplete("customsClearanceOfficeCode", "HT_CUO", "code,description", textLabelSetter, "50");
    }
}

function applicatorNameAddressSetter(sourceId, item) {
    var agreement = item ? configureItems(item.agreement) : "";
    var nameAddress = item ? configureItems(item.nameAddress) : "";
    $('#applicatorNameAddress').html(nameAddress);
    $('#applicatorAgreement').html(agreement);

}

function productAutoCompleteSetter(sourceId, item) {
    var description = item ? configureItems(item.name) : "";
    var defaultQuantity = item ? configureItems(item.defaultQuantity) : "";
    var defaultNetWeight = item ? configureItems(item.defaultNetWeight) : "";
    var defaultGrossWeight = item ? configureItems(item.defaultGrossWeight) : "";
    var botanicalName = item ? configureItems(item.botanicalName) : "";
    $('#productTypeName').html(description);
    $('#defaultQuantity').val(defaultQuantity);
    $('#defaultNetWeight').val(defaultNetWeight);
    $('#defaultGrossWeight').val(defaultGrossWeight);
    $('#botanicalName').val(botanicalName);
    disabledBotanicalField();
    setObservationsField();

}

function getLocalRef(name) {
    return window[name];
}

function companyNameAddressSetter(sourceId, item) {
    var itemName = item ? configureItems(item.description) : "";
    $('#exporterName').text(itemName);
    $('#exporterAddress').text(itemName.concat(getAddress(sourceId, item)));
}

function declarantNameAddressSetter(sourceId, item) {
    var itemName = item ? configureItems(item.description) : "";
    $('#declarantName').text(itemName);
    $('#declarantAddress').text(itemName.concat(getAddress(sourceId, item)));
}

function getAddress(sourceId, item) {
    var address = '';
    address += item && item.address1 ? item.address1 + ' ' : '';
    address += item && item.address2 ? item.address2 + ' ' : '';
    address += item && item.address3 ? item.address3 + ' ' : '';
    address += item && item.address4 ? item.address4 + ' ' : '';
    return address;
}

function setTarDescription(sourceId, item) {
    $("#commodityDescription").val(item ? item.description : '');
}

function configureItems(source) {
    return (source != null) ? source : '';
}

function textLabelSetter(sourceId, item) {
    var itemPropertyToSet;
    var fieldsWithNameProp = ['productTypeCode']//add field here if its description property name == "name"

    itemPropertyToSet = jQuery.inArray(sourceId, fieldsWithNameProp) >= 0 ? "name" : "description";

    var targetId = sourceId.replace("Code", "Name");
    if (targetId) {
        var defaultDisplay = item ? item[itemPropertyToSet] : '';
        var element = $("#" + targetId);
        if (element.is('label')) {
            element.text(defaultDisplay);
        } else {
            element.val(defaultDisplay);
        }
    }
}

function disabledBotanicalField() {

    if ($("#productTypeCode").val() == "CAFE" || $("#productTypeCode").val() == "CACAO") {
        $("#botanicalName").attr("disabled", "disabled");
    } else {
        $("#botanicalName").removeAttr('disabled');
    }

}

function setObservationsField() {
    if ($("#productTypeCode").val() === "BOIS") {
        $("#formContents #observations").val($("#boisMention").val());
        $("#formContents #observations" + "UTF").val($("#formContents #observations").serialize().split("=")[1]);
    }else if ($("#productTypeCode").val() === "MANGUE") {
        $("#formContents #observations").val($("#mangueMention").val());
        $("#formContents #observations" + "UTF").val($("#formContents #observations").serialize().split("=")[1]);
    } else {
        $("#formContents #observations").val("");
    }
}

$(document).ready(function () {
    makeAutocomplete("code", "RIMM_APL", "code,nameAddress", null, "50");
});
