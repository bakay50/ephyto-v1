package com.webbfontaine.ephyto.applicator

import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.grails.plugins.conversation.store.session.SessionStore
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j

@Slf4j("LOGGER")
@Transactional
class ApplicatorService {

    SessionStore sessionStoreService

    def addToSessionStore(Applicator applicator, conversationId = null) {
        if (conversationId) {
            sessionStoreService.put(conversationId, applicator)
        } else {
            sessionStoreService.put(applicator)
        }
    }
}
