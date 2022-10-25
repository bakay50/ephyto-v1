grails.gorm.default.mapping = {
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTime, class: org.joda.time.DateTime
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDate, class: org.joda.time.LocalDate
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime, class: org.joda.time.LocalDateTime
}

jodatime.format.html5 = true
jodatime.format.org.joda.time.LocalDate = 'dd/MM/yyyy'
jodatime.format.org.joda.time.DateTime = "dd/MM/yyyy HH:mm:ss"
timeFormat = 'HH:mm:ss'

com.webbfontaine.plugin.utils.dateTimeConverter.dateTime = ['dd/MM/yyyy HH:mm:ss', 'dd/MM/yyyy']
com.webbfontaine.plugin.utils.concurrent.stripes = 256



searchConfig{
    limit=5000
}


// webservice url
rest {
    useWebService="N"
    sad {
        url="https://uatapp.webbfontaine.ci/esad/sad/declaration"
        enabled = true
    }
    isLocalhost = false
    jossoCookieString = "70DF5B12E506808BE9A2CB4969F11A29"  //param "jossoCookieString" value is mandatory if "isLocalhost" equals true
}

// limited number of treatment
treatment{
    maxTreatmentConfig = 5
    enabled = true
}

com {
    webbfontaine {
        conversation {
            accessTimeout = 20
        }
        grails {
            plugins {
                validation {
                    rules {
                        rulesWithNewInstance = true
                        publishDeepVerifyFinishedEvent = true
                        logRulesExecutionTime = true
                    }
                }
                layout {
                    locales = 'fr,en'
                }
                workflow {
                    searchResultActions.newWindow = false
                }
                utils {
                    dateTimeConverter {
                        localDate = ["dd/MM/yyyy", "yyyy-MM-dd", "MM/dd/yy"]
                        dateTime = ["dd/MM/yyyy HH:mm:ss"]
                    }
                }
            }
        }
        ephyto {
            search {
                ajaxSearch = true
            }
            fieldsConfig {

                // ephyto  fields configuration
                ephyto {
                    gen {
                        // fieldName :                 ['Null','Stored', 'Requested', 'Queried', 'Canceled', 'Rejected', 'Approved', 'Replaced','Signed']
                        /*********************************************
                         * start Header Fields
                         * *******************************************/
                        documentType                 = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        requestNumber                = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        requestDate                  = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        userReference                = ['O', 'O', 'P', 'O', 'P', 'P', 'P', 'P', 'P']
                        otReference                  = ['O', 'O', 'P', 'O', 'P', 'P', 'P', 'P', 'P']
                        ptReference                  = ['O', 'O', 'P', 'O', 'P', 'P', 'P', 'P', 'P']
                        dockingPermissionRef         = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        dockingPermissionDate        = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        phytosanitaryCertificateRef  = ['H', 'H', 'H', 'H', 'H', 'H', 'M', 'P', 'P']
                        phytosanitaryCertificateDate = ['H', 'H', 'H', 'H', 'H', 'H', 'M', 'P', 'P']
                        filingDate                   = ['H', 'H', 'H', 'H', 'H', 'H', 'M', 'P', 'P']
                        signatureDate                = ['H', 'H', 'H', 'H', 'H', 'H', 'M', 'P', 'P']
                        dockNumber                   = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        customsClearanceOfficeCode   = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        customsClearanceOfficeName   = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        declarationSerial            = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        declarationNumber            = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        declarationDate              = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        modeOfTransportCode          = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        modeOfTransportName          = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        meansOfTransport             = ['O', 'O', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        boardingDate                 = ['O', 'O', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        countryOfDestinationCode     = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        countryOfDestinationName     = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        placeOfUnloadingCode         = ['O', 'M', 'P', 'M', 'P', 'P', 'M', 'P', 'P']
                        placeOfUnloadingName         = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        proposedOperationDate	     = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        proposedOperationTime        = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        operDate                =      ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        operTime                =      ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        nameAndSurnameMinistryAgent  = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        titleMinistryAgent           = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        observations                 = ['O', 'O', 'P', 'O', 'P', 'P', 'P', 'O', 'O']
                        status                       = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        defaultQuantity              = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        defaultNetWeight             = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        defaultGrossWeight           = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        nextDocRef                   = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        previousDocRef               = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        treatment                    = ['O', 'O', 'P', 'O', 'P', 'P', 'P', 'P', 'P']

                        /*********************************************
                         * Names and Parties
                         * *******************************************/
                        realExportNameAddress        = ['O', 'O', 'P', 'O', 'P', 'P', 'M', 'P', 'P']
                        exporterCode                 = ['M', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        exporterName                 = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        exporterAddress              = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        exporterName                 = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        exporterAddress              = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        exporterNameAddress          = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        consigneeNameAddress         = ['M', 'O', 'P', 'O', 'P', 'P', 'M', 'P', 'P']
                        declarantCode                = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        declarantNameAddress         = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        applicantName                = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        applicantTelephone           = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        applicantEmail               = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        /*********************************************
                         * Goods
                         * *******************************************/
                        commodityCode                = ['M', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        commodityDescription         = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        productTypeCode              = ['M', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        productTypeName              = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        harvest                      = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        season                       = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        commercialDescriptionGoods   = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        botanicalName                = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        countryOfOriginCode          = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        countryOfOriginName          = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        packageMarks                 = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        packageCode                  = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        packageName                  = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        totalQuantity                = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        totalNetWeight               = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        totalGrossWeight             = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        volume                       = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        totalItems                   = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']


                    }

                    /*********************************************
                     * start Treatement Fields
                     * *******************************************/
                    itemTreatment{
                        itemNumber                   = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        warehouse                    = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        applicatorCode               = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        applicatorAgreement          = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        applicatorNameAddress        = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P']
                        disinfectionCertificateRef   = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        disinfectionCertificateDate  = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        treatmentType                = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        treatmentSartDate            = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        treatmentSartTime            = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        treatmentEndDate             = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        treatmentEndTime             = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        usedProducts                 = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        concentration                = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                        treatmentDuration            = ['O', 'O', 'P', 'M', 'P', 'P', 'O', 'P', 'P']
                    }


                    /*********************************************
                     * ItemGood
                     * *******************************************/
                    itemGood {
                        // fieldName : ['Null','Stored', 'Requested', 'Queried', 'Canceled', 'Rejected', 'Approved', 'Replaced']
                        itemRank                     = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        previousDocumentReference    = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        previousDocumentItem         = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        batchNumber                  = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        subBatchNumber               = ['O', 'O', 'P', 'O', 'P', 'P', 'O', 'P', 'P']
                        quantity                     = ['M', 'M', 'P', 'O', 'P', 'P', 'M', 'P', 'P']
                        remainingQuantiy             = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        netWeight                    = ['M', 'M', 'P', 'O', 'P', 'P', 'M', 'P', 'P']
                        remainingNetWeight           = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        grossWeight                  = ['M', 'M', 'P', 'O', 'P', 'P', 'M', 'P', 'P']
                        remainingGrossWeight         = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        analysisResult               = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        potted                       = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                        observations                 = ['H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H']
                    }

                    /*********************************************
                     * start Attachment
                     * *******************************************/
                    attachment {
                        // fieldName : ['Null','Stored', 'Requested', 'Queried', 'Canceled', 'Rejected', 'Approved', 'Replaced']
                        attNumber = ['P', 'P', 'P', 'P', 'P', 'P', 'P','P','P']
                        docType   = ['M', 'M', 'P', 'M', 'P', 'P', 'M','P','P']
                        docRef    = ['M', 'M', 'P', 'M', 'P', 'P', 'M','P','P']
                        docDate   = ['M', 'M', 'P', 'M', 'P', 'P', 'M','P','P']
                        attDoc    = ['M', 'M', 'P', 'M', 'P', 'P', 'M','P','P']
                    }
                    /*********************************************
                     * end Attachment
                     * *******************************************/

                    /*********************************************
                     * start EphytoLog
                     * *******************************************/
                    ephytoLog {
                        // fieldName : ['Null','Stored', 'Requested', 'Queried', 'Canceled', 'Rejected', 'Approved', 'Replaced']
                        date      = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P','P']
                        userlogin = ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P','P']
                        message   = ['P', 'P', 'M', 'M', 'P', 'M', 'P', 'P','P']
                    }
                    /*********************************************
                     * end ephytoLog
                     * *******************************************/
                    /*********************************************
                     * start Applicator
                     * *******************************************/
                    applicator {
                        //['register', 'update', 'cancel', 'activate']
                        agreement     = ['M', 'P', 'P', 'P']
                        code          = ['M', 'M', 'P', 'P']
                        nameAddress   = ['M', 'M', 'P', 'P']
                        status        = ['T', 'P', 'P', 'P']
                    }
                    /*********************************************
                     * end Applicator
                     * *******************************************/
                }
            }

            customs.email = "apaladan@wfgmb.com"
        }
    }
}

grails.gorm.default.constraints = {
    '*'(nullable: true)
}

grails.databinding.dateFormats = ["dd/MM/yyyy", "yyyy-MM-dd"]
dateFormat = 'dd/MM/yyyy'
timeFormat = 'HH:mm:ss'
jodatime.format.html5 = true
jodatime.format.org.joda.time.LocalDate = dateFormat
jodatime.format.org.joda.time.DateTime = "${dateFormat} ${timeFormat}"
jodatime.format.org.joda.time.LocalDateTime = "${dateFormat} ${timeFormat}"
JQueryDateFormat = "dd/mm/yy"
bootstrapDate = 'DD/MM/YYYY'
com.webbfontaine.grails.plugins.utils.concurrent.stripes = 256
jodatime.format.html5 = true
jodatime.format.org.joda.time.LocalDateEn = 'MM/dd/yyyy'
timeFormat = 'HH:mm:ss'
timeFormatEnglish = 'hh:mm:ss a'
timeFormats = 'hh:mm:ss'
attDocHasDate = true
//regExp for date in format dd-MMM-yyyy
DatePattern = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])/20\\d\\d\$"
attDocHasDate = true
attachmentMaxSizeBytes = 2 * 1024 * 1024
attachmentPageMaxSizeBytes = 2 * 1024 * 1024
xmlMaxSizeBytes = 3 * 1024 * 1024//3 MB
xlsMaxSizeBytes = 30 * 1024 * 1024
attachmentAcceptedFormats = ['pdf']
xlsAcceptedFormats = ['xls']
xmlAcceptedFormats = ['xml']
attachmentDomainClassName = 'com.webbfontaine.ephyto.gen.attachment.Attachment'
timeFormat = 'HH:mm:ss'

//grails.mail.default.from=""
grails {
    mail {
        host = "smtp.webbfontaine.ci"
        port = 25
        username = "gucealerts-noreply@guce.ci"
        password=""
        props = ["mail.smtp.auth":"false",
                 "mail.smtp.socketFactory.port":"25"]

    }
}


//grails.mail.default.from = "efemci.wf@gmail.com"

// wf-search configuration
pagination {
    searchResultMax = 10
}


userProperty {
    checkingEnabled = true
    allowMultipleProperty {
        dec = false
        tin = false
        officeAccess = false
    }
}

com.webbfontaine.grails.plugins.validation.refConfig.appDomainFieldsDescriptionPostFix = "name"
com.webbfontaine.grails.plugins.taglibs.fieldConfig.treatReadonlyAsDisabled = false
decimalNumberPattern = "15,3"
wholeNumberFormat = "#,##0"
monetaryNumberFormat = "#,###"
decimalNumberFormat = "#,###.000"
exchangeRateFormat = "#,##0.000"
quantityNumberFormat = "#,##0.000"
grails.mail.default.from ="gucealerts-noreply@guce.ci" //"efemci.wf@gmail.com"
grails.mail.enabled = true
defaultLanguage= Locale.FRENCH
bigDecimalNumberFormat = "#,##0.00"

com{
    webbfontaine{
        ephyto{
            enableDigitalSignature = false
            showDigitalSignature = true
            digitalSignatureAdditionalDetails{
                location = "Ivory Coast"
                rectangle{
                    llx = 500 //Left
                    lly =  25 //Bottom
                    urx = 400 //Right
                    ury =  60 //Top
                }
            }
            gws{
                sign{
                    url ="https://uatapp.webbfontaine.ci/gws/api/sign" //"<host>/gws/api/sign"
                    username = 'gws_signer' //'<user>'
                    password =  '12345678' //'<pass>'
                }
            }
        }
    }
}

com{
    webbfontaine{
        ephyto{
            productCode {
                coffee{
                    defaultQuantity =420
                }
                cacao {
                    defaultQuantity =385
                }
            }
        }
    }
}
com.webbfontaine.grails.plugins.layout.locales = 'fr,en'
