<%@ page import="com.webbfontaine.grails.plugins.utils.TypesCastUtils; com.webbfontaine.grails.plugins.utils.TypesCastUtils; com.webbfontaine.ephyto.EphytoGenUtils; com.webbfontaine.ephyto.LogUtils;" %>

<g:if test="${LogUtils.dateTitleRequired(log)}">
    <bootstrap:div class="control-group">
        <strong>${com.webbfontaine.grails.plugins.utils.TypesCastUtils.formatDate(log?.date)}</strong>
        <br>
    </bootstrap:div>
</g:if>
<bootstrap:div class="control-group-sub">
    [${com.webbfontaine.grails.plugins.utils.TypesCastUtils.formatTime(log?.date)}]
    ${log.userLogin}:
    <g:message
            code="default.committed.labelBefore"/> <strong>${message(code: 'ephytoGen.operation.' + log?.operationHumanName())}</strong><g:message
        code="default.committed.labelAfter"/>.
<g:message
        code="default.newStatus.label"/> <strong>${message(code: 'ephytoGen.status.' + log?.endStatus, default: log?.endStatus)}</strong>
    <g:if test="${log.message}">
        <bootstrap:div style="clear: both; color: #222222">
            <bootstrap:div style="word-wrap: break-word; max-width: 900px">
                [${com.webbfontaine.grails.plugins.utils.TypesCastUtils.formatTime(log?.date)}] <strong>${log?.userLogin}: ${message(code: "ephytoGen.${log?.operationHumanName()?.toLowerCase()}.message.label")}:</strong>
                <g:each in="${log.message?.split("\n")}">
                    ${message(code: it)}<br/>
                </g:each>
            </bootstrap:div>
        </bootstrap:div>
    </g:if>
</bootstrap:div>
