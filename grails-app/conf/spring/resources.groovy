import com.webbfontaine.common.file.upload.ValidatableFileUploadBase
import com.webbfontaine.common.file.validator.FileCorrectnessValidator
import com.webbfontaine.common.file.validator.FileExtensionValidator
import com.webbfontaine.common.file.validator.FileSizeValidator
import com.webbfontaine.ephyto.sequence.KeyRepository
import com.webbfontaine.ephyto.sequence.SequenceGenerator
import com.webbfontaine.ephyto.workflow.ApplicatorWorkflow
import com.webbfontaine.ephyto.workflow.EphytoWorkflow
import com.webbfontaine.grails.plugins.conversation.store.session.SessionStore
import com.webbfontaine.grails.plugins.utils.fileupload.FileUploadBase
import com.webbfontaine.grails.plugins.utils.fileupload.FileUploadMessageSource
import grails.util.Environment
import grails.util.Holders
import org.springframework.web.client.RestTemplate

// Place your Spring DSL code here
beans = {
    ephytoWorkflow(EphytoWorkflow)
    applicatorWorkflow(ApplicatorWorkflow)
    sequenceGenerator(SequenceGenerator)
    keyRepository(KeyRepository)
    restTemplate(RestTemplate){}

    def defaultLanguage = Holders.config.defaultLanguage?: Locale.FRENCH
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = defaultLanguage
        java.util.Locale.setDefault(defaultLanguage)
    }
    Locale.setDefault(Locale.FRENCH)

    fileUploadBase(FileUploadBase) {}

    attachmentFileUploadBase(ValidatableFileUploadBase, ref("fileUploadBase")) {
        def fileUploadMessageSource = new FileUploadMessageSource()
        validators = [
                new FileSizeValidator(fileUploadMessageSource, Holders.config.attachmentMaxSizeBytes),
                new FileCorrectnessValidator(fileUploadMessageSource),
                new FileExtensionValidator(fileUploadMessageSource, Holders.config.attachmentAcceptedFormats)
        ]
    }

    xmlFileUploadBase(ValidatableFileUploadBase, ref("fileUploadBase")) {
        def fileUploadMessageSource = new FileUploadMessageSource()
        validators = [
                new FileSizeValidator(fileUploadMessageSource, Holders.config.xmlMaxSizeBytes),
                new FileCorrectnessValidator(fileUploadMessageSource),
                new FileExtensionValidator(fileUploadMessageSource, Holders.config.xmlAcceptedFormats)
        ]
    }

    objectStoreService(SessionStore) {
        objectStoreTime = 60 * 60  // in secs
    }
}
