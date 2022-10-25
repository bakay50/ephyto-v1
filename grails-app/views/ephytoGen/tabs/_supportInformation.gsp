<%@ page import="com.webbfontaine.ephyto.constants.Operations; com.webbfontaine.ephyto.LogUtils; com.webbfontaine.ephyto.constants.Statuses; grails.util.Holders" %>
<bootstrap:div id="queryAndNotificationsContainer" class="form-horizontal">
    <bootstrap:div id="supInfoContent">
        <bootstrap:div class="control-group">
            <label><strong><g:message
                    code="ephytoGen.owner.label"/></strong> ${ephytoGenInstance?.ownerUser}@${ephytoGenInstance?.ownerGroup}
            </label>
        </bootstrap:div>

        <bootstrap:div class="control-group">
            <label><strong><g:message
                    code="ephytoGen.status.label"/></strong> <g:message code="status.${ephytoGenInstance?.status}"
                                                                        default="${ephytoGenInstance?.status}"/>
            </label>
        </bootstrap:div>

        <g:set var="lastOperationLog" value="${LogUtils.getLastOperationLog(ephytoGenInstance)}"/>
        <g:each in="${ephytoGenInstance?.logs}" var="log">
            <g:render template="utils/logRecord" model="[log: log, lastOperationLog: lastOperationLog]"/>
        </g:each>
        <g:if test="${Statuses.PERMITTED_STATUSES_FOR_QUERY_MSG.contains(ephytoGenInstance?.status) && ephytoGenInstance?.startedOperation != com.webbfontaine.ephyto.constants.Operations.OI_APPROVE_REQUESTED && !actionName?.contains("show")}">
            <sec:ifAnyGranted roles="ROLE_EPHYTO_GOV_OFFICER,ROLE_EPHYTO_GOV_SENIOR_OFFICER">
                <bootstrap:formSection>
                    <bootstrap:formGroup labelClass="h4" labelCode="ephytoGen.message.label">
                        <bootstrap:formInput span="6">
                            <wf:genericInput type="area" value="${params.message}" name="message"
                                             style="resize: vertical;" field="message" inputSpan="10" rows="4"
                                             maxlength="1024"/>
                        </bootstrap:formInput>
                    </bootstrap:formGroup>
                </bootstrap:formSection>
            </sec:ifAnyGranted>
        </g:if>
    </bootstrap:div>
</bootstrap:div>

