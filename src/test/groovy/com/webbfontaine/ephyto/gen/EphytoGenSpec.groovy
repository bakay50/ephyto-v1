package com.webbfontaine.ephyto.gen

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.webbfontaine.ephyto.ConstraintUnitSpec
import spock.lang.Unroll

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(EphytoGen)
class EphytoGenSpec extends ConstraintUnitSpec {

    def setup() {

    }

    @Unroll("test EphytoGen all constraints #field is #error")
    def "test all EphytoGen constraints"() {
        when: "a new instance of EphytoGen"
        def obj = new EphytoGen("$field" : val)

        then: "validate"
        validateConstraints(obj, field, error)

        where:
        error                | field                         || val
        'maxSize.exceeded'   | 'modeOfTransportCode'         || getCustomTextWithLength(4)
        'maxSize.exceeded'   | 'requestNumber'               || getCustomTextWithLength(14)
        'maxSize.exceeded'   | 'otReference'                 || getCustomTextWithLength(31)
        'maxSize.exceeded'   | 'ptReference'                 || getCustomTextWithLength(31)
        'maxSize.exceeded'   | 'customsClearanceOfficeCode'  || getCustomTextWithLength(6)
        'maxSize.exceeded'   | 'countryOfDestinationCode'    || getCustomTextWithLength(3)
        'maxSize.exceeded'   | 'placeOfUnloadingCode'        || getCustomTextWithLength(6)
        'maxSize.exceeded'   | 'exporterCode'                || getCustomTextWithLength(18)
        'nullable'           | 'consigneeNameAddress'        || null
        'maxSize.exceeded'   | 'commodityCode'               || getCustomTextWithLength(15)
        'maxSize.exceeded'   | 'productTypeCode'             || getCustomTextWithLength(45)
    }


    def "test adding Ephyto goods should assign correct rank"() {
        given: "a new instance of Goods"
        def good = new ItemGood()

        when: "new item is added"
        domain.addItem(good)

        then: "Number should be set accordingly"
        domain.itemGoods != null
        domain.itemGoods[0].itemRank == 1
    }

    def "test getItem should return correct instance by Number"() {
        given: "a list of goods added to domain"
        def goods = [new ItemGood(batchNumber:'1',subBatchNumber:'1',quantity:2,grossWeight:2,netWeight:4), new ItemGood(batchNumber:'2',subBatchNumber:'12',quantity:22,grossWeight:22,netWeight:42), new ItemGood(batchNumber:'3',subBatchNumber:'13',quantity:23,grossWeight:23,netWeight:44)]
        goods.each { item ->
            domain.addItem(item)
        }

        when: "good with itemRank 2 should return ItemGood with subBatchNumber 12"
        def item = domain.getItem(2)

        then:
        item.itemRank == 2
        item.subBatchNumber == '12'
    }

    def "test removeItem should automatically shift item rank"() {
        given: "a list of goods added to domain"
        def goods = [new ItemGood(batchNumber:'1',subBatchNumber:'1',quantity:2,grossWeight:2,netWeight:4), new ItemGood(batchNumber:'2',subBatchNumber:'12',quantity:22,grossWeight:22,netWeight:42), new ItemGood(batchNumber:'3',subBatchNumber:'13',quantity:23,grossWeight:23,netWeight:44)]
        goods.each { item ->
            domain.addItem(item)
        }

        when: "item with itemRank 2 is removed"
        domain.removeItem(2)
        def item = domain.getItem(2)

        then: "goods with batchNumber 2 rank is == 3"
        item != null
        item.batchNumber == '3'

    }

    String getCustomTextWithLength(Integer length) {
        'a' * length
    }

    void validateConstraints(domain, field, error) {
        def validated = domain.validate()
        if (error && error != 'valid') {
            assert !validated
            assert domain.errors[field]
            assert domain.errors[field].code == error
        } else {
            assert !domain.errors[field]
        }
    }
}
