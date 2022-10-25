<bootstrap:div id="nameAndPartiesContainer">
    <layout:resource name="messages.js"/>

    <bootstrap:formSection>
        <g:if test="${applicatorInstance?.status}">
            <bootstrap:genericInput  field="status" id="status"
                                     name="status" bean="${applicatorInstance}"
                                     labelCode="applicator.status.label" maxlength="13" inputSpan="4"
                                     value="${message(code: "applicator.status.${applicatorInstance?.status}", default: "${applicatorInstance?.status}")}" />
        </g:if>
        <bootstrap:genericInput  field="agreement" id="agreement"
                                name="agreement" bean="${applicatorInstance}"
                                labelCode="applicator.agreement.label" maxlength="13" inputSpan="4" value="${applicatorInstance?.agreement}"/>

        <bootstrap:genericInput  field="code" id="code"
                                name="code" bean="${applicatorInstance}"
                                labelCode="applicator.code.label" maxlength="13" inputSpan="4"  value="${applicatorInstance?.code}"/>
        <bootstrap:genericInput  field="nameAddress" id="nameAddress" type="area"
                                name="nameAddress"  bean="${applicatorInstance}"
                                labelCode="applicator.address.label" maxlength="4000" inputSpan="5"
                                 inputSpan="6" rows="4" value="${applicatorInstance?.nameAddress}" />
    </bootstrap:formSection>
</bootstrap:div>

