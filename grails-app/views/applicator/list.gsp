<%@ page import="grails.util.Holders; org.springframework.context.i18n.LocaleContextHolder; org.springframework.web.servlet.support.RequestContextUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="page"/>
</head>

<body>
<content tag="application">
    <bootstrap:div>
        <h3><g:message code="applicator.search.label"/></h3>
        <layout:pageResources dependsOn="application">
            <wf:tagLibSetup locale="${request.locale}"/>
            <layout:resource name="${message(code: 'dataTablesResources')}"/>
            <layout:resource
                    name="${asset.i18nPath([name: 'messages', locale: org.springframework.context.i18n.LocaleContextHolder.locale])}"
                    skipCheck="${true}"/>
            <layout:resource name="ephytoSearchResources"/>
            <wf:utilsSetup confirmDialog="true"/>
        </layout:pageResources>
        <layout:resource name="${message(code: 'i18messages')}"/>
        <layout:resource name="export"/>
        <layout:resource name="searchResult-override" type="css"/>
        <layout:resource name="searchUtils" plugin="wf-search" dependsOn="application.js"/>
        <layout:resource name="search-core" type="css"/>
        <wf:hiddenField name="locale" value="${RequestContextUtils.getLocale(request)}"/>
        <wf:hiddenField name="controllerName" value="${controllerName}"/>
        <wf:ajaxProgress/>
        <g:render template="/utils/messages"/>
        <g:render template="/applicator/utils/rimmBeans"/>
        <wf:searchForm name="searchApplicatorForm" id="searchApplicatorForm" controller="applicator" action="search"
                       method="get"
                       model="[searchCommand: searchCommand]" template="/applicator/search/searchCriteria"/>
        <g:set var="resultList" value="${resultList}"></g:set>
        <bootstrap:div id="searchResults">
            <g:if test="${resultList != null}">
                <g:render plugin="wf-search" template="/searchResult" model="[
                        actionsTemplate  : actionsTemplate,
                        searchCommand    : searchCommand,
                        max              : max,
                        resultList       : resultList,
                        totalCount       : totalCount,
                        resultFieldStyles: resultFieldStyles]"/>
            </g:if>
        </bootstrap:div>
        <wf:confirmDialog model="[formName: 'searchApplicatorForm']"/>
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
