package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.workflow.operations.UpdateOperation
import com.webbfontaine.grails.plugins.workflow.operations.OperationClass
import groovy.util.logging.Slf4j
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Slf4j
class DocAccessInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocAccessInterceptor.class)
    def ephytoSecurityService
    def ephytoGenBpmService
    def springSecurityService


    int order = HIGHEST_PRECEDENCE + 1

    DocAccessInterceptor() {
        match controller: 'ephytoGen', action: 'list'
    }

    boolean before() {
        GrailsWebRequest webRequest = GrailsWebRequest.lookup()
        if (params.id) {
            def ephytoGen = EphytoGen.get(params.id)
            if (ephytoGen) {
                ephytoGenBpmService.initOperationsForEdit(ephytoGen, params)
                if (!ephytoGen.operations.any {
                    it instanceof UpdateOperation || it instanceof OperationClass
                }) {
                    LOGGER.warn("User ${ephytoSecurityService.getUserName()} don't have access to any operation but try to edit document. Id = ${ephytoGen.id} ")
                    redirect(uri: '/access-denied')
                    return false
                } else {
                    return true
                }
            } else {
                return false
            }
        } else if (webRequest.actionName == 'list') {
            return true
        }
    }

    boolean after() {
        return true
    }

}
