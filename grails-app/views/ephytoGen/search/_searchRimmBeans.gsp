<%@ page import="com.webbfontaine.ephyto.ReferenceUtils; com.webbfontaine.ephyto.constants.UserPropertyConstants; com.webbfontaine.ephyto.BusinessLogicUtils; com.webbfontaine.ephyto.constants.ReferenceConstants;org.joda.time.LocalDate" %>
<g:set var="htDate" value="${LocalDate.now().toDate().clearTime()}"/>
<wf:hiddenField name="locale" id="locale" value="${org.springframework.context.i18n.LocaleContextHolder.getLocale()}"/>
<wf:localRef beanAlias="HT_CUO" selectFields="${ReferenceConstants.COMMON_SELECT_FIELDS_NAME_STR}"
             htDate="${htDate}" renderOnView="true"/>

<wf:localRef beanAlias="RIMM_APL" selectFields="${ReferenceConstants.APPLICATOR_SELECT_FIELDS_STR}" renderOnView="true"/>
<g:set var="tinProperties" value="${BusinessLogicUtils.hasUserProperties(UserPropertyConstants.TIN)}"/>
<g:if test="${tinProperties.hasUserProperties}">
    <wf:hiddenField name="tinUserProps" value="true"/>
    <g:if test="${tinProperties.hasDefaultProperty}">
        <g:set var="defTinProperty" value="${tinProperties.hasDefaultProperty ? tinProperties.userPropValues[0] : null}"
               scope="request"/>
    </g:if>
    <g:else>
        <wf:localRefCustom refName="REF_CMP" selectFields="${ReferenceConstants.COMMON_SELECT_FIELDS_NAME_STR}"
                           from="${ReferenceUtils.getCompanyFields(tinProperties.userPropValues)?:[""]}" renderOnView="true"/>
    </g:else>
</g:if>

<g:set var="decProperties" value="${BusinessLogicUtils.hasUserProperties(UserPropertyConstants.DEC)}" scope="request"/>
<g:if test="${decProperties.hasUserProperties}">
    <wf:hiddenField name="decUserProps" value="true"/>
    <g:if test="${decProperties.hasDefaultProperty}">
        <g:set var="defDecProperty" value="${decProperties.hasDefaultProperty ? decProperties.userPropValues[0] : null}"
               scope="request"/>
    </g:if>
    <g:else>
        <wf:localRefCustom refName="REF_DEC" selectFields="${ReferenceConstants.COMMON_SELECT_FIELDS_NAME_STR}"
                           from="${ReferenceUtils.getDeclarantFields(decProperties?.userPropValues)?:[""]}" renderOnView="true"/>
    </g:else>
</g:if>

<g:set var="officeAccessProperties" value="${BusinessLogicUtils.hasUserProperties(UserPropertyConstants.OFFICE)}"/>

<g:if test="${officeAccessProperties.hasUserProperties}">
    <wf:hiddenField name="officeAccessUserProps" value="true"/>
    <g:if test="${officeAccessProperties.hasDefaultProperty}">
        <g:set var="defOfficeAccessProperty"
               value="${officeAccessProperties.hasDefaultProperty ? officeAccessProperties.userPropValues[0] : null}"
               scope="request"/>
    </g:if>
    <g:else>
        <wf:localRefCustom refName="HT_CUO" selectFields="${ReferenceConstants.DECLARANT_SELECT_FIELDS_STR}" renderOnView="true"
                           from="${ReferenceUtils.getOfficeAccessFields(officeAccessProperties.userPropValues)?:[""]}"/>
    </g:else>
</g:if>