<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="page">
    <title></title>
</head>

<body>
<content tag="application">
    <h3>${message(code: 'label.create.label', args: [message(code: "${com.webbfontaine.ephyto.BusinessLogicUtils.updateLabelStartedOperations(ephytoGenInstance)}"), message(code: "${com.webbfontaine.ephyto.constants.EphytoConstants.TYPE_PHYTO}")])}</h3>
    <bootstrap:form name="appMainForm" id="appMainForm" type="horizontal normal left" class="form-horizontal"
                    grid='12(3-3-6)' url="[action: 'show']" novalidate="novalidate" controller="certificat">
        <g:set var="numberFormatConfig" value="${grailsApplication.config}"/>
        <layout:pageResources dependsOn="application">
            <wf:tagLibSetup locale="${request.locale}" validation="all"/>
            <asset:i18n name="messages" locale="${LocaleContextHolder.locale}"/>
            <layout:resource name="${asset.i18nPath([name: 'messages', locale: LocaleContextHolder.locale])}"
                             skipCheck="${true}"/>
            <layout:resource name="ephytoEditResources"/>
            <wf:tagLibSetup locale="${request.locale}" validation="all"/>
            <wf:utilsSetup confirmDialog="true"/>
            <wfConversation:conversationSetup conversationId="${params.conversationId}"/>
        </layout:pageResources>
        <wf:loadMandatoryFields bean="${ephytoGenInstance}" refName="ephytoGen"/>
        <g:render template="/utils/messages"/>
        <g:render template="/ephytoGen/utils/hiddenLinks"/>
        <g:render template="/itemGoods/utils/hiddenLinks"/>
        <g:render template="/itemGoods/itemGoods"/>
        <wf:hiddenField name="id" value="${ephytoGenInstance?.id}"/>
        <wf:hiddenField name="currentCommitOperation" id="currentCommitOperation" />
        <wf:hiddenField name="currentCommitOperationValue" id="currentCommitOperationValue" />
        <wf:hiddenField name="locale"
                        value="${org.springframework.web.servlet.support.RequestContextUtils.getLocale(request)}"/>
        <g:render template="/ephytoGen/temps/operationsContainer"
                  model="[ephytoGenInstance: ephytoGenInstance]"/>
        <g:hasErrors bean="${ephytoGenInstance}">
            <div class="wf-alert-scrolable alert alert-block alert-error errorContainer">
                <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>x</button>
                <wf:beanErrors bean="${ephytoGenInstance}"/>
            </div>
        </g:hasErrors>
        <bootstrap:div id="formContent">
            <g:render template="/ephytoGen/edit/editFormContent"
                      model="[ephytoGenInstance: ephytoGenInstance]"/>
        </bootstrap:div>
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
    </bootstrap:form>

</content>
</body>
</html>