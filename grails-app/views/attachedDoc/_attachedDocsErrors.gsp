<bootstrap:div style="display: none;">Error</bootstrap:div>
<bootstrap:alert class="alert-error errorContainer innerErrorContainer">
    <g:hasErrors bean="${attachedDoc}">
        <wf:beanErrors bean="${attachedDoc}" class="subErrorMessages attFld_${attachedDoc.attNumber}"
                       id="${attachedDoc.attNumber}_attachmentsTab_attFld"/>
    </g:hasErrors>
</bootstrap:alert>