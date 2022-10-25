<bootstrap:div style="display:none;width:1200px; height:720px; left:10%; top:6%;" tabindex="-1" data-backdrop="static"
               data-keyboard="false"
               class="modal fade" id="dialogItemGood" aria-hidden="false">

    <bootstrap:div class="modal-dialog">
        <bootstrap:div class="modal-content">
            <bootstrap:div class="modal-header" style="padding: 4px 4px !important;">
                <g:render template="/itemGoods/utils/itemGoodHeader"
                          model="[ephytoGenInstance: ephytoGenInstance, itemGoodInstance: itemGoodInstance]"/>
            </bootstrap:div>

            <bootstrap:div class="modal-body" style="height:300px !important;">
                <bootstrap:div id="error" style="clear:both; margin-left:10px;">
                    <bootstrap:div style="clear:both; margin-left:5px;"></bootstrap:div>
                </bootstrap:div>
                <bootstrap:form class="form-horizontal" id="appformitemgood" name="appformitemgood" novalidate="novalidate">
                    <bootstrap:div id="content">
                        <g:render template="/itemGoods/utils/itemGoodForm"
                                  model="[ephytoGenInstance: ephytoGenInstance, itemGoodInstance: itemGoodInstance]"/>
                    </bootstrap:div>
                </bootstrap:form>
            </bootstrap:div>

            <bootstrap:div class="modal-footer" style=" height:70px; margin-top:27px">
                <g:render template="/itemGoods/utils/itemGoodFooter"
                          model="[ephytoGenInstance: ephytoGenInstance, itemGoodInstance: itemGoodInstance]"/>
            </bootstrap:div>

        </bootstrap:div>
    </bootstrap:div>

</bootstrap:div>
