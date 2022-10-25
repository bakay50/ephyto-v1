taglib.validation.applyRegexpRuleToListOfNumericInputs = function () {
    $.validator.addMethod("maxLengthField", function (value, element, param) {
        if (value) {
            value = reformatDelimiter(value);
            if (value.indexOf('.') !== -1) {
                var valueArr = value.split(".");
                return valueArr[0].length <= param[0] && valueArr[1].length <= param[1]
            } else {
                return value.length <= param[0]
            }
        }
        return true;
    }, I18N['client.incorrectFormat']);
    $(".wf-decimal-input, .wf-monetary-input, .wf-quantity-input, .wf-exchange-rate-input, .wf-bigDecimal-input").each(function () {
        var regexpForRule = $(this).attr('regexpForRule');
        var maxLength = 0;
        var maxFractionDigits = 0;
        if (regexpForRule) {
            if (regexpForRule.indexOf(",") !== -1) {
                maxLength = regexpForRule.split(",")[0];
                maxFractionDigits = regexpForRule.split(",")[1];
            } else {
                maxLength = regexpForRule.split(",")[0];
            }
        }
        if (maxLength > 0) {
            $(this).rules('add', {maxLengthField: [maxLength, maxFractionDigits]});
        }
    });
}