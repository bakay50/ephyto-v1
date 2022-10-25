<!doctype html>
<html>
<head>
    <meta name="layout" content="page"/>
</head>

<body>

<content tag="services">
    <g:render template="/layouts/services"/>
</content>


<content tag="application">
    <div class="control-group" style="text-align: center;padding-top: 10%">

        <h3><strong>${message(code: 'default.accessDenied.label', default: 'Access denied')}</strong></h3>
        <br/>

        <h1>${message(code: 'default.accessDeniedMessage.label', default: 'Forbidden: You don\'t have permission to access requested page')}</h1>

    </div>
</content>
</body>
</html>