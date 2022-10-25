<%@ page import="com.webbfontaine.ephyto.utils.EphytoSecurityServiceUtils" %>

<g:if test="${EphytoSecurityServiceUtils.roleHasAccess()}">
<sec:access expression="hasAnyRole('ROLE_EPHYTO_DECLARANT', 'ROLE_EPHYTO_TRADER')">
    <li>
        <g:link controller="ephytoGen" action="create">
            <bootstrap:icon name="pencil"/>
            <layout:message prefix="ephyto" code="createpcd.label"/>
        </g:link>
    </li>
    <li class="divider"></li>
</sec:access>
<sec:access expression="hasAnyRole('ROLE_EPHYTO_SUPER_ADMINISTRATOR')">
    <li>
        <g:link controller="applicator" action="create">
            <bootstrap:icon name="pencil"/>
            <layout:message prefix="applicator" code="create.label"/>
        </g:link>
    </li>
    <li>
        <g:link controller="applicator" action="list">
            <bootstrap:icon name="search"/>
            <layout:message prefix="applicator" code="search.label"/>
        </g:link>
    </li>
</sec:access>
    <li>
        <g:link controller="ephytoGen" action="list">
            <bootstrap:icon name="search"/>
            <layout:message prefix="ephyto" code="search.label"/>
        </g:link>
    </li>
</g:if>