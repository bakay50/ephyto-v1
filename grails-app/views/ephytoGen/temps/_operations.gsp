<g:render plugin="wf-workflow" template="/operationButtons" model="[operations: ephytoGenInstance?.operations]"/>
<bootstrap:linkButton class="btn Close" controller="ephytoGen" action="list"><g:message code="operation.close"
                                                                                         default="Close"/></bootstrap:linkButton>
<wf:ajaxProgress/>

