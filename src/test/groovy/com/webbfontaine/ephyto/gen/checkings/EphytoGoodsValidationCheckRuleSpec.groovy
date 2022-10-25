package com.webbfontaine.ephyto.gen.checkings

import com.webbfontaine.ephyto.gen.ItemGood
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import spock.lang.Specification

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 19/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

class EphytoGoodsValidationCheckRuleSpec extends Specification{


    void "test ephyto when goods is null"(){
        given:
            EphytoGen ephytoGen = new EphytoGen()
            RuleContext ruleContext = new RuleContext(ephytoGen,ephytoGen.errors)
            GoodValidationRule rule = new GoodValidationRule()
            rule.apply(ruleContext)
        expect:
            ruleContext.hasErrors()
            ephytoGen.itemGoods == null
    }

    void "test ephyto when goods is empty"(){
        given:
        EphytoGen ephytoGen = new EphytoGen()
        ephytoGen.itemGoods = new ArrayList<ItemGood>()
        RuleContext ruleContext = new RuleContext(ephytoGen,ephytoGen.errors)
        GoodValidationRule rule = new GoodValidationRule()
        rule.apply(ruleContext)
        expect:
        ruleContext.hasErrors()
        ephytoGen.totalItems == null
        ephytoGen.itemGoods.size() == 0
    }

    void "test ephyto when ephyto has goods"(){
        given:
            ItemGood good = new ItemGood(quantity: new Integer("100"))
            EphytoGen ephytoGen = new EphytoGen()
            ephytoGen.itemGoods = new ArrayList<ItemGood>()
            ephytoGen.itemGoods.add(good)
            ephytoGen.totalItems = ephytoGen?.itemGoods.size()
            RuleContext ruleContext = new RuleContext(ephytoGen,ephytoGen.errors)
            GoodValidationRule rule = new GoodValidationRule()
            rule.apply(ruleContext)
        expect:
            !ruleContext.hasErrors()
            ephytoGen.totalItems == 1
    }
}
