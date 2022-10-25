package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.gen.EphytoGen
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.webbfontaine.ephyto.constants.Operations.OI_DELETE_STORED

@Slf4j
class DeleteInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteInterceptor.class);
    def objectStoreService
    def ephytoSecurityService
    def ephytoGenBpmService
    def conversationService

    DeleteInterceptor() {
        match controller: '*', action: 'delete'
    }

    boolean before() {
        EphytoGen ephytoGen
        if (params.conversationId) {
            ephytoGen = objectStoreService.get(params.conversationId) as EphytoGen
            params.commitOperation = OI_DELETE_STORED
        } else {
            ephytoGen = EphytoGen.get(params.id)

            if (!ephytoGen) {
                return false
            }

            ephytoGenBpmService.initOperations(ephytoGen)
            params.commitOperation = OI_DELETE_STORED
        }

        params.commitOperationName = ephytoGenBpmService.getCommitOperationName(OI_DELETE_STORED)
        if (!ephytoGenBpmService.checkCommitOperationAccess(params.commitOperation, ephytoGen?.operations)) {
            LOGGER.warn("id = {}, cid = {}. User {} try to perform {} operation but don't have access.",
                    ephytoGen?.id, params.conversationId, ephytoSecurityService.getUserName(), params.commitOperation)
            redirect(uri: '/access-denied')
            return false
        } else {
            return true
        }
    }

    boolean after() {
        true
    }

    void afterView() {
        true
    }

}
