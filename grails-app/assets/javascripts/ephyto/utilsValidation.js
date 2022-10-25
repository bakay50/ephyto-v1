/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 11/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
var isValidateElement ={
    addRuleFor: function (element) {
        $('#appMainForm').validate();
        $("#" + element).rules('add', {
            required: true
        });
        $("#" + element).prop("required", true);
        $("#" + element).addClass('mandatory');
        $("#appMainForm").validate().element("[id=" + element + "]");
    },
    removeRuleFor: function (element) {
        $('#appMainForm').validate();
        $("#" + element).removeClass("error-border");
        $("#" + element).rules('remove');
        $("#" + element).prop("required", false);
        $("#" + element).removeClass('mandatory');
        $("#appMainForm").validate().element("[id=" + element + "]");
    }

}