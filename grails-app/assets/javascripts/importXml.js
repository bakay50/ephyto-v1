/**
 * Created by DEV22 on 4/15/2016.
 */

function initXmlUploadAjaxForm() {
    var uploadDialogObject = {
        uploadDialogId: "importXmlDialog",
        fileInputId: "xmlFile",

        onUploadCompleteFunction: function (xhr) {
            onXMLUploadComplete(xhr.responseText);
        }
    };

    var submitButtonObject = {
        uploadDialogId: "importXmlDialog",
        submitButtonId: "startUpload",
        fileInputId: "xmlFile",
        errorMessage: xmlExtensionAlertMsg,
        acceptedFormatsArrayInLowerCase: $("#xmlAcceptedFormats").val().split(',')
    };

    onStartUploadButtonClick(submitButtonObject);
    onUploadDialogFireButtonClick("importXmlDialog", "xmlImportButton");
    initUploadDialog(uploadDialogObject);
}

function onXMLUploadComplete(serverResponse) {

    if (serverResponse.indexOf("errorContainer xmlError") == -1) {
        $("#importXmlDialog #domainName").val("ephytoGen");
        $("#importXmlDialog").modal("hide");
        $("#formContents").html(serverResponse);
        updateAction();
        initDocumentAfterAjaxOperation();
    } else {
        var xmlErrors = $("#xmlErrors");
        xmlErrors.html("<div></div>");
        $("#xmlErrors").html(serverResponse);
        $("#xmlErrors").addClass("wf-error-bold");

    }
}

function updateAction(){
    $("#appMainForm").attr("action","/ephyto/ephytoGen/save");
}

function initDocumentAfterAjaxOperation() {
    initDocument();
    reApplyRegexForInputAfterXmlImport();
}

function reApplyRegexForInputAfterXmlImport(){
    var $elementsToApply = $(".wfinput").filter(function(){
        if($(this).attr("regexp")){
            return $(this);
        }else{
            return false;
        }
    });
    if(applyRegexpRestrictionToListOfInputs !== 'undefined'){
        applyRegexpRestrictionToListOfInputs($elementsToApply);
    }
}