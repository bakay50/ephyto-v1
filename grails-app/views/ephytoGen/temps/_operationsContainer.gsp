<header id="overview" class="jumbotron subhead">
    <bootstrap:div id="operationButtonsContainers" name="operationButtonsContainers">
        <sec:access
                expression="hasAnyRole('ROLE_EPHYTO_GOV_OFFICER','ROLE_EPHYTO_GOV_SENIOR_OFFICER','ROLE_EPHYTO_ADMINISTRATOR','ROLE_EPHYTO_GOV_SUPERVISOR')">
            <g:if test="${com.webbfontaine.ephyto.BusinessLogicUtils.canPrint(ephytoGenInstance)}">


                    <bootstrap:linkButton class="btn print" controller="print" action="print"
                                          labelCode="ephytoGen.button.print.label"
                                          params="[domainName    : 'ephytoGen', id: ephytoGenInstance?.id,
                                                   conversationId: params.conversationId ?: '']">
                        <i class="glyphicon glyphicon-print pull-left" ></i>
                    </bootstrap:linkButton>


            </g:if>
        </sec:access>
        <sec:access expression="hasAnyRole('ROLE_EPHYTO_DECLARANT','ROLE_EPHYTO_TRADER')">
            <g:if test="${com.webbfontaine.ephyto.BusinessLogicUtils.canPrintDraft(ephytoGenInstance)}">
                <bootstrap:linkButton class="btn print" controller="print" action="printDraft"
                                      labelCode="ephytoGen.button.printDraft.label"
                                      params="[domainName    : 'ephytoGen', id: ephytoGenInstance?.id,
                                               conversationId: params.conversationId ?: '']">
                    <i class="glyphicon glyphicon-print pull-left"></i>
                </bootstrap:linkButton>
            </g:if>
        </sec:access>
        <g:if test="${ephytoGenInstance?.status == null}">
            <bootstrap:linkButton id="xmlImportButton" labelCode="xml.import.label" class="btn xmlImportButton">
                <i class="glyphicon glyphicon-circle-arrow-down pull-left"></i>
            </bootstrap:linkButton>
        </g:if>
        <g:if test="${ephytoGenInstance?.id}">
            <bootstrap:linkButton class="btn exportXMLButton ephytoGen" labelCode="xml.export.label" id="exportXMLButton">
                <i class="glyphicon glyphicon-circle-arrow-up  pull-left"></i>
            </bootstrap:linkButton>

        </g:if>

    <bootstrap:div id="operationButtonsContainer" name="operationButtonsContainer"
                       style="float: right !important;" class="pull-right">
            <g:render template="temps/operations" model="[ephytoGenInstance: ephytoGenInstance]"/>

        </bootstrap:div>
    </bootstrap:div>
</header>