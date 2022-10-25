<div style="display: none;">Error</div>
<wf:alert class="alert-error errorContainer innerErrorContainer">
    <g:hasErrors bean="${item}">
        <wf:beanErrors bean="${itemGood}" class="innerErrorMessages itemFld_${itemGood.itemRank}" id="${itemGood.itemRank}_itemsTab_itemFld"/>
    </g:hasErrors>
</wf:alert>