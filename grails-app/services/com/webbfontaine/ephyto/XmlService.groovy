package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.xml.DocXmlBinder
import grails.transaction.Transactional
import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil

@Transactional
class XmlService {
    private static final EPHYTO_GEN_NAME = "ephytoGen"

    String objectToXml(domainInstance, domainName) {
        switch (domainName) {
            case EPHYTO_GEN_NAME:
                return XmlUtil.serialize(
                        DocXmlBinder.bindEphytoGenToXml(domainInstance)
                )
            default:
                return null
        }


    }

    def buildObjectInstance(GPathResult importedXml, domainName) throws RuntimeException {
        def domainInstance
        if (domainName == EPHYTO_GEN_NAME) {
            domainInstance = DocXmlBinder.bindXmlToEphytoGen(importedXml)
        }
        domainInstance
    }

}
