$(document).ready(function () {
    initAndActivateValidationSearch();
    //updateResizeColumnsTable("searchResults");
    var controllerName = $("#controllerName").val();
    var searchNameForm
    if (controllerName == "ephytoGen") {
        initEphytoGenAutocomplete();
        searchNameForm = "searchEphytoGenForm"
    }
    enableFields();
    setupAutocomplete();
    enableSearchKeyEnter(searchNameForm);
    checkEmptyFields();
});


function enableSearchKeyEnter(searchNameForm) {
    if (searchNameForm) {
        $(window).on('keydown', function (event) {
            enableEnterKeys(searchNameForm, event, this)
        });
        $(".wfinput").on("keydown", function wfinput(e) {
            enableEnterKeys(searchNameForm, e, this)
        });
    }
}

    
function enableEnterKeys(searchNameForm, event, obj) {
    var key = (event.keyCode ? event.keyCode : event.which);
    if (key === 13) {
        $(obj).blur();
        setupAutocomplete();
        $("#" + searchNameForm).submit();
        event.preventDefault();
        event.stopPropagation();
        return false;
    }
}


function textLabelSetter(sourceId, item) {
    var itemPropertyToSet;
    var fieldsWithNameProp = []//add field here if its description property name == "name"

    itemPropertyToSet = jQuery.inArray(sourceId, fieldsWithNameProp) >= 0 ? "name" : "description";

    var targetId;

    targetId = sourceId.replace("Code", "Name");
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

function initEphytoGenAutocomplete() {

    //makeAjaxAutocomplete('applicatorCode', 'RIMM_APL', 'code,nameAddress', 'code,agreement,nameAddress', 'code,nameAddress', null, '50');
    makeAutocomplete("applicatorCode", "RIMM_APL", "code,nameAddress", null, "50");

    if (hasUserProperties("#decUserProps")) {
        var autocompleteSource = constructModelForAutocomplete(getLocalRef("REF_DEC"), "code,description");
        makeAutocompleteByModel("declarantCode", autocompleteSource, null, "100");
    } else {
        makeAjaxAutocomplete("declarantCode", "HT_DEC", "code,description","code,description",
            "code,description", null, "100");
    }

    if (hasUserProperties("#tinUserProps")) {
        var autocompleteSource = constructModelForAutocomplete(getLocalRef("REF_CMP"), "code,description");
        makeAutocompleteByModel("exporterCode", autocompleteSource, null, "100");
    } else {
        makeAjaxAutocomplete("exporterCode", "HT_CMP", "code,description","code,description",
            "code,description", null, "100");
    }

    if(hasUserProperties("#officeAccessUserProps")){
        var autocompleteSource = constructModelForAutocomplete(getLocalRef("HT_CUO"), "code,description");
        makeAutocompleteByModel("customsClearanceOfficeCode", autocompleteSource, textLabelSetter, "50");
    } else {
        makeAjaxAutocomplete("customsClearanceOfficeCode", "HT_CUO", "code,description","code,description","code,description", textLabelSetter, "50");
    }

}


function textLabelSetters(sourceId, item) {
    var fieldsWithNameProp = [""]
    var itemPropertyToSet = jQuery.inArray(sourceId, fieldsWithNameProp) >= 0 ? "name" : "description";
    if (sourceId == "defaultCP3Code") {
        targetId = sourceId.replace("Code", "Desc");
    } else {
        targetId = sourceId.replace("Code", "Name");
    }
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

function resetEphytoCriteriaFields() {
    //$("#searchEphytoGenForm").find(".wfinput:not([readonly])").val("");
    var searchEphytoGenForm = $("#searchEphytoGenForm");
    var searchFields = searchEphytoGenForm.find(".wfinput:not([readonly])");
    $.each(searchFields, function () {
        if(this.name != 'max'){
            $(this).val("");
            $(this).removeClass("form_error");
        }
    });
    $("#requestDate").attr("disabled", "disabled");
    $("#requestDateTo").attr("disabled", "disabled");
    $("#disinfectionCertificateDate").attr("disabled", "disabled");
    $("#disinfectionCertificateDateTo").attr("disabled", "disabled");
    $("#dockingPermissionDate").attr("disabled", "disabled");
    $("#dockingPermissionDateTo").attr("disabled", "disabled");
    $("#phytosanitaryCertificateDate").attr("disabled", "disabled");
    $("#phytosanitaryCertificateDateTo").attr("disabled", "disabled");

}


function enableFields() {
    var fields = ['disinfectionCertificateDate', 'requestDate', 'dockingPermissionDate', 'phytosanitaryCertificateDate'];

    $.each(fields, function () {
        var field = $("#" + this);
        var fieldTo = $("#" + this + "To");
        var opField = $("#op_" + this);
        var opVal = opField.val();

        field.attr('disabled', opVal == "");
        fieldTo.attr('disabled', opVal != "between");

        opField.change(function () {
            if (opField.val() == "") {
                disableSearchField(field);
                disableSearchField(fieldTo);
            } else if (opField.val() == "between") {
                enableDateField(field);
                enableDateField(fieldTo);
                field.focus();
            } else {
                enableDateField(field);
                disableSearchField(fieldTo);
                field.focus();
            }
        });
    })

}

function enableDateField(field) {
    field.removeAttr('disabled');
    if (field.attr('name') == 'requestDate' || field.attr('name') == 'requestDateTo' || field.attr('name') == 'disinfectionCertificateDate' || field.attr('name') == 'disinfectionCertificateDateTo' ||
        field.attr('name') == 'dockingPermissionDate' || field.attr('name') == 'dockingPermissionDateTo' || field.attr('name') == 'phytosanitaryCertificateDate' || field.attr('name') == 'phytosanitaryCertificateDateTo') {
        if (field.hasClass('generate-datepicker')) {
            makeDatepicker(field);
        }
    }

}

function disableSearchField(field) {
    field.val("");
    field.attr('disabled', true);
}

function checkEmptyFields(){
    var fields = ['requestNumber', 'disinfectionCertificateRef', 'dockingPermissionRef', 'phytosanitaryCertificateRef','userReference'];
    $.each(fields, function () {
        var fieldelement = this;
        var field = $("#" + this);
        var opVal = field.val();
        if(opVal){
            updateUTFValue(fieldelement)
          }
        field.change(function () {
            updateUTFValue(fieldelement)
        });
    })
}

function updateUTFValue(fieldelement){
    $("#searchEphytoGenForm #"+fieldelement+"UTF").val($("#searchEphytoGenForm #"+fieldelement).serialize().split("=")[1]);
    console.log("UTF value :" + $("#searchEphytoGenForm #"+fieldelement+"UTF").val());
}