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
      <layout:resource name="ephytoEditResources"/>
      <wf:utilsSetup confirmDialog="true"/>
      <layout:resource name="messages.js"/>
      <wfConversation:conversationSetup conversationId="${params.conversationId}"/>
    </layout:pageResources>
    <wf:loadMandatoryFields bean="${applicatorInstance}" refName="applicator"/>
    <wf:hiddenField name="tempOper"/>
    <wf:hiddenField name="id" value="${applicatorInstance?.id}"/>
    <g:if test="${applicatorInstance?.id}">
      <h3>${message(code: 'applicator.edit.label')}</h3>
    </g:if>
    <g:else>
      <h3>${message(code: 'applicator.create.label')}</h3>
    </g:else>
    <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
    <g:render template="/applicator/utils/hiddenLinks"/>
    <g:render template="/utils/messages"/>
    <g:if test="${hasErrors}">
      <bootstrap:alert type="error" class="wf-alert-scrolable error alert-block alert-danger errorContainer">
        <g:hasErrors bean="${applicatorInstance}">
          <wf:beanErrors bean="${applicatorInstance}"/>
        </g:hasErrors>

        <g:if test="${flash.errorMessage}">
          ${flash.errorMessage}
        </g:if>
      </bootstrap:alert>
    </g:if>
    <bootstrap:div id="rimmContainer">
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
  </bootstrap:div>
</content>

</body>
</html>
