<%@ page import="grails.util.Holders" %>
<wf:hiddenField name="addAttURL" id="addAttURL" disabled=""
                value="${createLink(controller: controller, action: 'addAttachedDoc')}"/>
<wf:hiddenField name="removeAttURL" id="removeAttURL"
                value="${createLink(controller: controller, action: 'removeAttachment')}" disabled=""/>
<wf:hiddenField name="uploadURL" id="uploadURL" value="${createLink(controller: controller, action: 'uploadFile')}"/>
<wf:hiddenField name="removeAttFile" id="removeAttFile" disabled=""
                value="${createLink(controller: controller, action: 'removeAttFile')}"/>
<wf:hiddenField name="updateAttURL" id="updateAttURL" disabled=""
                value="${createLink(controller: controller, action: 'updateAtt')}"/>
<wf:hiddenField name="cancelEditURL" id="cancelEditURL" disabled=""
                value="${createLink(controller: controller, action: 'cancelEdit')}"/>
<wf:hiddenField name="attachmentAcceptedFormats" disabled=""
                value="${Holders.grailsApplication.config.attachmentAcceptedFormats.join(',')}"/>
<g:hiddenField name="openAttDoc" value="${createLink(controller: controller, action: 'openAttDoc')}"/>
<g:hiddenField name="openAttDocForEdit" value="${createLink(controller: controller, action: 'openAttDocForEdit')}"/>
