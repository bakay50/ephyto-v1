<%@ page import="grails.util.Metadata" %>
<g:applyLayout name="sw-page">
    <html>
    <head>
        <wf:jsI18nSupport/>
        <layout:resource name="application"/>
    </head>

    <body>
    <content tag="breadcrumbs">
        <bootstrap:breadcrumb>
            <bootstrap:breadcrumbItem>
                <a href="${grailsApplication.config.trade.path}">
                    <wf:message code="layout.home"/>
                </a>
            </bootstrap:breadcrumbItem>
            <bootstrap:breadcrumbItem last="true">
                <layout:appName/>
            </bootstrap:breadcrumbItem>
            <bootstrap:breadcrumbItem class="pull-right">
                <a href="#"><wf:message prefix="layout" code="version"/>: <g:meta
                        name="${Metadata.APPLICATION_VERSION}"/></a>
            </bootstrap:breadcrumbItem>
        </bootstrap:breadcrumb>
        <wf:loadSystemMessages appName="ephyto"/>
    </content>
    <content tag="services">
        <li style="width:200px !important;">
            <g:render template="/layouts/services"/>
        </li>
        <g:pageProperty name="page.services"/>
    </content>
    <content tag="application">
        <g:pageProperty name="page.application"/>
    </content>
    </body>
    </html>
</g:applyLayout>