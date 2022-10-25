/**
 * Created by DEV23 on 3/28/2016.
 */

var EphytoTreatmentController = {
    /**
     * @return {boolean}
     */
    CRUD: function (data) {
        $.ajax({
            type: data.type,
            url: data.url,
            data: data.params,
            success: function (response) {
                var $itemListContainer = $("#treatmentListContainer");
                var $itemsTab = $("#treatments");
                var $itemFormCtr = $("#treatmentFormCtr");
                switch (data.action) {
                    case "addTreatment":
                        taglib.validation.assignMandatoryRuleToInput = false;
                        EphytoTreatmentController.renderInnerForm(response);
                        EphytoTreatmentController.initTypeTreatment();
                        EphytoTreatmentController.updateTreatmentGroupeValidation();
                        initRefForAddTreatment();
                        EphytoTreatmentController.initTreatment();
                        EphytoTreatmentController.initTempOperValue();
                        configureMainButtons(EphytoTreatmentController.getTabMainContainer(), true);
                        focusOnErrorField();
                        removeFieldError();
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), false);
                        break;
                    case "editTreatment":
                        $itemListContainer.remove();
                        $itemsTab.find(".f_treatmentContainer").append(response);
                        EphytoTreatmentController.updateTypeTreatment();
                        EphytoTreatmentController.updateTreatmentGroupeValidation();
                        initRefForAddTreatment();
                        EphytoTreatmentController.initTreatment();
                        configureMainButtons(EphytoTreatmentController.getTabMainContainer(), true);
                        focusOnErrorField();
                        removeFieldError();
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), false);
                        disableOtherErrorLinks("itemsTab", null, "itemFld", data.rank);
                        break;
                    case "showTreatment":
                        $itemListContainer.remove();
                        $itemsTab.find(".f_treatmentContainer").append(response);
                        EphytoTreatmentController.updateTypeTreatment();
                        EphytoTreatmentController.disableTreatmentForm();
                        configureMainButtons(EphytoTreatmentController.getTabMainContainer(), true);
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), false);
                       disableOtherErrorLinks();
                        break;
                    case "deleteTreatment":
                        $itemListContainer.remove();
                        $itemsTab.find(".f_treatmentContainer").append(response);
                        EphytoTreatmentController.updateTypeTreatment();
                        EphytoTreatmentController.disableTreatmentForm();
                        configureMainButtons(EphytoTreatmentController.getTabMainContainer(), true);
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), false);
                        break;
                    case "saveTreatment":
                        if (!responseHasErrors(response, EphytoTreatmentController.getErrorContainer())) {
                            $itemFormCtr.remove();
                            $itemsTab.find(".f_treatmentContainer").append(response);
                            showAjaxSuccessDialog(I18N['client.addOperationSuccessfully']);
                            configureMainButtons(EphytoTreatmentController.getTabMainContainer(), false);
                            enableAllErrorLinks();
                        }
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), false);
                        break;
                    case "updateTreatment":
                        if (!responseHasErrors(response, EphytoTreatmentController.getErrorContainer())) {
                            $itemFormCtr.remove();
                            $itemsTab.find(".f_treatmentContainer").append(response);
                            showAjaxSuccessDialog(I18N['client.modifyOperationSuccessfully']);
                            configureMainButtons(EphytoTreatmentController.getTabMainContainer(), false);
                            enableAllErrorLinks();
                        }
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), false);
                        break;
                    case "removeTreatment":
                        $itemFormCtr.remove();
                        $itemsTab.find(".f_treatmentContainer").append(response);
                        showAjaxSuccessDialog(I18N['client.deleteOperationSuccessfully']);
                        configureMainButtons(EphytoTreatmentController.getTabMainContainer(), false);
                        EphytoTreatmentController.removeErrorsOnDelete(data.params)
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), false);
                        break;
                    case "closeTreatment":
                        $itemFormCtr.remove();
                        $itemsTab.find(".f_treatmentContainer").append(response);
                        configureMainButtons(EphytoTreatmentController.getTabMainContainer(), false);
                        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), false);
                        enableAllErrorLinks();
                        break;
                }
            },
            error: function (response) {
            }
        });
        return false;
    },

    addTreatment: function () {
       configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), true);
        var params = {};
        params["conversationId"] = $("#conversationId").val()
        var data = {
            type: "GET",
            url: $("#addTreatmentUrl").val(),
            params: params,
            action: "addTreatment"
        }
        EphytoTreatmentController.CRUD(data);

    },

    editTreatment: function (itemRank) {
        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), true);
        var params = EphytoTreatmentController.setItemParams(itemRank);
        var data = {
            type: "GET",
            url: $("#editTreatmentUrl").val(),
            params: params,
            action: "editTreatment",
            rank: itemRank
        }
        EphytoTreatmentController.CRUD(data);

    },

    showTreatment: function (itemRank) {
        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), true);
        var params = EphytoTreatmentController.setItemParams(itemRank);
        var data = {
            type: "POST",
            url: $("#showTreatmentUrl").val(),
            params: params,
            action: "showTreatment"
        };
        EphytoTreatmentController.CRUD(data);
    },

    deleteTreatment: function (itemRank) {
        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(false), true);
        var params = EphytoTreatmentController.setItemParams(itemRank);
        var data = {
            type: "POST",
            url: $("#deleteTreatmentUrl").val(),
            params: params,
            action: "deleteTreatment"
        };
        EphytoTreatmentController.CRUD(data);
    },

    saveTreatment: function (element, itemRank) {
        if (EphytoTreatmentController.validateTreatment()) {
            configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), true);
            var params = EphytoTreatmentController.setItemParams(itemRank);
            var data = {
                type: "POST",
                url: $("#saveTreatmentUrl").val(),
                params: params,
                action: "saveTreatment"
            }
            EphytoTreatmentController.CRUD(data);
        }
    },

    updateTreatment: function (element, itemRank) {
        if (EphytoTreatmentController.validateTreatment()) {
            configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), true);
            var params = EphytoTreatmentController.setItemParams(itemRank);
            var data = {
                type: "POST",
                url: $("#updateTreatmentUrl").val(),
                params: params,
                action: "updateTreatment"
            };
            EphytoTreatmentController.CRUD(data);
        }
    },

    removeTreatment: function (element, itemRank) {
        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), true);
        var params = EphytoTreatmentController.setItemParams(itemRank);
        openConfirmDialog(I18N['client.deleteTreatmentConfirm'],
            function () {
                var data = {
                    type: "POST",
                    url: $("#removeTreatmentUrl").val(),
                    params: params,
                    action: "removeTreatment"
                }
                EphytoTreatmentController.CRUD(data);

            }, // no button handler
            function () {
                configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), false);
            }, // close button handler
            function () {
                configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), false);
            }
        )
    },

    closeTreatment: function (itemNumber) {
        configureFormFields(EphytoTreatmentController.getTreatmentFieldsToConfigure(true), true);
        var params = EphytoTreatmentController.setItemParams(itemNumber);
        var data = {
            type: "GET",
            url: $("#closeTreatmentUrl").val(),
            params: params,
            action: "closeTreatment"
        }
        EphytoTreatmentController.CRUD(data);
    },

    initTreatment: function () {
        cancelEnterKey();
        applyRegexpRestrictionToListOfInputs($("#treatments .f_treatmentContainer [regexp], #treatments .f_treatmentContainer [regexpForRule]"));
       formatNumberInputOnChange($("#treatments .f_treatmentContainer .wf-decimal-input, .wf-monetary-input, .wf-quantity-input, .wf-exchange-rate-input, .wf-custom-input"));
    },

    validateTreatment: function () {
        var targetFields = $(".mainTreatmentForm").find(".wfinput");
        return validateFormTreatment(targetFields);
    },

    removeItemFieldError: function () {
        var allItemElems = $("#items #itemContainer .wfinput");
        removeError(allItemElems, "itemErrorContainer");
    },

    getTreatmentFields: function () {
        var itemFields = ["itemCode", "itemName", "itemRank", "addItemLink"]
        return itemFields;
    },

    getTreatmentFieldsToConfigure: function (isTreatmentForm) {
        var treatmentFields
        if (isTreatmentForm == true) {
            treatmentFields = [".treatmentMainBtn"]
        } else {
            treatmentFields = [".treatmentListBtn"]
        }
        return treatmentFields
    },

    setItemParams: function (rank) {
        var treatmentFieldsToSet = {
            itemNumber: "itemNumber",
            warehouse: "warehouse",
            applicatorCode: "applicatorCode",
            applicatorAgreement: "applicatorAgreement",
            applicatorNameAddress: 'applicatorNameAddress',
            disinfectionCertificateRef: "disinfectionCertificateRef",
            disinfectionCertificateDate: "disinfectionCertificateDate",
            treatmentType: "treatmentType",
            treatmentSartDate: "treatmentSartDate",
            treatmentSartTime: "treatmentSartTime",
            treatmentEndDate: "treatmentEndDate",
            treatmentEndTime: "treatmentEndTime",
            usedProducts: "usedProducts",
            concentration: "concentration",
            treatmentDuration: "treatmentDuration"
            //,j_treatmentDuration :"j_treatmentDuration"
        }
        return setParamsValue(rank, null, treatmentFieldsToSet)
    },

    getErrorContainer: function () {
        var errorContainer = $("#treatmentFormCtr").find("#treatmentErrors");
        return errorContainer;
    },
    manageRowToUpdate: function (rowNumber, editedRow, rowToUpdate) {
        manageRowToUpdate(EphytoTreatmentController.getTreatmentFields(), rowNumber, editedRow, rowToUpdate)
    },

    disableTreatmentForm: function () {
        var itemTab = $("#treatmentFormCtr")
        disableFormTreatment(itemTab)
    },

    renderInnerForm: function (response) {
        var ctrToRemove = $("#treatmentListContainer");
        var itemTab = $("#ephytoGenTab").find(".f_treatmentContainer");
        renderActionResults(ctrToRemove, itemTab, response)
    },
    initTempOperValue: function () {
        $("#tempOper").val('tempValue')
    },
    removeErrorsOnDelete: function (params) {
        var elStr = params["rank"] + "_itemsTab"
        removeAllTabErrors("treatmentTab")
    },
    getTabMainContainer: function () {
        var ctr = $("#treatmentTab");
        return ctr;
    },
    updateTypeTreatment :function(){
        if($.inArray($("#productTypeCode").val().trim(), EphytoTreatmentController.getExceptionsProducts()) > -1){
            if($("#treatmentType").val()  === I18N['client.LONG_SOUS_BACHE']){
                $("#treatmentType").val("");
            }
        }else if ($.inArray($("#productTypeCode").val().trim(), EphytoTreatmentController.getMangueProducts()) > -1){
            $("#treatmentType").val(I18N['client.APPROCH_SYSTEMIQUE']);
        }else{
            $("#treatmentType").val(I18N['client.LONG_SOUS_BACHE']);
        }
    },
    initTypeTreatment :function(){
        if($.inArray($("#productTypeCode").val().trim(), EphytoTreatmentController.getExceptionsProducts()) > -1){
            $("#treatmentType").val("");
        }else if ($.inArray($("#productTypeCode").val().trim(), EphytoTreatmentController.getMangueProducts()) > -1){
            $("#treatmentType").val(I18N['client.APPROCH_SYSTEMIQUE']);
        }else{
            $("#treatmentType").val(I18N['client.LONG_SOUS_BACHE']);
        }
    },
    updateTreatmentGroupeValidation : function(){
        var treatmentFieldsException = new Array();
        var treatmentExcludeFieldsException = new Array();
        var treatmentProtectionFieldsException =new Array("treatmentSartDate", "treatmentSartTime", "treatmentEndDate", "treatmentEndTime","treatmentDuration");
        $("#tempProtection").css("display", "none");
        var exceptionGroups = {
            heureDebut: "treatmentSartDate",
            heureFin: "treatmentEndDate"
        }
        var productTypeCode = $("#productTypeCode").val();
        var selectedvalue = $('select#treatment').val();

        var treatmentSartDate = $("#treatmentSartDate").val();
        var treatmentEndDate = $("#treatmentEndDate").val();
        var treatmentDuration = $("#treatmentDuration").val();

        if(productTypeCode === I18N['client.DIVERS'] && selectedvalue === I18N['client.YES']){
            $("#appMainForm").validate();
            $("#appMainForm").validate().groups = exceptionGroups;
            treatmentFieldsException.push("treatmentType","treatmentSartDate","treatmentEndDate","usedProducts","concentration","treatmentDuration","disinfectionCertificateRef","disinfectionCertificateDate");
            treatmentExcludeFieldsException.push("treatmentSartTime","treatmentEndTime");
            updateRuleOnFields(treatmentFieldsException);
            removeRuleOnFields(treatmentExcludeFieldsException);
        }else if(productTypeCode === I18N['client.BOIS'] && selectedvalue === I18N['client.YES']){

            if (treatmentSartDate.trim()!='' || treatmentEndDate.trim()!='' || treatmentDuration.trim()!=''){
                treatmentFieldsException.push("treatmentType","usedProducts","concentration","treatmentDuration","treatmentSartDate","treatmentEndDate");
            }else if (treatmentSartDate.trim()=='' && treatmentEndDate.trim()=='' && treatmentDuration.trim()==''){
                treatmentFieldsException.push("treatmentType","usedProducts","concentration");
            }else
            {   
                treatmentFieldsException.push("treatmentType","usedProducts","concentration");
            }
            removeRuleOnFields(treatmentProtectionFieldsException);
            updateRuleOnFields(treatmentFieldsException);
            displayTempProtect(treatmentSartDate,treatmentEndDate,treatmentDuration);
         }else if($.inArray(productTypeCode, EphytoTreatmentController.getExceptionsProducts()) > -1 && selectedvalue === I18N['client.NO']){
                  treatmentFieldsException.push("warehouse","applicatorCode","disinfectionCertificateRef","disinfectionCertificateDate","treatmentType","treatmentSartDate","treatmentSartTime","treatmentEndDate","treatmentEndTime","usedProducts","concentration","treatmentDuration");
                  removeRuleOnFields(treatmentFieldsException);
         }
      },
      getExceptionsProducts : function() {
          var exceptionProducts = new Array(I18N['client.FRUIT'],I18N['client.BOIS'],I18N['client.DIVERS']);
          return exceptionProducts
      },
    getMangueProducts : function() {
        var mangueProducts = new Array(I18N['client.MANGUE']);
        return mangueProducts
    }
}

function applyProtectionRule(){
    EphytoTreatmentController.updateTreatmentGroupeValidation();
}

function displayTempProtect(treatmentSartDate,treatmentEndDate,treatmentDuration){
    if (treatmentSartDate.trim()=='' && treatmentEndDate.trim()=='' && treatmentDuration.trim()==''){
        $("#tempProtection").css("display", "block");

    }else {
        $("#tempProtection").css("display", "none");
    }
}


function initEphytoGenDatePickersTreatment() {
    var ephytoDateFields = new Array("treatmentSartDate","treatmentEndDate","disinfectionCertificateDate")
    $.each(ephytoDateFields, function () {
        var field = $("#" + this);
        if (typeof field.val() != "undefined") {
            if (field.hasClass('generate-datepicker')) {
                makeDatepicker(field);
            }
        }
    });
}