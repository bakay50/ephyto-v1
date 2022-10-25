package com.webbfontaine.ephyto.interceptor

import com.webbfontaine.ephyto.erimm.Applicator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static com.webbfontaine.ephyto.constants.Operations.OC_CANCEL_VALID


class CancelInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteInterceptor.class);
    def objectStoreService
    def ephytoSecurityService
    def applicatorWorkflowService

    CancelInterceptor() {
        match controller: 'applicator', action: 'cancel'
    }

    boolean before() {
        Applicator applicator
        if (params.conversationId) {
            applicator = objectStoreService.get(params.conversationId) as Applicator
            params.commitOperation = OC_CANCEL_VALID
        } else {
            applicator = Applicator.get(params.id)

            if (!applicator) {
                return false
            }

            applicatorWorkflowService.initOperations(applicator)
            params.commitOperation = OC_CANCEL_VALID
        }

        params.commitOperationName = applicatorWorkflowService.getCommitOperationName(OC_CANCEL_VALID)
        if (applicatorWorkflowService.checkCommitOperationAccess(params.commitOperation, applicator?.operations)) {
            return true
        } else {
            LOGGER.warn("id = {}, cid = {}. User {} try to perform {} operation but don't have access.",
                    applicator?.id, params.conversationId, ephytoSecurityService.getUserName(), params.commitOperation)
            redirect(uri: '/access-denied')
            return false
        }
    }

    boolean after() {
        true
    }
}
