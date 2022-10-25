/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 11/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

function calculateduration() {
    var elementSartTime = "treatmentSartTime";
    var elementSartDate = "treatmentSartDate";
    var elementEndTime = "treatmentEndTime";
    var elementEndDate = "treatmentEndDate";
    var newStartDate
    var newEndDate
    var isvalidStart = false
    var isvalidEnd = false
    $('#appMainForm').validate();
    if ($("#appMainForm").validate().element("[id=" + elementSartTime + "]") && $("#appMainForm").validate().element("[id=" + elementSartDate + "]")) {
        var splitStartDate = splitDate($("#treatmentSartDate").val());
        console.log('splitStartDate:' + splitStartDate)
        var splitStartTime = splitTime($("#treatmentSartTime").val());
        console.log('splitStartTime:' + splitStartTime)
        var newStartDate = createNewDate(splitStartDate, splitStartTime);
        isvalidStart = true
        console.log('newStartDate:' + newStartDate)
    }
    if ($("#appMainForm").validate().element("[id=" + elementEndTime + "]") && $("#appMainForm").validate().element("[id=" + elementEndDate + "]")) {
        var splitEndDate = splitDate($("#treatmentEndDate").val());
        console.log('splitEndDate:' + splitEndDate)
        var splitEndTime = splitTime($("#treatmentEndTime").val());
        console.log('splitEndTime:' + splitEndTime)
        var newEndDate = createNewDate(splitEndDate, splitEndTime);
        isvalidEnd = true
        console.log('newEndDate:' + newEndDate)
    }
    if(isvalidStart == true && isvalidEnd == true){
        var hourDiff = newEndDate - newStartDate; //in ms
        var secDiff = hourDiff / 1000; //in s
        var minDiff = hourDiff / 60 / 1000; //in minutes
        var hDiff = hourDiff / 3600 / 1000; //in hours
        var humanReadable = {};
        humanReadable.hours = Math.floor(hDiff);
        humanReadable.minutes = minDiff - 60 * humanReadable.hours;
        console.log('humanReadable:' + humanReadable.hours +  "" + humanReadable.minutes)
        if(humanReadable.hours != 'NaN' || humanReadable.hours != NaN || humanReadable.hours != 'NaNNaN'){
            $("#treatmentDuration").val(humanReadable.hours)
            $("#j_treatmentDuration").val(humanReadable.hours)
        }else{
            $("#treatmentDuration").val("")
            $("#j_treatmentDuration").val("")
        }

    }else{
        $("#treatmentDuration").val("")
        $("#j_treatmentDuration").val("")
    }

}

function splitDate(element) {
    return element.split("/");
}

function splitTime(element) {
    return element.split(":");
}

function createNewDate(elementdate, elementtime) {
    return new Date(elementdate[2], elementdate[1], elementdate[0], elementtime[0], elementtime[1], elementtime[2])
}

function getHour(element){

}

function formatDecimalValue(field){
    var fieldVal= $("#"+field).val()
    if(fieldVal!==null && fieldVal.trim()!==""){
        var fieldValue  = getNumericValue(field) ;
        updateValue($("#"+field),fieldValue);
    }

}



function updateValue($element,value){
    $element.val(value);
    $element.formatNumber({format:$("#decimalNumberFormat").val(), locale: $('#locale').val()});
}

function getNumericValue(fieldName) {
    var numeric = parseFloat(reformatNumber($("#" + fieldName).val()));
    if (isNaN(numeric)) {
        numeric = 0;
    }
    return numeric;
}

function formatGoodDecimalValue(field){
    console.log("in formatGoodDecimalValue  field = "+$("#"+field).val())
    var fieldVal= $("#"+field).val()
    var fieldUTFValue= $("#dialogItemGoodPopup #"+field+"UTF").val(fieldVal);
    console.log("in formatGoodDecimalValue  fieldUTFValue :"+ fieldUTFValue)
    if(fieldVal!==null && fieldVal.trim()!==""){
        var fieldValue  = getNumericValue(field) ;
        updateValue($("#"+field),fieldValue);
    }
}