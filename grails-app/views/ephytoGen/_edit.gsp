<%@ page import="org.springframework.context.i18n.LocaleContextHolder; org.springframework.web.servlet.support.RequestContextUtils" contentType="text/html;charset=UTF-8" %>

<bootstrap:div id="formContents">
    <layout:pageResources dependsOn="application">
        <layout:resource name="${message(code: 'dataTablesResources')}"/>
        <layout:resource name="${message(code: 'i18messages')}"/>
        <layout:resource name="ephytoEditResources"/>
        <layout:resource name="ephytoGen" type="css"/>
        <layout:resource name="override/taglib-override"/>
        <layout:resource
                name="${asset.i18nPath([name: 'messages', locale: org.springframework.context.i18n.LocaleContextHolder.locale])}"
                skipCheck="${true}"/>
        <wf:tagLibSetup locale="${request.locale}" validation="all"/>
        <wf:utilsSetup confirmDialog="true"/>
        <wfConversation:conversationSetup conversationId="${params.conversationId}"/>
    </layout:pageResources>
    <wf:loadMandatoryFields bean="${ephytoGenInstance}" refName="ephytoGen"/>
    <wf:hiddenField name="controllerName" value="${controllerName}"/>
    <wf:hiddenField name="id" value="${ephytoGenInstance?.id}"/>
    <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
    <wf:hiddenField name="status_doc" value="${ephytoGenInstance?.status}"/>
    <wf:hiddenField name="tempOper"/>
    <g:render template="/ephytoGen/utils/hiddenLinks"/>
    <g:render template="/itemGoods/utils/hiddenLinks"/>
    <g:render template="/itemGoods/itemGoods"/>
    <h3>${message(code: 'label.create.label', args: [message(code: "${com.webbfontaine.ephyto.BusinessLogicUtils.updateLabelStartedOperations(ephytoGenInstance)}"), message(code: "${com.webbfontaine.ephyto.constants.EphytoConstants.TYPE_PHYTO}")])}</h3>
    <g:render template="/utils/messages"/>
    <bootstrap:div id="rimmContainer">
        <g:render template="/ephytoGen/common/rimmBeans"/>
    </bootstrap:div>
    <bootstrap:form name="appMainForm" small="${true}" class="form-horizontal"
                    action="${ephytoGenInstance?.id ? 'update' : 'save'}"
                    grid='12(3-3-6)'>

        <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
        <wf:hiddenField name="id" value="${ephytoGenInstance?.id}"/>
        <g:set var="numberFormatConfig" value="${grailsApplication.config}"/>
        <wf:hiddenField name="conversationId" value="${params?.conversationId}"/>
        <header id="overview" class="jumbotron subhead">
            <bootstrap:div id="operationButtonsContainers" name="operationButtonsContainers">
                    <sec:access
                            expression="hasAnyRole('ROLE_EPHYTO_GOV_OFFICER','ROLE_EPHYTO_GOV_SENIOR_OFFICER','ROLE_EPHYTO_ADMINISTRATOR','ROLE_EPHYTO_GOV_SUPERVISOR')">
                        <g:if test="${com.webbfontaine.ephyto.BusinessLogicUtils.canPrint(ephytoGenInstance)}">
                                <bootstrap:linkButton class="btn print" controller="print" action="print"
                                                      labelCode="ephytoGen.button.print.label"
                                                      params="[domainName    : 'ephytoGen', id: ephytoGenInstance?.id,
                                                               conversationId: params.conversationId ?: '']">
                                    <i class="glyphicon glyphicon-print"></i>
                                </bootstrap:linkButton>
                        </g:if>
                    </sec:access>
                    <sec:access expression="hasAnyRole('ROLE_EPHYTO_DECLARANT','ROLE_EPHYTO_TRADER')">
                        <g:if test="${com.webbfontaine.ephyto.BusinessLogicUtils.canPrintDraft(ephytoGenInstance)}">
                            %{--<div id="printOperationContainer">--}%
                                <bootstrap:linkButton class="btn print" controller="print" action="printDraft"
                                                      labelCode="ephytoGen.button.printDraft.label"
                                                      params="[domainName    : 'ephytoGen', id: ephytoGenInstance?.id,
                                                               conversationId: params.conversationId ?: '']">
                                    <i class="glyphicon glyphicon-print"></i>
                                </bootstrap:linkButton>
                        </g:if>
                    </sec:access>
                        <g:if test="${ephytoGenInstance?.status == null}">
                            <bootstrap:linkButton id="xmlImportButton" labelCode="xml.import.label" class="btn xmlImportButton">
                                <i class="glyphicon glyphicon-circle-arrow-down"></i>
                            </bootstrap:linkButton>
                        </g:if>
                        <g:if test="${ephytoGenInstance?.id}">
                            <bootstrap:linkButton class="btn exportXMLButton ephytoGen" labelCode="xml.export.label" id="exportXMLButton">
                                <i class="glyphicon glyphicon-circle-arrow-up"></i>
                            </bootstrap:linkButton>

                        </g:if>
                <bootstrap:div id="operationButtonsContainer" name="operationButtonsContainer"
                               style="float: right !important;" class="pull-right">
                    <g:render plugin="wf-workflow" template="/operationButtons"
                              model="[operations: ephytoGenInstance?.operations]"/>
                    <bootstrap:linkButton class="btn Close" controller="ephytoGen" action="list"><g:message
                            code="operation.close"
                            default="Close"/></bootstrap:linkButton>
                    <wf:ajaxProgress/>

                </bootstrap:div>
            </bootstrap:div>
        </header>
        <bootstrap:div id="formContent">
            <g:render template="/ephytoGen/edit/editFormContent"
                      model="[ephytoGenInstance: ephytoGenInstance]"/>
        </bootstrap:div>
        <bootstrap:submitButton name="submitCurrentOperation" style="display: none"/>
    </bootstrap:form>

    <g:render template="/utils/importXMLDialog"
              model="['controller': 'xml', action: 'importXML']"/>
    <g:if test="${ephytoGenInstance?.addAttachedDocs || ephytoGenInstance?.editAttachedDocs}">
        <g:render template="/attachedDoc/attDocUploadFile" model="[ephytoGenInstance: ephytoGenInstance]"/>
        <g:render template="/attachedDoc/attachedDeleteFileDlg"/>
    </g:if>
    <wf:confirmDialog model="[formName: 'appMainForm']"/>
    <g:render template="/dialogs/messageDialog" plugin="wf-utils"/>
</bootstrap:div>


