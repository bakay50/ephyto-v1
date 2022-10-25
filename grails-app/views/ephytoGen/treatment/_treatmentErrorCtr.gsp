<bootstrap:div style="display: none;">Error</bootstrap:div>
<wf:alert class="alert-error errorContainer innerErrorContainer">
    <g:hasErrors bean="${treatment}">
        <wf:beanErrors bean="${treatment}" class="innerErrorMessages treatmentFld_${treatment.itemNumber}" id="${treatment.itemNumber}_treatmentsTab_treatmentFld"/>
    </g:hasErrors>
</wf:alert>