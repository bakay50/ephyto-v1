package com.webbfontaine.ephyto.sad.http

import com.webbfontaine.grails.plugins.security.josso.Utils
import grails.util.Holders
import org.apache.http.HttpEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.LaxRedirectStrategy
import org.apache.http.util.EntityUtils
import javax.servlet.http.HttpServletRequest




/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Y. SYLLA
 * Date: 10/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class SadHttpClient {

    static final String EMPTY = ""
    static final String JOSSO_SINGLE_SIGN_ON_COOKIE = "JOSSO_SESSIONID"
    public static String getSadXmlString(String url,String clearanceOfficeCode, String declarationSerial,String declarationNumber,String declarationDate , HttpServletRequest request) {
        String result = null
        boolean isLocalhost = Holders.config.rest?.isLocalhost
        String localJossoCookie = Holders.config.rest?.jossoCookieString
        String jossoCookieString = "${JOSSO_SINGLE_SIGN_ON_COOKIE}=${isLocalhost?localJossoCookie?.toString():Utils.getJossoCookie(request)?.value}"

            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .build()
            HttpGet httpGet = new HttpGet(createSadRequestString(url, clearanceOfficeCode, declarationSerial, declarationNumber, declarationDate))
            httpGet.addHeader("Cookie", jossoCookieString)
            httpGet.addHeader("Accept", "application/json");
            httpGet.addHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(httpGet)
            try {
                HttpEntity entity = response.getEntity()
                result = EntityUtils.toString(entity)
            } catch (e) {
                throw new RuntimeException(e)
            } finally {
                response.close()
            }

        return result
    }

    private static String createSadRequestString(String url,String clearanceOfficeCode, String declarationSerial,String declarationNumber,String declarationDate){
        StringBuilder sb = new StringBuilder()
        String leadingSlash = url[-1] == "/" ? EMPTY : "/"
        sb.append(url).append(leadingSlash).append(clearanceOfficeCode).append("/").append(declarationSerial).append("/").append(declarationNumber).append("/").append(formatDate(declarationDate))
        sb.toString()
    }

    private static String formatDate(String dateValue) {
        if (dateValue) {
            if(dateValue.count("/") == 2){
                return dateValue.replaceAll("/", "-")
            }
        }
        return null
    }
}
