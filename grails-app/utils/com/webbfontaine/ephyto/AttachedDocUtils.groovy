package com.webbfontaine.ephyto


/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 04/10/2017
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class AttachedDocUtils {

    static List getPaginateList(def domain, String listName){
        def max = 10
        def size = domain?."${listName}"?.size()
        return domain?."${listName}"?.subList(0,(max > size)? size : max)
    }

    static List getSubList(List domainList, Integer max, Integer offset){
        def listSize =  domainList.size()
        return domainList.subList(offset, (max+offset) > listSize ? listSize : (max+offset))
    }


    static String setFileContentType(String extension) {
        String contentType

        if(extension.equalsIgnoreCase("png")) {
            contentType = "image/png"
        } else
        if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
            contentType = "image/jpeg"
        } else
        if(extension.equalsIgnoreCase("gif")) {
            contentType = "image/gif"
        } else
        if(extension.equalsIgnoreCase("tiff") || extension.equalsIgnoreCase("tif")) {
            contentType = "image/tiff"
        } else
        if(extension.equalsIgnoreCase("bmp")) {
            contentType = "image/bmp"
        } else
        if(extension.equalsIgnoreCase("pdf")) {
            contentType = "application/pdf"
        } else
        if(extension.equalsIgnoreCase("txt")) {
            contentType = "text/plain"
        } else
        if(extension.equalsIgnoreCase("xsl")) {
            contentType = "application/excel"
        } else
        if(extension.equalsIgnoreCase("doc")) {
            contentType = "application/msword"
        } else
        if(extension.equalsIgnoreCase("docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        } else
        if(extension.equalsIgnoreCase("xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        } else {
            contentType = "application/octet-stream"
        }

        return contentType
    }
}
