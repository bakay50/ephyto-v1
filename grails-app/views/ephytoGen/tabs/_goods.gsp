<%@ page import="com.webbfontaine.ephyto.BusinessLogicService; com.webbfontaine.ephyto.constants.Operations" %>
<bootstrap:formSection>
    <bootstrap:genericInput field="commodityCode" id="commodityCode" name="commodityCode" disabled="${!ephytoGenInstance?.isFieldEditable('commodityCode')}"
                            bean="${ephytoGenInstance}" labelCode="ephytoGen.commodityCode.label" maxlength="10"/>
    <bootstrap:formGroup labelCode="ephytoGen.commodityDescription.label">
        <bootstrap:formInput span="5">
            <wf:genericInput field="commodityDescription" id="commodityDescription" name="commodityDescription" bean="${ephytoGenInstance}" type="area"
                             disabled="${!ephytoGenInstance?.isFieldEditable('commodityDescription')}" rows="4" maxlength="3500" value="${ephytoGenInstance?.commodityDescription}"/>
        </bootstrap:formInput>
    </bootstrap:formGroup>


    <bootstrap:genericInput id="commercialDescriptionGoods" field="commercialDescriptionGoods"
                            bean="${ephytoGenInstance}" name="commercialDescriptionGoods" maxlength="50"
                            class="span4" onchange="updateForUTF8('commercialDescriptionGoods')" inputSpan="5"
                            value="${ephytoGenInstance?.commercialDescriptionGoods}"
                            labelCode="ephytoGen.commercialDescriptionGoods.label"
                            disabled="${!ephytoGenInstance?.isFieldEditable('commercialDescriptionGoods')}"/>

    <bootstrap:formGroup labelCode="ephytoGen.botanicalName.label">
        <bootstrap:formInput span="5">
            <wf:genericInput field="botanicalName" bean="${ephytoGenInstance}" type="area" id="botanicalName"
                             name="botanicalName" maxlength="100"
                             row="5" cols="150" class="span4" onchange="updateForUTF8('botanicalName')"
                             value="${ephytoGenInstance?.botanicalName}"
                             readonly="${BusinessLogicService.isBotanicalNameFieldEnabledForGovUser(ephytoGenInstance)?false:(ephytoGenInstance?.isBotanicEditable()?false:(ephytoGenInstance?.isNewproduct() ? (!ephytoGenInstance?.isFieldEditable('botanicalName')) : true))}"/>
        </bootstrap:formInput>
    </bootstrap:formGroup>

    <bootstrap:formGroup labelCode="ephytoGen.harvest.label">
        <bootstrap:formInput span="5">
            <wf:genericInput field="harvest" bean="${ephytoGenInstance}" id="harvest" name="harvest" maxlength="100"
                             row="8" cols="150" class="span5" type="area"
                             value="${ephytoGenInstance?.harvest}" onchange="updateForUTF8('harvest')"
                             disabled="${!ephytoGenInstance?.isFieldEditable('harvest')}"/>
        </bootstrap:formInput>
    </bootstrap:formGroup>

    <bootstrap:formGroup labelCode="ephytoGen.season.label">
        <bootstrap:formInput span="5">
            <wf:genericInput field="season" bean="${ephytoGenInstance}" id="season" name="season" maxlength="100"
                             row="8" cols="150" class="span5" type="area"
                             value="${ephytoGenInstance?.season}" onchange="updateForUTF8('season')"
                             disabled="${!ephytoGenInstance?.isFieldEditable('season')}"/>
        </bootstrap:formInput>
    </bootstrap:formGroup>

    <bootstrap:formGroup labelCode="ephytoGen.countryOfOriginCode.label">
        <bootstrap:formInput span="2">
            <wf:genericInput field="countryOfOriginCode" id="countryOfOriginCode" bean="${ephytoGenInstance}" name="countryOfOriginCode"
                             value="${ephytoGenInstance?.countryOfOriginCode}" maxlength="10"
                             class="span2" labelCode="ephytoGen.countryOfOriginCode.label"
                             disabled="${!ephytoGenInstance?.isFieldEditable('countryOfOriginCode')}"/>
        </bootstrap:formInput>
        <bootstrap:div class="form-group-labels">
            <bootstrap:label
                    id="countryOfOriginName">${ephytoGenInstance?.countryOfOriginName}</bootstrap:label>
        </bootstrap:div>
    </bootstrap:formGroup>



    <bootstrap:genericInput field="packageMarks" id="packageMarks" name="packageMarks" bean="${ephytoGenInstance}"
                            value="${ephytoGenInstance?.packageMarks}" maxlength="50"
                            class="span4" onchange="updateForUTF8('packageMarks')" inputSpan="5"
                            labelCode="ephytoGen.packageMarks.label"
                            disabled="${!ephytoGenInstance?.isFieldEditable('packageMarks')}"/>

    <bootstrap:formGroup labelCode="ephytoGen.packageCode.label">
        <bootstrap:formInput span="2">
            <wf:genericInput field="packageCode" id="packageCode" bean="${ephytoGenInstance}" name="packageCode"
                             value="${ephytoGenInstance?.packageCode}" maxlength="10"
                             class="span2" labelCode="ephytoGen.packageCode.label"
                             disabled="${!ephytoGenInstance?.isFieldEditable('packageCode')}"/>
        </bootstrap:formInput>
        <bootstrap:div class="form-group-labels">
            <bootstrap:label
                    id="packageName">${ephytoGenInstance?.packageName}</bootstrap:label>
        </bootstrap:div>
    </bootstrap:formGroup>

    <bootstrap:formGroup labelCode="ephytoGen.totalQuantity.label">
        <bootstrap:formInput>
            <wf:quantityInput
                    field="totalQuantity" bean="${ephytoGenInstance}"
                    labelCode="ephytoGen.totalQuantity.label" name="totalQuantity"
                    regexp="${"[0-9,.\\s]"}" id="totalQuantity"
                    regexpForRule="#,##0.000"
                    onchange="formatDecimalValue('totalQuantity')"
                    value="${ephytoGenInstance?.sumQuantityGoods()==0?"":ephytoGenInstance?.sumQuantityGoods()}" maxlength="19"
                    class="span2" style="float:right !important;"
                    disabled="${!ephytoGenInstance?.isFieldEditable('totalQuantity')}"/>
        </bootstrap:formInput>
    </bootstrap:formGroup>


    <bootstrap:formGroup labelCode="ephytoGen.totalNetWeight.label">
        <bootstrap:formInput>
            <wf:quantityInput
                          labelCode="ephytoGen.totalNetWeight.label" name="totalNetWeight"
                          regexp="${"[0-9,.\\s]"}"
                          regexpForRule="#,##0.000"
                          maxlength="19" id="totalNetWeight"
                          value="${ephytoGenInstance?.sumNetWeightGoods()==0?"":ephytoGenInstance?.sumNetWeightGoods()}"
                          class="span2"
                          onchange="formatDecimalValue('totalNetWeight')"
                          disabled="${!ephytoGenInstance?.isFieldEditable('totalNetWeight')}"/>
        </bootstrap:formInput>
        <bootstrap:formInput span="3">
            <bootstrap:label id="NetWeightLabel" labelCode="ephyto.KG.label"
                             title="${message(code: 'ephyto.KG.label', default: 'KG')}"></bootstrap:label>
        </bootstrap:formInput>

    </bootstrap:formGroup>


    <bootstrap:formGroup labelCode="ephytoGen.totalGrossWeight.label">
        <bootstrap:formInput>
            <wf:quantityInput
                          labelCode="ephytoGen.totalGrossWeight.label" name="totalGrossWeight"
                          regexp="${"[0-9,.\\s]"}"
                          regexpForRule="#,##0.000" id="totalGrossWeight"
                          onchange="formatDecimalValue('totalGrossWeight')"
                          maxlength="19"
                          value="${ephytoGenInstance?.sumGrossWeightGoods()==0?"":ephytoGenInstance?.sumGrossWeightGoods()}"
                          class="span2"
                          disabled="${!ephytoGenInstance?.isFieldEditable('totalGrossWeight')}"/>
        </bootstrap:formInput>
        <bootstrap:formInput span="3">
            <bootstrap:label id="GrossWeightLabel" labelCode="ephyto.KG.label"
                             title="${message(code: 'ephyto.KG.label', default: 'KG')}"></bootstrap:label>
        </bootstrap:formInput>

    </bootstrap:formGroup>

    <bootstrap:formGroup labelCode="ephytoGen.volume_m3.label">
        <bootstrap:formInput>
            <wf:quantityInput name="volume" id="volume"
                              value="${ephytoGenInstance?.volume}"
                              regexp="${"[0-9,.\\s]"}"
                              regexpForRule="#,##0.000"
                              class="updateWitdVolume"
                              maxlength="19"
                              onchange="formatDecimalValue('volume')"
                              disabled="${!ephytoGenInstance?.isFieldEditable('volume')}"/>
        </bootstrap:formInput>
        <bootstrap:formInput span="5">
            <bootstrap:label id="volumeLabel" name="volumeLabel" labelCode="ephyto.M3.label" style="padding-right:10px !important;"
                             title="${message(code: 'ephyto.M3.label', default: 'm3')}"></bootstrap:label>
        </bootstrap:formInput>

    </bootstrap:formGroup>

    <bootstrap:formGroup labelCode="ephytoGen.totalItems.label">
        <bootstrap:formInput>
            <wf:textInput field="totalItems" bean="${ephytoGenInstance}" labelCode="ephytoGen.totalItems.label"
                          name="totalItems" id="totalItems"
                          value="${ephytoGenInstance?.sumItemGoods()}"
                          class="span2"
                          disabled="${!ephytoGenInstance?.isFieldEditable('totalItems')}"/>
        </bootstrap:formInput>

    </bootstrap:formGroup>

    <bootstrap:div id="addGoods" style="clear:both;">
        <bootstrap:div class="control-group">
            <bootstrap:div id="GoodsButton" style="clear:both;margin-bottom:15px !important;">
                <label class="control-label">
                    <strong><g:message code="ephytoGen.goodsDetails.label" default="Goods Details"/>
                    <g:if test="${ephytoGenInstance?.isDocumentEditable()}">
                        <g:if test="${ephytoGenInstance?.addItemGoods || ephytoGenInstance?.editItemGoods}">
                            <bootstrap:linkButton id="addGoodsLinkModal" class="btn"
                                                  title="${message(code: 'attachedDocs.add.button.label')}"
                                                  noLink="#"
                                                  onclick="showDialogItemGoods()" icon="plus"/>
                        </g:if>
                    </g:if>
                    </strong>
                </label>
            </bootstrap:div>
        </bootstrap:div>
        <table class="table table-bordered table-striped tableNoWrap" id="attDocTable">
            <thead id="theadHeader">
            <tr>

                <th id="actionsGoods" style="width:75% !important;border-left:1px solid transparent !important;border-top:1px solid transparent !important;border-right:0px solid !important;border-bottom-right : 1px !important;"></th>
                <th style="width:65%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">
                    <g:message code="ephytoGen.rank.label" default="Rank"/>
                </th>
                <th style="width: 80%;text-align: justify">
                    <g:message code="ephytoGen.previousDocumentReference.label"
                               default="Previous Document Reference"/>
                </th>
                <th style="width:80%;text-align: justify">
                    <g:message code="ephytoGen.previousDocumentItem.label"
                               default="Previous Document Item"/>
                </th>
                <th style="width:80%;text-align: center">
                    <g:message code="ephytoGen.batchNumber.label" default="Batch Number"/>
                </th>
                <th style="width:80%;text-align: justify">
                    <g:message code="ephytoGen.subBatchNumber.label" default="Sub-Batch Number"/>
                </th>
                <th style="width:80%;text-align: justify">
                    <g:message code="ephytoGen.quantity.label" default="Quantity"/>
                </th>
                <th style="width:80%;text-align: center">
                    <g:message code="ephytoGen.RemainingQuantiy.label" default="Remaining Quantiy"/>
                </th>
                <th style="width:80%;text-align: justify">
                    <g:message code="ephytoGen.netWeight_kg.label" default="Net Weight (kg)"/>
                </th>
                <th style="width:80%;text-align: center">
                    <g:message code="ephytoGen.remainingNetWeight_kg.label"
                               default="Remaining Net Weight (kg)"/>
                </th>
                <th style="width:70%;text-align: justify">
                    <g:message code="ephytoGen.grossWeight_kg.label" default="Gross Weight  (kg)"/>
                </th>
                <th style="width:70%;text-align: justify">
                    <g:message code="ephytoGen.remainingGrossWeight_kg.label"
                               default="Remaining Gross Weight  (kg)"/>
                </th>
                <th style="width:80%;text-align: justify">
                    <g:message code="ephytoGen.analysisResult.label" default="Analysis Result"/>
                </th>
                <th style="width:80%;text-align:justify">
                    <g:message code="ephytoGen.potted.label" default="Analysis Result"/>
                </th>
                <th style="width:98%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">
                    <g:message code="ephytoGen.Observations.label" default="Analysis Result"/>
                </th>
            </tr>
            </thead>
            <tbody id="itemGoodListBody">
            <g:render template="/itemGoods/itemGoodList"
                      model="[
                              'instance'   : ephytoGenInstance,
                              'controller' : 'itemGood',
                              addItemGoods : ephytoGenInstance?.addItemGoods,
                              editItemGoods: ephytoGenInstance?.editItemGoods
                      ]"/>

            </tbody>
        </table>
    </bootstrap:div>
</bootstrap:formSection>


