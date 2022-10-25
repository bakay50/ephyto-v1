<%@ page import="grails.util.Holders;com.webbfontaine.ephyto.TypeCastUtils;java.net.URLEncoder;java.net.URLDecoder;java.nio.charset.StandardCharsets" %>
<wf:hiddenField value="${URLEncoder.encode(itemGoodInstance?.quantity == null ? "" : itemGoodInstance?.quantity?.toString() ,StandardCharsets.UTF_8.name())}" id="quantityUTF" name="quantityUTF" />
<wf:hiddenField value="${URLEncoder.encode(itemGoodInstance?.netWeight == null ? "" : itemGoodInstance?.netWeight?.toString() ,StandardCharsets.UTF_8.name())}" id="netWeightUTF" name="netWeightUTF" />
<wf:hiddenField value="${URLEncoder.encode(itemGoodInstance?.grossWeight == null ? "" : itemGoodInstance?.grossWeight?.toString() ,StandardCharsets.UTF_8.name())}" id="grossWeightUTF" name="grossWeightUTF" />
