package com.webbfontaine.ephyto

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.grails.plugins.rimm.cmp.Company
import com.webbfontaine.grails.plugins.rimm.dec.Declarant
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.transactions.Transactional
import grails.util.Holders
import org.slf4j.LoggerFactory
import org.springframework.context.i18n.LocaleContextHolder

import org.joda.time.LocalDate

import static com.webbfontaine.ephyto.constants.Operations.OI_SIGN_APPROVED
import static com.webbfontaine.ephyto.constants.Operations.OP_SIGN
import static com.webbfontaine.ephyto.constants.Statuses.ST_SIGNED
import static com.webbfontaine.grails.plugins.validation.rules.ref.utils.RefUtils.isHistorized
import static com.webbfontaine.grails.plugins.validation.rules.ref.utils.RefUtils.withHistorizedCriteria

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Yacouba SYLLA
 * Date: 20/11/2017
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */

@Transactional
class MailNotificationService implements GrailsConfigurationAware{

    private static final LOGGER = LoggerFactory.getLogger(MailNotificationService);

    def mailService
    def groovyPageRenderer
    boolean mailEnabled
    def notifyByMail(def domainInstance, String commitOp) {
        LOGGER.debug("In notifyByMail() method of ${MailNotificationService}")
        if (mailEnabled) {
            def mails
            try {
                mails = createMails(domainInstance, commitOp)
            } catch (e) {
                LOGGER.error(e.getMessage(), e)
                return
            }
            if (mails) {
                mails.each { Mail mail ->
                    if(mail?.recipients){
                        try {
                            LOGGER.info("Sending mail [${mail.mailSubject}] to ${mail.recipients}")
                            mailService.sendMail {
                                async true
                                to mail.recipients
                                subject mail?.mailSubject
                                html groovyPageRenderer.render(view: "/common/_mailMessage", model: [mailMsgBody: mail?.mailMsgBody])
                            }
                        } catch (e) {
                            LOGGER.error(e.getMessage(), e)
                        }
                    }else{
                        LOGGER.info("Error when sending mail, mail recipient is not found!")
                    }

                }
            }
        }
    }

    private static final class Mail {
        String mailSubject
        String mailMsgBody
        String[] recipients
    }



    private static getMessageSource() {
        Holders.applicationContext.messageSource
    }

    private static getMessageValue(String code, Object[] args) {
        messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
    }

    public static def getMailSubject(EphytoGen ephytoGen){
        return getMessageValue("ephyto.mail.sign.subject",[ephytoGen.requestNumber,ephytoGen.requestDate] as Object[])
    }


    public static def getMailMsgBody(EphytoGen ephytoGen){
        return getMessageValue("ephyto.mail.sign.body", [ephytoGen.requestNumber,ephytoGen.declarantName,ephytoGen.exporterName] as Object[])
    }

    def createMails(EphytoGen domainInstance, String operation){
        String endStatus = domainInstance.status
        def mails = []
        def mailSubject
        def mailMsgBody

        def recipient = []

        switch (operation){
            case OP_SIGN:
            case OI_SIGN_APPROVED:
                if(endStatus.equals(ST_SIGNED)){
                    mailSubject = getMessageValue("ephyto.mail.sign.subject", [domainInstance?.requestNumber,domainInstance?.requestDate] as Object[])
                    mailMsgBody = getMessageValue("ephyto.mail.sign.body", [domainInstance?.requestNumber,domainInstance?.declarantName,domainInstance?.exporterName] as Object[])
                }
                break

            default:
                LOGGER.info("Unable to create mail using ${operation} operation. Operation not supported.")
                break
        }
        if(mailSubject){
            Declarant decData = getDecData(domainInstance)
            if(decData?.email){
                recipient.add(decData.email)
            }
            Company cmpData = getCmpData(domainInstance)
            if(cmpData?.email){
                recipient.add(cmpData.email)
            }
        }
        if(recipient.size() > 0){
            mails.add(new Mail(mailSubject: mailSubject, mailMsgBody: mailMsgBody, recipients: recipient))
        }
        return mails
    }


    private Declarant getDecData(EphytoGen domainInstance){
        Declarant decData = getHtObject(domainInstance,Declarant)
        return decData
    }

    private static Date getWorkingDate(domainInstance) {
        def workingDate = LocalDate.now()
        def dateOnly = workingDate.toDate().clearTime()
        return dateOnly
    }

    def getHtObject(domainInstance,domainClass) {
        def criteriaClosure = {
            eq("code", domainInstance?.declarantCode)
            cache(false)
        }

        if (isHistorized(domainClass)) {
            criteriaClosure = withHistorizedCriteria(getWorkingDate(domainInstance), criteriaClosure)
        }

        def refObject = null

        try {
            def queryResult = domainClass.createCriteria().list() {
                criteriaClosure.delegate = delegate
                criteriaClosure()
            }

            if (queryResult?.size() > 0) {
                LOGGER.debug("Total {} instances of {} were found", queryResult.size(), domainClass.getSimpleName())
                refObject = queryResult[0]
            }
        } catch (e) {
            LOGGER.error("", e)
        }

        return refObject
    }


    private Company getCmpData(EphytoGen domainInstance){
        Company cmpData = getHtObject(domainInstance,Company)
        return cmpData
    }


    private static Date getWorkingDate() {
        def workingDate = LocalDate.now()
        def dateOnly = workingDate.toDate().clearTime()
        return dateOnly
    }

    @Override
    void setConfiguration(Config ephy) {
        mailEnabled = ephy?.grails?.mail.enabled
    }
}
