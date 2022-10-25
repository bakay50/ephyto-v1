<bootstrap:div class="alert alert-error errorContainer">
    <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>x</button>
    <g:hasErrors bean="${attDoc}">
        <wf:beanErrors bean="${attDoc}" class="attDocInnerErrorMessages"/>
    </g:hasErrors>
</bootstrap:div>