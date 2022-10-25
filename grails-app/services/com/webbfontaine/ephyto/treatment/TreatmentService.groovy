package com.webbfontaine.ephyto.treatment

import com.webbfontaine.ephyto.DataBindingHelper
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.EphytoTreatment
import grails.gorm.transactions.Transactional
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.joda.time.LocalDate
import static com.webbfontaine.ephyto.TypeCastUtils.*

@Transactional
class TreatmentService {

    def docVerificationService
    def conversationService

    def addTreatment(Map params, EphytoGen ephytoGenInstance) {
        boolean added = false
        EphytoTreatment treatment = new EphytoTreatment()
        conversationService.bindData(treatment, params, DataBindingHelper.getEphytoTreatmentAcceptableFields(ephytoGenInstance))
        //treatment?.treatmentDuration = params.j_treatmentDuration
        treatment.ephytoGen = ephytoGenInstance
        treatment.itemNumber = ephytoGenInstance.getTreatmentSequenceNumber()
        if (isItemInstanceValid(treatment)) {
            ephytoGenInstance.addTreatment(treatment)
            added = true
        }
        return [added: added, ephytoTreatment: treatment]
    }

    def updateTreatment(Map params, EphytoGen ephytoGenInstance) {
        def rank = toInteger(params.itemNumber)
        EphytoTreatment tempEphytoTraitement = new EphytoTreatment(ephytoGenInstance.getTreatement(rank).properties)
        conversationService.bindData(tempEphytoTraitement, params, DataBindingHelper.getEphytoTreatmentAcceptableFields(ephytoGenInstance))
        //tempEphytoTraitement?.treatmentDuration = params.j_treatmentDuration
        boolean updated = false
        if (isItemInstanceValid(tempEphytoTraitement)) {
            ephytoGenInstance.getTreatement(rank).properties = tempEphytoTraitement.properties
            updated = true
        }
        return [updated: updated, tempEphytoTreatment: tempEphytoTraitement]
    }

    def removeTreatment(EphytoGen ephytoGenInstance) {
        def rank = currentRequestParams().itemNumber?toInteger(currentRequestParams().itemNumber): 0
        EphytoTreatment treatment = ephytoGenInstance.getTreatement(rank)
        boolean removed
        if (treatment) {
            ephytoGenInstance.removeTreatement(rank)
            removed = true
        } else {
            removed = false
            treatment = EphytoTreatment.newInstance()
            treatment.errors.rejectValue('rank', 'treatment.errors.notFound', 'Cannot find Item from the list')
        }
        return [removed: removed, ephytoTreatment: treatment]
    }



    def isItemInstanceValid(EphytoTreatment treatment) {
        return docVerificationService.deepVerify(treatment) && treatment.validate()
    }

    private static Map currentRequestParams() {
        GrailsWebRequest.lookup().params
    }

    def setItemParams(params, EphytoGen ephytoGenInstance) {
        ephytoGenInstance?.metaClass?.getProperties()?.each {
            def propName = it.name
            def paramsValue = params."${propName}"
            if (paramsValue) {
                def propType = it.type
                if (propType == LocalDate) {
                    ephytoGenInstance."${propName}" = toLocalDate(paramsValue?.toString())
                } else if (propType == Integer) {
                    ephytoGenInstance."${propName}" = toInteger(paramsValue?.toString())
                }  else if (propType == String) {
                    ephytoGenInstance."${propName}" = paramsValue
                } else if (propType == BigDecimal) {
                    ephytoGenInstance."${propName}" = toBigDecimal(paramsValue?.toString())
                }
            }
        }
    }

    def setInitialValues(EphytoTreatment treatmentInstance, EphytoGen ephytoGenInstance, rank) {
        treatmentInstance.itemNumber = rank
        treatmentInstance.ephytoGen = ephytoGenInstance
    }

}
