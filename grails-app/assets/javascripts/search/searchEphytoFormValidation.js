$(document).ready(function () {

    $.validator.setDefaults({
        errorClass: 'form_error',
        errorElement: 'label'
    });


    $("#searchEphytoGenForm").validate({
        invalidHandler: function (form, validator) {
            var elements = validator.invalidElements();
            var errors = validator.numberOfInvalids();
            if (elements || errors) {
                var firstInvalidElement = $(validator.errorList[0].element);
                $('html,body').scrollTop(firstInvalidElement.offset().top);
                firstInvalidElement.focus();
            }
        },
        ignore: [],
        focusInvalid: false,
        rules: {
            requestDate: {
                required: {
                    depends: function () {
                        return isEmptyFields('requestDate','requestDate')
                    }
                }
            }
            ,
            dockingPermissionDate: {
                required: {
                    depends: function () {
                        return isEmptyFields('dockingPermissionDate','dockingPermissionDate')
                    }
                }
            },
            phytosanitaryCertificateDate: {
                required: {
                    depends: function () {
                        return isEmptyFields('phytosanitaryCertificateDate','phytosanitaryCertificateDate')
                    }
                }
            },
            requestDateTo: {
                required: {
                    depends: function () {
                        return isEmptyFields('requestDate','requestDateTo')
                    }
                }
            },
            dockingPermissionDateTo: {
                required: {
                    depends: function () {
                        return isEmptyFields('dockingPermissionDate','dockingPermissionDateTo')
                    }
                }
            },
            phytosanitaryCertificateDateTo: {
                required: {
                    depends: function () {
                        return isEmptyFields('phytosanitaryCertificateDate','phytosanitaryCertificateDateTo')
                    }
                }
            },
            disinfectionCertificateDateTo:{
                required: {
                    depends: function () {
                        return isEmptyFields('disinfectionCertificateDate','disinfectionCertificateDateTo')
                    }
                }
            },
            disinfectionCertificateDate:{
                required: {
                    depends: function () {
                        return isEmptyFields('disinfectionCertificateDate','disinfectionCertificateDate')
                    }
                }
            }

        },

        messages: {
            requestDate: { required: "" },
            requestDateTo: { required: "" },
            dockingPermissionDate: {required: ""},
            dockingPermissionDateTo: {required: ""},
            phytosanitaryCertificateDate: {required: ""},
            phytosanitaryCertificateDateTo:{required: ""},
            disinfectionCertificateDate: { required: "" },
            disinfectionCertificateDateTo: { required: "" }
        },
        errorPlacement: function (error, element) {
            var fieldsName = element.attr("name");
            var Opfields = ["op_phytosanitaryCertificateDate","op_dockingPermissionDate","op_requestDate","op_disinfectionCertificateDate"];
            if ($.inArray(fieldsName, Opfields) > -1) {
                resizeError(fieldsName);
            }
        }
    });
});

function isEmptyFields(opfieldName,fieldName) {
    var OpFieldsValue = $("#op_"+opfieldName).val();
    var Tofields = ["disinfectionCertificateDateTo","dockingPermissionDateTo","phytosanitaryCertificateDateTo"]
    if ($.inArray(fieldName, Tofields) > -1) {
        return OpFieldsValue == "between"
    } else {
        return OpFieldsValue != ""
    }
}

function resizeError(fieldsName){
    $('label.form_error[for="' + fieldsName + '"]').css({"margin-right": "36px"})
}