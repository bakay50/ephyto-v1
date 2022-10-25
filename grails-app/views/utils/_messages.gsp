<g:if test="${flash.message}">
    <wf:alert class="alert-info">${flash.message}</wf:alert>
</g:if>
<g:if test="${infoMessage}">
    <wf:alert class="alert-info">${infoMessage}</wf:alert>
</g:if>
<g:if test="${flash.errorMessage}">
    <wf:alert class="alert-error">${flash.errorMessage}</wf:alert>
</g:if>
<g:if test="${errorMessage}">
    <wf:alert class="alert-error">${errorMessage}</wf:alert>
</g:if>