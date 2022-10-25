<%@ page import="com.webbfontaine.ephyto.TypeCastUtils" %>
<bootstrap:grid id="dialogItemGoodPopup" name="dialogItemGoodPopup">
    <bootstrap:formSection>
        <bootstrap:formGroup labelCode="ephytoGen.itemRank.label">
            <bootstrap:formInput span="3">
                <wf:textInput bean="${itemGoodInstance}"
                              field="itemNumber" id="itemRank" name="itemRank"
                              value="${itemGoodInstance?.itemRank}" class="span2" maxlength="13"
                              disabled="${!ephytoGenInstance?.isItemGoodEditable('itemRank')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('previousDocumentReference') ? 'none' : 'block'}">
            <bootstrap:formGroup labelCode="ephytoGen.previousDocumentReference.label">
                <bootstrap:formInput span="3">
                    <wf:textInput bean="${itemGoodInstance}" field="previousDocumentReference"
                                  id="previousDocumentReference" name="previousDocumentReference"
                                  value="${itemGoodInstance?.previousDocumentReference}"
                                  labelCode="ephytoGen.previousDocumentReference.label" class="span2"
                                  maxlength="13"
                                  disabled="${!ephytoGenInstance?.isItemGoodEditable('previousDocumentReference')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:div>

        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('previousDocumentItem') ? 'none' : 'block'}">
            <bootstrap:formGroup labelCode="ephytoGen.previousDocumentItem.label">
                <bootstrap:formInput span="3">
                    <wf:textInput bean="${itemGoodInstance}" field="previousDocumentItem"
                                  id="previousDocumentItem" name="previousDocumentItem"
                                  value="${itemGoodInstance?.previousDocumentItem}" class="span2"
                                  maxlength="35"
                                  disabled="${!ephytoGenInstance?.isItemGoodEditable('previousDocumentItem')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:div>

        <bootstrap:formGroup labelCode="ephytoGen.batchNumber.label">
            <bootstrap:formInput span="4">
                <wf:textInput bean="${itemGoodInstance}" field="batchNumber" id="batchNumber"
                              name="batchNumber"
                              value="${itemGoodInstance?.batchNumber}" class="span2"
                              maxlength="15"
                              disabled="${!ephytoGenInstance?.isItemGoodEditable('batchNumber')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>


        <bootstrap:formGroup labelCode="ephytoGen.subBatchNumber.label">
            <bootstrap:formInput span="4">
                <wf:textInput bean="${itemGoodInstance}"
                              field="subBatchNumber" id="subBatchNumber" name="subBatchNumber"
                              value="${itemGoodInstance?.subBatchNumber}"
                              class="span2"
                              disabled="${!ephytoGenInstance?.isItemGoodEditable('subBatchNumber')}"
                              maxLength="15"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>


        <bootstrap:formGroup labelCode="ephytoGen.quantity.label">
            <bootstrap:formInput span="3">
                <wf:quantityInput bean="${itemGoodInstance}" field="quantity"
                              id="quantity" name="quantity" regexpForRule="#,##0.000"
                                  regexp="${"[0-9,.\\s]"}"
                                  maxlength="19"
                                  onchange="formatGoodDecimalValue('quantity')"
                              value="${itemGoodInstance?.quantity}" class="span2"
                              disabled="${!ephytoGenInstance?.isItemGoodEditable('quantity')}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>


        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('remainingQuantiy') ? 'none' : 'block'}">
            <bootstrap:formGroup labelCode="ephytoGen.remainingQuantiy.label">
                <bootstrap:formInput span="3">
                    <wf:textInput bean="${itemGoodInstance}" field="remainingQuantiy"
                                  id="remainingQuantiy" name="remainingQuantiy"
                                  value="${itemGoodInstance?.remainingQuantiy}"
                                  class="span2" regexp="${'[0-9 \\s]'}" regexpForRule="${"[0-9]"}"
                                  disabled="${!ephytoGenInstance?.isItemGoodEditable('remainingQuantiy')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:div>


        <bootstrap:formGroup labelCode="ephytoGen.netWeight_kg.label">
            <bootstrap:formInput span="3">
                <wf:quantityInput id="netWeight" name="netWeight"
                                  regexp="${"[0-9,.\\s]"}"
                                  bean="${itemGoodInstance}"
                                  field="netWeight"
                                  regexpForRule="#,##0.000"
                                  maxlength="19"
                                  onchange="formatGoodDecimalValue('netWeight')"
                              value="${itemGoodInstance?.netWeight}" class="span2"
                              disabled="${!ephytoGenInstance?.isItemGoodEditable('netWeight')}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <bootstrap:div id="addonnetWeight">
                    <bootstrap:label id="addonnetWeights" labelCode="ephyto.KG.label"
                                     title="${message(code: 'ephyto.KG.label', default: 'KG')}"></bootstrap:label>
                </bootstrap:div>
            </bootstrap:formInput>
            <bootstrap:div id="addonnetWeight"></bootstrap:div>
        </bootstrap:formGroup>


        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('remainingNetWeight') ? 'none' : 'block'}">
            <bootstrap:formGroup labelCode="ephytoGen.remainingNetWeight_kg.label">
                <bootstrap:formInput span="3">
                    <wf:textInput id="remainingNetWeight" name="remainingNetWeight"
                                  regexp="${'[0-9 \\s]'}"
                                  regexpForRule="${"[0-9]"}"
                                  value="${itemGoodInstance?.remainingNetWeight}"
                                  class="span2"
                                  disabled="${!ephytoGenInstance?.isItemGoodEditable('remainingNetWeight')}"/>
                </bootstrap:formInput>
                <bootstrap:formInput span="3">
                    <bootstrap:label id="NetWeightLabel" labelCode="ephyto.KG.label"
                                     title="${message(code: 'ephyto.KG.label', default: 'KG')}"></bootstrap:label>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:div>


        <bootstrap:formGroup labelCode="ephytoGen.grossWeight_kg.label">
            <bootstrap:formInput span="3">
                <wf:quantityInput id="grossWeight" name="grossWeight" value="${itemGoodInstance?.grossWeight}" class="span2"
                                  regexp="${"[0-9,.\\s]"}"
                                  maxlength="19"
                                  regexpForRule="#,##0.000"
                                  onchange="formatGoodDecimalValue('grossWeight')"
                              disabled="${!ephytoGenInstance?.isItemGoodEditable('grossWeight')}"/>

            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <bootstrap:label class="updateLabel" id="addongrossWeights" labelCode="ephyto.KG.label"
                                 title="${message(code: 'ephyto.KG.label', default: 'KG')}"></bootstrap:label>

            </bootstrap:formInput>
            <bootstrap:div style="display: none" id="addongrossWeight"></bootstrap:div>
        </bootstrap:formGroup>


        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('remainingGrossWeight') ? 'none' : 'block'}">
            <bootstrap:formGroup labelCode="ephytoGen.remainingGrossWeight_kg.label">
                <bootstrap:formInput span="3">
                    <wf:textInput id="remainingGrossWeight" name="remainingGrossWeight"
                                  value="${itemGoodInstance?.remainingGrossWeight}" class="span2"
                                  disabled="${!ephytoGenInstance?.isItemGoodEditable('remainingGrossWeight')}"/>
                </bootstrap:formInput>
                <bootstrap:formInput span="3">
                    <bootstrap:label id="remainingGrossWeightLabel" labelCode="ephyto.KG.label"
                                     title="${message(code: 'ephyto.KG.label', default: 'KG')}"></bootstrap:label>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:div>


        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('analysisResult') ? 'none' : 'block'}">
            <bootstrap:formGroup labelCode="ephytoGen.analysisResult.label">
                <bootstrap:formInput span="3">
                    <wf:checkBox id="analysisResult" style="margin-right:-100px !important;" name="analysisResult"
                                 value="${itemGoodInstance?.analysisResult}"
                                 disabled="${!ephytoGenInstance?.isItemGoodEditable('analysisResult')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:div>

        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('potted') ? 'none' : 'block'}">
            <bootstrap:formGroup labelCode="ephytoGen.potted.label">
                <bootstrap:formInput span="3">
                    <wf:checkBox id="potted" name="potted" value="${itemGoodInstance?.potted}"
                                 style="margin-right:-100px !important;"
                                 disabled="${!ephytoGenInstance?.isItemGoodEditable('potted')}"/>
                </bootstrap:formInput>
            </bootstrap:formGroup>
        </bootstrap:div>


        <bootstrap:div
                style="display: ${!ephytoGenInstance?.isItemGoodEditable('observations') ? 'none' : 'block'}">
            <wf:textInput id="observations" name="observations" maxlength="200" row="8" cols="100" class="span4"
                          value="${itemGoodInstance?.observations}" labelCode="ephytoGen.observations.label"
                          bean="${itemGoodInstance}" field="observations1"
                          disabled="${!ephytoGenInstance?.isItemGoodEditable('observations')}"/>
        </bootstrap:div>

        <g:render template="/itemGoods/utils/hiddenGoodFieldsUTF"  model="[itemGoodInstance: itemGoodInstance]"/>

    </bootstrap:formSection>
</bootstrap:grid>
