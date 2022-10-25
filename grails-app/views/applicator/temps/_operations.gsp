<g:render plugin="wf-workflow" template="/operationButtons" model="[operations: applicatorInstance?.operations]"/>
<bootstrap:linkButton class="btn Close" controller="applicator" action="list"><g:message code="operation.close"
                                                                                        default="Close"/></bootstrap:linkButton>
<wf:ajaxProgress/>