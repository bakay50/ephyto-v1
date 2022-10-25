<%@ page import="org.springframework.context.i18n.LocaleContextHolder; org.springframework.web.servlet.support.RequestContextUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="page"/>
</head>

<body>
<content tag="application">
    <bootstrap:div id="formContents">
        <layout:pageResources dependsOn="application">
            <wf:tagLibSetup locale="${request.locale}" validation="all"/>
            <layout:resource name="${asset.i18nPath([name:'messages', locale:LocaleContextHolder.locale])}" skipCheck="${true}"/>
            <layout:resource name="${message(code: 'dataTablesResources')}"/>
            <layout:resource name="${message(code: 'i18messages')}"/>
            <layout:resource name="ephytoEditResources"/>
            <layout:resource name="override/taglib-override"/>
            <wf:utilsSetup confirmDialog="true"/>
            <wfConversation:conversationSetup conversationId="${params.conversationId}"/>
            <layout:resource name="ephytoGen" type="css"/>
        </layout:pageResources>
        <wf:loadMandatoryFields bean="${ephytoGenInstance}" refName="ephytoGen"/>
        <wf:hiddenField name="controllerName" value="${controllerName}"/>
        <wf:hiddenField name="tempOper"/>
        <wf:hiddenField name="id" value="${ephytoGenInstance?.id}"/>
        <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
        <wf:hiddenField name="status_doc" value="${ephytoGenInstance?.status}"/>
        <wf:hiddenField name="current_op" value="${params.op}"/>
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
            <wf:hiddenField name="currentCommitOperation" id="currentCommitOperation" />
            <wf:hiddenField name="currentCommitOperationValue" id="currentCommitOperationValue" />
            <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
            <wf:hiddenField name="id" value="${ephytoGenInstance?.id}"/>
            <g:set var="numberFormatConfig" value="${grailsApplication.config}"/>
            <wf:hiddenField name="conversationId" value="${params?.conversationId}"/>
            <g:render template="/ephytoGen/temps/operationsContainer"
                      model="[ephytoGenInstance: ephytoGenInstance]"/>
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
</content>

</body>
</html>
