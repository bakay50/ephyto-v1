package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.constants.Operations
import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.validation.rules.Rule
import com.webbfontaine.grails.plugins.validation.rules.RuleContext
import grails.plugin.springsecurity.SpringSecurityService
import org.grails.core.DefaultGrailsDomainClass
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.VALIDATE_INSTANCE
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getParam
import static com.webbfontaine.grails.plugins.utils.WebRequestUtils.getConversationId

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 31/03/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class VersionCheckingRule implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionCheckingRule.class);

    SpringSecurityService springSecurityService

    @Override
    void apply(RuleContext ruleContext) {
        def ephytoGen = ruleContext.getTargetAs(EphytoGen)

        if (ephytoGen.notProperlyInitialized || !validateInstance) {
            return
        }

        if (action == Operations.OA_UPDATE) {
            def dbVersion = getOriginalVersion(ephytoGen.id)
            if (dbVersion != null) {
                if (dbVersion != ephytoGen.version) {
                    if (ephytoGen.version > dbVersion) {
                        def dbInstance = EphytoGen.get(ephytoGen.id)
                        dbInstance.refresh()
                        LOGGER.warn("id = {}, cid = {}. User = {}. Error while updating document because DB version mismatch for tvfGen. DB Version = {}. current version = {}. tvfGen: {}",
                                ephytoGen.id, conversationId, springSecurityService?.principal?.username, dbVersion, ephytoGen.version, detailedDump(dbInstance, ephytoGen))
                    } else {
                        ruleContext.rejectValue("version", "default.optimistic.locking.failure", "Another user has updated this document while you were editing")
                    }
                }
            } else {
                ruleContext.rejectValue("version", "default.not.found.message", ["Document", ephytoGen.id] as Object[], "Document not found")
            }
        }

    }

    private def getValidateInstance() {
        getParam(VALIDATE_INSTANCE)
    }

    private def getAction() {
        com.webbfontaine.grails.plugins.utils.WebRequestUtils.getAction()
    }

    private Long getOriginalVersion(ephytoGenID) {
        return (Long) EphytoGen.createCriteria().list {
            eq("id", ephytoGenID)
            projections {
                property("version")
            }
        }[0]
    }

    private String detailedDump(def dbVersion, def ephytoGen) {
        StringBuilder sb = new StringBuilder()
        new DefaultGrailsDomainClass(EphytoGen.class).persistentProperties.each {
            if (!(dbVersion[it.name] == null && ephytoGen[it.name] == null) && !(dbVersion[it.name]?.equals(ephytoGen[it.name]))) {
                sb.append("***************** ")
            }
            sb.append("${it.name}  ${dbVersion[it.name]} => ${ephytoGen[it.name]}\n")
        }
        return sb.toString()

    }
}
