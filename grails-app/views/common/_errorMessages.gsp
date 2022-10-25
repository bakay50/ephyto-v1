<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<g:if test="${hasErrors}">
    <wf:alert class="alert-error errorContainer mainErrorContainer">
    %{--Error container for EphtytoGen--}%
        <wf:beanErrors bean="${ephytoGenInstance}" class="errorMes"/>
        <g:set var="goods" value="${ephytoGenInstance?.itemGoods}"/>
        <g:set var="attachments" value="${ephytoGenInstance?.attachments}"/>
        <g:set var="treatments" value="${ephytoGenInstance?.itemTreatments}"/>

        <g:each in="${goods}" status="i" var="item">
            <g:hasErrors bean="${goods}">
                <wf:beanErrors bean="${goods}" class="hasInnerForm" id="${item?.itemNumber}_itemsTab_itemFld"/>
            </g:hasErrors>
        </g:each>

        <g:each in="${treatments}" status="i" var="treatment">
            <g:hasErrors bean="${treatment}">
                <wf:beanErrors bean="${treatment}" class="hasInnerForm" id="${treatment?.itemNumber}_treatmentTab_treatmentFld"/>
            </g:hasErrors>
        </g:each>

        <g:each in="${attachments}" status="i" var="attachment">
            <g:hasErrors bean="${attachment}">
                <wf:beanErrors bean="${attachment}" class="hasInnerForm attFld_${attachment.attNumber}" id="${attachment.attNumber}_attachmentsTab_attFld"/>
            </g:hasErrors>
        </g:each>

    </wf:alert>
</g:if>
<div id="xmlErrors"></div>
