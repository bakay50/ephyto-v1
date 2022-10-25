<bootstrap:div style="display:none;width: 600px; height:600px; left: 30%; top: 30%;" class="modal fade"
               id="uploadDialogbis" aria-hidden="false" name="uploadDialogbis">
    <bootstrap:div class="modal-dialog">
        <bootstrap:div class="modal-content">
            <bootstrap:div class="modal-header" style="padding: 4px 4px !important;">
                <g:render template="/attachedDoc/attachedDocHeader"
                          model="[ephytoGenInstance: ephytoGenInstance, attdocInstance: attdocInstance]"/>
            </bootstrap:div>

            <bootstrap:form id="fileUpload"
                            url="[controller: 'attachment', action: 'uploadAttDoc']"
                            enctype="multipart/form-data"
                            name="fileUpload" target="hidden-upload-frames"
                            method="post">
                <bootstrap:div class="modal-body" style="height:200px !important;">
                    <bootstrap:div id="content">
                        <g:render template="/attachedDoc/attachedDocForm"
                                  model="[ephytoGenInstance: ephytoGenInstance, attdocInstance: attdocInstance]"/>
                    </bootstrap:div>

                </bootstrap:div>
                <bootstrap:modalFooter class="modal-footer" style=" height: 100px; margin-top: 30px">
                    <g:render template="/attachedDoc/attachedDocFooter"
                              model="[ephytoGenInstance: ephytoGenInstance, attdocInstance: attdocInstance]"/>
                </bootstrap:modalFooter>

            </bootstrap:form>

        </bootstrap:div>
    </bootstrap:div>
    <iframe onload="onUploadCompleteModal();" style="display: none" name="hidden-upload-frames"
            id="hidden-upload-frames"></iframe>
</bootstrap:div>