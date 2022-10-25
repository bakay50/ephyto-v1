package com.webbfontaine.ephyto.config

import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.gen.EphytoGen
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll

import static com.webbfontaine.ephyto.constants.Statuses.ST_QUERIED
import static com.webbfontaine.ephyto.constants.Statuses.ST_REQUESTED
import static com.webbfontaine.ephyto.constants.Statuses.ST_STORED

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@TestMixin(GrailsUnitTestMixin)
class FieldsConfigurationSpec extends Specification {

    @Unroll("Test #fieldName is editable if EphytoGen status is #status")
    def "Test configuration for EphytoGen fields should be set accordingly"() {
        when:
        def ephytoGen = new EphytoGen(status: status)
        ephytoGen.setIsDocumentEditable(true)

        then:
        ephytoGen.isFieldEditable(fieldName)

        where:
        fieldName                          | status
        'exporterCode'                     | null
        'exporterCode'                     | ST_STORED
        'exporterCode'                     | ST_QUERIED
        'userReference'                    | null
        'userReference'                    | ST_STORED
        'userReference'                    | ST_QUERIED
        'dockingPermissionRef'             | null
        'dockingPermissionRef'             | ST_STORED
        'dockingPermissionRef'             | ST_QUERIED
        'dockingPermissionDate'            | null
        'dockingPermissionDate'            | ST_STORED
        'dockingPermissionDate'            | ST_QUERIED
        'dockNumber'                       | null
        'dockNumber'                       | ST_STORED
        'dockNumber'                       | ST_QUERIED
        'customsClearanceOfficeCode'       | null
        'customsClearanceOfficeCode'       | ST_STORED
        'customsClearanceOfficeCode'       | ST_QUERIED
        'declarationSerial'                | null
        'declarationSerial'                | ST_STORED
        'declarationSerial'                | ST_QUERIED
        'declarationNumber'                | null
        'declarationNumber'                | ST_STORED
        'declarationNumber'                | ST_QUERIED
        'declarationDate'                  | null
        'declarationDate'                  | ST_STORED
        'declarationDate'                  | ST_QUERIED
    }


    @Unroll("Test #fieldName is editable if EphytoGen status is #status")
    def "Test configuration for EphytoGen Treatment should be set accordingly"() {
        when:
        def ephytoGen = new EphytoGen(status: status)

        ephytoGen.setIsDocumentEditable(true)

        then:
        ephytoGen.isTreatmentEditable(fieldName)

        where:
        fieldName                       | status
        'warehouse'                     | null
        'warehouse'                     | ST_STORED
        'warehouse'                     | ST_QUERIED
        'applicatorCode'                | null
        'applicatorCode'                | ST_STORED
        'applicatorCode'                | ST_QUERIED
        'disinfectionCertificateRef'    | null
        'disinfectionCertificateRef'    | ST_STORED
        'disinfectionCertificateRef'    | ST_QUERIED
        'treatmentType'                 | null
        'treatmentType'                 | ST_STORED
        'treatmentType'                 | ST_QUERIED
        'treatmentSartDate'             | null
        'treatmentSartDate'             | ST_STORED
        'treatmentSartDate'             | ST_QUERIED
        'treatmentSartTime'             | null
        'treatmentSartTime'             | ST_STORED
        'treatmentSartTime'             | ST_QUERIED
        'treatmentEndTime'              | null
        'treatmentEndTime'              | ST_STORED
        'treatmentEndTime'              | ST_QUERIED
        'usedProducts'                  | null
        'usedProducts'                  | ST_STORED
        'usedProducts'                  | ST_QUERIED
        'concentration'                 | null
        'concentration'                 | ST_STORED
        'concentration'                 | ST_QUERIED
    }

    @Unroll("Test #fieldName is mandatory if EphytoGen status is #status")
    def "Test field is mandatory on given status"() {
        when:
        def ephytoGen = new EphytoGen(status: status)
        ephytoGen.setIsDocumentEditable(true)

        then:
        ephytoGen.isFieldEditable(fieldName)

        where:
        fieldName                         | status
        'exporterCode'                    | ST_STORED
        'exporterCode'                    | ST_QUERIED
        'userReference'                   | ST_STORED
        'userReference'                   | ST_QUERIED
        'dockingPermissionRef'            | ST_STORED
        'dockingPermissionRef'            | ST_QUERIED
        'dockingPermissionDate'           | ST_STORED
        'dockingPermissionDate'           | ST_QUERIED
        'modeOfTransportCode'             | ST_STORED
        'modeOfTransportCode'             | ST_QUERIED
        'meansOfTransport'                | ST_STORED
        'meansOfTransport'                | ST_QUERIED
        'boardingDate'                    | ST_STORED
        'boardingDate'                    | ST_QUERIED
        'countryOfDestinationCode'        | ST_STORED
        'countryOfDestinationCode'        | ST_QUERIED
        'placeOfUnloadingCode'            | ST_STORED
        'placeOfUnloadingCode'            | ST_QUERIED
    }

    @Unroll("Test #fieldName is mandatory if EphytoGen status is #status")
    def "Test field Treatment is mandatory on given status"() {
        when:
        def ephytoGen = new EphytoGen(status: status)
        ephytoGen.setIsDocumentEditable(true)

        then:
        ephytoGen.isTreatmentEditable(fieldName)

        where:
        fieldName                      | status
        'warehouse'                    | ST_STORED
        'warehouse'                    | ST_QUERIED
        'applicatorCode'               | ST_STORED
        'applicatorCode'               | ST_QUERIED
        'disinfectionCertificateRef'   | ST_STORED
        'disinfectionCertificateRef'   | ST_QUERIED
        'treatmentType'                | ST_STORED
        'treatmentType'                | ST_QUERIED
        'treatmentSartDate'            | ST_STORED
        'treatmentSartDate'            | ST_QUERIED
        'treatmentSartTime'            | ST_STORED
        'treatmentSartTime'            | ST_QUERIED
        'treatmentEndDate'             | ST_STORED
        'treatmentEndTime'             | ST_QUERIED
        'usedProducts'                 | ST_STORED
        'usedProducts'                 | ST_QUERIED
        'concentration'                | ST_STORED
        'concentration'                | ST_QUERIED
    }

    @Unroll("Test #fieldName is editable if EphytoGen attachment status is #status")
    def "Test configuration for EphytoGen attachment should be set accordingly"() {
        when:
        def ephytoGen = new EphytoGen(status: status)
        ephytoGen.setIsDocumentEditable(true)

        then:
        ephytoGen.isAttachmentEditable(fieldName)

        where:
        fieldName       | status
        'docType'       | null
        'docType'       | ST_STORED
        'docType'       | ST_QUERIED
        'docRef'        | null
        'docRef'        | ST_STORED
        'docRef'        | ST_QUERIED
        'docRef'        | null
        'docRef'        | ST_STORED
        'docRef'        | ST_QUERIED
    }

}
