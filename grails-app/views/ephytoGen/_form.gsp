<bootstrap:tab name="ephytoGenTab" id="ephytoGenTab" labelPrefix="ephytoGen" type="flat">
    <bootstrap:tabItem name="header" active="true">
        <g:render template="/ephytoGen/tabs/header" model="[ephytoGenInstance: ephytoGenInstance]"/>
    </bootstrap:tabItem>
    <bootstrap:tabItem name="nameAndParties">
        <g:render template="/ephytoGen/tabs/nameAndParties" model="[ephytoGenInstance: ephytoGenInstance]"/>
    </bootstrap:tabItem>
    <bootstrap:tabItem name="treatments">
        <bootstrap:div class="f_treatmentContainer" id="f_treatmentContainer">
            <g:render template="/ephytoGen/tabs/treatments" model="[ephytoGenInstance: ephytoGenInstance]"/>
        </bootstrap:div>
    </bootstrap:tabItem>
    <bootstrap:tabItem name="item">
        <bootstrap:div id="goodsContainer">
        <g:render template="/ephytoGen/tabs/goods" model="[ephytoGenInstance: ephytoGenInstance]"/>
            </bootstrap:div>
    </bootstrap:tabItem>
    <bootstrap:tabItem name="attachments">
        <g:render template="/ephytoGen/tabs/attachments" model="[certificatInstance: certificatInstance]"/>
    </bootstrap:tabItem>
    <g:if test="${ephytoGenInstance?.status}">
        <bootstrap:tabItem name="queryAndNotifications">
            <g:render template="/ephytoGen/tabs/supportInformation" model="[ephytoGenInstance: ephytoGenInstance]"/>
        </bootstrap:tabItem>
    </g:if>
</bootstrap:tab>