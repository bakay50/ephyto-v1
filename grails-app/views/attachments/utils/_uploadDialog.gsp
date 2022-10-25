<%@ page import="grails.util.Holders" %>
<bootstrap: id="uploadDialog" title="${message(code: 'attachedDocs.uploadDialog.title', default: 'Upload file')}"
            class="modal" style="display:none; width: 600px; left: 55%; top: 60%">
    <bootstrap:div class="modal-header">
        <a class="close" id="closeButton">×</a>

        <h3><g:message code="attachedDocs.uploadDialog.title"/></h3>
    </bootstrap:div>
    <g:form url="[controller: controller, action: 'uploadFile']" name="fileUpload"
            enctype='multipart/form-data'>
        <bootstrap:div class="modal-body">
            <wf:fileInput id="attDoc" name="attDoc" accept="text/xml"/>
            <g:hiddenField name="fileExtension"/>
            <g:hiddenField name="preAttNumber"/>
        </bootstrap:div>

        <bootstrap:div class="modal-footer">
            <wf:submitButton name="startUpload" class="btn btn-success"/>
        </bootstrap:div>
    </g:form>


    <bootstrap: id="attUploadProgress">
        <bootstrap: class="uploadPercent" id="uploadPercent"></bootstrap:>

        <bootstrap: class="progressBar" id="progressBar"></bootstrap:>

        <bootstrap: class="ieProgressBar" id="ieProgressBar"></bootstrap:>
    </bootstrap:>

    <p style="color: green;">
        <b style="margin-left: 5px"><g:message code="utils.upload.accepted.formats"/>:</b>
        ${grails.util.Holders.grailsApplication.config.attachmentAcceptedFormats.join(', ')}
    </p>

    <p style="color: green;">
        <b style="margin-left: 5px"><g:message
                code="utils.upload.acceptedPDF.maxSize"/>:</b> ${Holders.config.attachmentMaxSizeBytes / (1024 * 1024)} MB
    </p>
</bootstrap:>