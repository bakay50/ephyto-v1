<%@ page import="com.webbfontaine.ephyto.ReferenceUtils;com.webbfontaine.ephyto.constants.Statuses; com.webbfontaine.ephyto.TypeCastUtils;com.webbfontaine.ephyto.constants.UserPropertyConstants; com.webbfontaine.ephyto.BusinessLogicUtils;org.springframework.web.servlet.support.RequestContextUtils" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.webbfontaine.ephyto.TypeCastUtils;java.net.URLEncoder;java.net.URLDecoder;java.nio.charset.StandardCharsets" %>
<bootstrap:grid grid="12(4-8)">
    <bootstrap:formSection>
        <bootstrap:formGroup groupSpan="12" labelSpan="2"  name="ephytoGen.search.status.label"
                             default="Status">
            <bootstrap:formInput span="2">
                <wf:select class="span2"
                           name="status"
                           value="${searchCommand?.status}"
                           optionKey="key"
                           optionValue="value"
                           noSelection="['': message(code: 'ephytoGen.all.label', default: 'ALL')]"
                           from="${[
                                   [key: Statuses.ST_STORED, value: message(code: "ephyto.status." + Statuses.ST_STORED)],
                                   [key: Statuses.ST_REQUESTED, value: message(code: "ephyto.status." + Statuses.ST_REQUESTED)],
                                   [key: Statuses.ST_QUERIED, value: message(code: "ephyto.status." + Statuses.ST_QUERIED)],
                                   [key: Statuses.ST_CANCELED, value: message(code: "ephyto.status." + Statuses.ST_CANCELED)],
                                   [key: Statuses.ST_REJECTED, value: message(code: "ephyto.status." + Statuses.ST_REJECTED)],
                                   [key: Statuses.ST_APPROVED, value: message(code: "ephyto.status." + Statuses.ST_APPROVED)],
                                   [key: Statuses.ST_REPLACED, value: message(code: "ephyto.status." + Statuses.ST_REPLACED)],
                                   [key: Statuses.ST_SIGNED, value: message(code: "ephyto.status." + Statuses.ST_SIGNED)],


                           ]}"/>

            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.userReference.label">
            <bootstrap:formInput span="2">
                <wf:textInput name="userReference" id="userReference"
                              value="${searchCommand.userReference}" disabled="${false}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.requestNumber.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" name="requestNumber" value="${searchCommand.requestNumber}"/>
            </bootstrap:formInput>

            <bootstrap:label name="ephytoGen.search.requestDate.label" span="2"/>
            <bootstrap:formInput span="2">
                <wf:selectOperators name="op_requestDate" value="${searchCommand?.op_requestDate}"
                                    class="span2"
                                    optionKey="key"
                                    optionValue="value"
                                    from="${[[key: 'equals', value: message(code: 'search.equals.operator', default: 'equals')],
                                             [key: 'less than', value: message(code: 'search.lessThan.operator', default: 'less than')],
                                             [key: 'greater than', value: message(code: 'search.greaterThan.operator', default: 'greater than')],
                                             [key: 'between', value: message(code: 'search.between.operator', default: 'between')]]}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.requestDate)}" id="requestDate"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="requestDate" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.requestDateTo)}" id="requestDateTo"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="requestDateTo" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>

        </bootstrap:formGroup>

        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.disinfectionCertificateRef.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" name="disinfectionCertificateRef"
                              value="${searchCommand.disinfectionCertificateRef}"/>
            </bootstrap:formInput>

            <bootstrap:label name="ephytoGen.search.disinfectionCertificateDate.label" span="2"/>
            <bootstrap:formInput span="2">
                <wf:selectOperators name="op_disinfectionCertificateDate"
                                    value="${searchCommand?.op_disinfectionCertificateDate}"
                                    class="span2"
                                    optionKey="key"
                                    optionValue="value"
                                    from="${[[key: 'equals', value: message(code: 'search.equals.operator', default: 'equals')],
                                             [key: 'less than', value: message(code: 'search.lessThan.operator', default: 'less than')],
                                             [key: 'greater than', value: message(code: 'search.greaterThan.operator', default: 'greater than')],
                                             [key: 'between', value: message(code: 'search.between.operator', default: 'between')]]}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.disinfectionCertificateDate)}"
                               id="disinfectionCertificateDate"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="disinfectionCertificateDate" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.disinfectionCertificateDateTo)}"
                               id="disinfectionCertificateDateTo"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="disinfectionCertificateDateTo" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>

        </bootstrap:formGroup>


        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.dockingPermissionRef.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" name="dockingPermissionRef" value="${searchCommand.dockingPermissionRef}"/>
            </bootstrap:formInput>

            <bootstrap:label name="ephytoGen.search.dockingPermissionDate.label" span="2"/>
            <bootstrap:formInput span="2">
                <wf:selectOperators name="op_dockingPermissionDate" value="${searchCommand?.op_dockingPermissionDate}"
                                    class="span2"
                                    optionKey="key"
                                    optionValue="value"
                                    from="${[[key: 'equals', value: message(code: 'search.equals.operator', default: 'equals')],
                                             [key: 'less than', value: message(code: 'search.lessThan.operator', default: 'less than')],
                                             [key: 'greater than', value: message(code: 'search.greaterThan.operator', default: 'greater than')],
                                             [key: 'between', value: message(code: 'search.between.operator', default: 'between')]]}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.dockingPermissionDate)}"
                               id="dockingPermissionDate"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="dockingPermissionDate" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.dockingPermissionDateTo)}"
                               id="dockingPermissionDateTo"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="dockingPermissionDateTo" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>

        </bootstrap:formGroup>


        <bootstrap:formGroup groupSpan="12" labelSpan="2"
                             name="ephytoGen.search.phytosanitaryCertificateRef.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" name="phytosanitaryCertificateRef"
                              value="${searchCommand.phytosanitaryCertificateRef}"/>
            </bootstrap:formInput>

            <bootstrap:label name="ephytoGen.search.phytosanitaryCertificateDate.label" span="2"/>
            <bootstrap:formInput span="2">
                <wf:selectOperators name="op_phytosanitaryCertificateDate"
                                    value="${searchCommand?.op_phytosanitaryCertificateDate}"
                                    class="span2"
                                    optionKey="key"
                                    optionValue="value"
                                    from="${[[key: 'equals', value: message(code: 'search.equals.operator', default: 'equals')],
                                             [key: 'less than', value: message(code: 'search.lessThan.operator', default: 'less than')],
                                             [key: 'greater than', value: message(code: 'search.greaterThan.operator', default: 'greater than')],
                                             [key: 'between', value: message(code: 'search.between.operator', default: 'between')]]}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.phytosanitaryCertificateDate)}"
                               id="phytosanitaryCertificateDate"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="phytosanitaryCertificateDate" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>
            <bootstrap:formInput span="2">
                <wf:datepicker value="${TypeCastUtils.fromDate(searchCommand?.phytosanitaryCertificateDateTo)}"
                               id="phytosanitaryCertificateDateTo"
                               onkeydown="return allowKeyPress(event);" onPaste="return false;"
                               style="width: 125px !important;margin-top: auto !important;"
                               name="phytosanitaryCertificateDateTo" class="span2" regexp="${'^[\\d /]+$'}"/>
            </bootstrap:formInput>

        </bootstrap:formGroup>

        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.customsClearanceOfficeCode.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" id="customsClearanceOfficeCode" name="customsClearanceOfficeCode"
                              value="${searchCommand.customsClearanceOfficeCode}"
                              disabled="${false}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>


        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.exporterCode.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" id="exporterCode" name="exporterCode"
                              value="${defTinProperty?defTinProperty:searchCommand.exporterCode}"
                              disabled="${defTinProperty?true:false}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.applicatorCode.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" id="applicatorCode" name="applicatorCode"
                              value="${searchCommand.applicatorCode}"
                              disabled="${false}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>


        <bootstrap:formGroup groupSpan="12" labelSpan="2" name="ephytoGen.search.declarantCode.label">
            <bootstrap:formInput span="2">
                <wf:textInput class="span2" id="declarantCode" name="declarantCode"
                              value="${defDecProperty ? defDecProperty : searchCommand.declarantCode}"
                              disabled="${defDecProperty ? true : false}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

    </bootstrap:formSection>

    <wf:hiddenField id="searchFormat" name="searchFormat" value="html"/>
    <code:searchButtons clear="resetEphytoCriteriaFields()"/>
</bootstrap:grid>

<g:render template="/utils/hiddenLinksSearch"/>



