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
            <layout:resource name="override/taglib-override"/>
            <wf:utilsSetup confirmDialog="true"/>
            <wfConversation:conversationSetup conversationId="${params.conversationId}"/>
        </layout:pageResources>
        <wf:loadMandatoryFields bean="${applicatorInstance}" refName="applicator"/>
        <wf:hiddenField name="tempOper"/>
        <wf:hiddenField name="id" value="${applicatorInstance?.id}"/>
        <h3>${message(code: 'applicator.show.label')}</h3>
        <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
        <g:render template="/applicator/utils/hiddenLinks"/>
        <g:render template="/utils/messages"/>
        <bootstrap:div id="rimmContainer">
            <g:render template="/applicator/utils/rimmBeans"/>
        </bootstrap:div>
        <bootstrap:form name="appMainForm" small="${true}" class="form-horizontal"
                        action="${applicatorInstance?.id ? 'update' : 'save'}"
                        grid='12(3-3-6)'>
            <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
            <wf:hiddenField name="id" value="${applicatorInstance?.id}"/>
            <g:set var="numberFormatConfig" value="${grailsApplication.config}"/>
            <wf:hiddenField name="conversationId" value="${params?.conversationId}"/>
            <g:render template="/applicator/temps/operationsContainer"
                      model="[applicatorInstance: applicatorInstance]"/>
            <bootstrap:div id="formContent">
                <g:render template="/applicator/temps/form"
                          model="[applicatorInstance: applicatorInstance]"/>
            </bootstrap:div>
            <bootstrap:submitButton name="submitCurrentOperation" style="display: none"/>

        </bootstrap:form>

        <wf:confirmDialog model="[formName: 'appMainForm']"/>
        <g:render template="/dialogs/messageDialog" plugin="wf-utils"/>
        <g:if test="${operationDoneMessage}">
            <layout:javascript defer="true">
                $(function () {
                    openMessageDialog("${operationDoneMessage.title}", "${operationDoneMessage.message}")
                    initDialogCustomStyle()
              });
            </layout:javascript>
        </g:if>
    </bootstrap:div>
</content>

</body>
</html>
