<%@ page import="com.webbfontaine.ephyto.ReferenceUtils;com.webbfontaine.ephyto.constants.Statuses; com.webbfontaine.ephyto.TypeCastUtils;com.webbfontaine.ephyto.constants.UserPropertyConstants; com.webbfontaine.ephyto.BusinessLogicUtils;org.springframework.web.servlet.support.RequestContextUtils" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.webbfontaine.ephyto.TypeCastUtils;java.net.URLEncoder;java.net.URLDecoder;java.nio.charset.StandardCharsets" %>
<bootstrap:grid grid="12(4-8)">
    <bootstrap:formSection>
        <bootstrap:formGroup groupSpan="12" labelSpan="3" name="ephytoGen.search.status.label">
            <bootstrap:formInput span="3">
                <wf:select class="span2"
                           name="status"
                           value="${searchCommand?.status}"
                           optionKey="key"
                           optionValue="value"
                           noSelection="['': message(code: 'ephytoGen.all.label', default: 'ALL')]"
                           from="${[
                                   [key: Statuses.ST_VALID, value: message(code: "status." + Statuses.ST_VALID)],
                                   [key: Statuses.ST_INVALID, value: message(code: "status." + Statuses.ST_INVALID)],


                           ]}"/>

            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup groupSpan="12" labelSpan="3" name="applicator.agreement.label">
            <bootstrap:formInput span="3">
                <wf:textInput name="agreement" id="agreement"
                              value="${searchCommand.agreement}" disabled="${false}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>
        <bootstrap:formGroup groupSpan="12" labelSpan="3" name="applicator.code.label">
            <bootstrap:formInput span="3">
                <wf:textInput name="code" id="code"
                              value="${searchCommand.code}" disabled="${false}"/>
            </bootstrap:formInput>
        </bootstrap:formGroup>

    </bootstrap:formSection>

    <wf:hiddenField id="searchFormat" name="searchFormat" value="html"/>
    <wf:searchButtons/>
</bootstrap:grid>

