<%@ page import="grails.util.Holders" %>

<g:hiddenField name="openItemGood" value="${createLink(controller: 'itemGood', action: 'openItemGood')}"/>
<g:hiddenField name="saveItemUrl" value="${createLink(controller: 'itemGood', action: 'saveItem')}"/>
<g:hiddenField name="showItemUrl" value="${createLink(controller: 'itemGood', action: 'showItem')}"/>
<g:hiddenField name="showEditItemUrl" value="${createLink(controller: 'itemGood', action: 'showEditItem')}"/>
<g:hiddenField name="updateItemUrl" value="${createLink(controller: 'itemGood', action: 'updateItem')}"/>
<g:hiddenField name="deleteItemUrl" value="${createLink(controller: 'itemGood', action: 'deleteItem')}"/>

