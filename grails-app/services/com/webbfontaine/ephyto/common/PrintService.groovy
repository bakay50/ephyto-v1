package com.webbfontaine.ephyto.common


import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.constants.Statuses
import com.webbfontaine.ephyto.print.PrintException
import com.webbfontaine.ephyto.print.PrintResult
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import grails.plugins.jasper.JasperReportDef
import grails.util.Holders
import grails.validation.Validateable
import net.sf.jasperreports.engine.JRParameter
import net.sf.jasperreports.engine.util.JRLoader
import org.grails.web.util.WebUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.i18n.LocaleContextHolder
import com.webbfontaine.ephyto.print.EphytoGenPrintFields

import static com.webbfontaine.ephyto.signature.SignatureUtils.enableDigitalSignature
import org.springframework.core.io.Resource
import org.springframework.context.support.MessageSourceResourceBundle
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import grails.util.Environment
import org.apache.commons.io.FilenameUtils
import grails.plugins.jasper.JasperExportFormat



@Transactional
class PrintService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintService.class)
    private static final FRENCH_DATE_FORMAT = "dd/MM/yyyy"
    private static final ENGLISH_DATE_FORMAT = "yyyy/MM/dd"
    private static final LOGO_DIR = "/reports/images/logo.gif"
    private static final MESSAGE_BASE_NAMES = ["classpath:grails-app/i18n/messages", "file:grails-app/i18n/messages", "WEB-INF/grails-app/i18n/messages"]
    private static final MESSAGE_ENCODING_CODE = "UTF-8"

    static transactional = false
    def jasperService
    def messageSource
    GrailsApplication grailsApplication
    def application = Holders.grailsApplication
    def gwsSignatureRestService
    private static final String EPHYTOGEN_PRINT_NAME="ephytoGen"

    def generateReport(name, parameters, fileFormat, reportData = []) {
        def locale = LocaleContextHolder.locale
        setLocaleParameters(parameters, locale,name)


        def reportDef = new MyJasperReportDef(name: "${name}.jasper",
                fileFormat: fileFormat, reportData: reportData, parameters: parameters, locale: locale,folder:EphytoConstants.FOLDER
        )

        byte[] reportBytes = jasperService.generateReport(reportDef).toByteArray()

        if(enableDigitalSignature && JasperExportFormat.PDF_FORMAT.extension.toUpperCase() == fileFormat.extension.toUpperCase()){
            LOGGER.debug("Signing PDF document with name: ${EPHYTOGEN_PRINT_NAME}")
            reportBytes = gwsSignatureRestService.sign(reportBytes, "${EPHYTOGEN_PRINT_NAME}.${JasperExportFormat.PDF_FORMAT.extension}").body
        }

       def printResult = new PrintResult(name:name, fileFormat: reportDef?.fileFormat, content: reportBytes)

        return [reportDef: reportDef, printResult: printResult]
    }


    def setLocaleParameters(parameters, Locale locale,name) {
        def dateFormat = locale?.toString() == 'fr'? FRENCH_DATE_FORMAT : ENGLISH_DATE_FORMAT
        def langage = locale?.toString()
        // convert and get logo as stream
        //def logoStrm = EphytoGenUtils.resourceAsStream(LOGO_DIR)
         //*messageSource.basenames = MESSAGE_BASE_NAMES
        //*messageSource.defaultEncoding=MESSAGE_ENCODING_CODE
        //*messageSource.clearCache()
        //*def resourceBundle = new MessageSourceResourceBundle(messageSource,locale)
        //parameters.put("insuredValueInLetter",insuredValueInLetter)


        def resourceBundle = new MessageSourceResourceBundle(grailsApplication.mainContext, langage ? new Locale(langage.toString()) : LocaleContextHolder.locale) {
            @Override
            protected Object handleGetObject(String key) {
                Object out = super.handleGetObject(name + "." + key)
                if (!out) {
                    out = super.handleGetObject(key)
                    if (!out) {
                        out = super.handleGetObject(key)
                    }
                }
                return out
            }
        }

        // you put parameter like logo or so on
       parameters << [(JRParameter.REPORT_RESOURCE_BUNDLE): resourceBundle, dateFormat: dateFormat,langage:langage]
       parameters
    }

    def loadJasperReport(String resourceName) throws PrintException {
        def stream = Environment.current == Environment.DEVELOPMENT ? new FileSystemResource("${resourceName}")?.getFile()?.newInputStream() : new ClassPathResource("${resourceName}")?.getFile()?.newInputStream()
        if(stream){
            def jr = JRLoader.loadObject(stream)
            stream.close()
            if (!jr) {
                throw new PrintException("Error during loading resource for Printing")
            }
            jr
        }
    }

    EphytoGenPrintFields createPrintFields(def ephytoGenInstance) {
        if (!ephytoGenInstance) {
            LOGGER.debug("Can not create EphytoGen print fields, as instance is null")
            return null
        }

        EphytoGenPrintFields printFields = new EphytoGenPrintFields(ephytoGenInstance)

        return printFields
    }

    def setEphytoGenReportParameters(def ephytoGen) {
        Map parameters = [:]
        if (Statuses.PERMITTED_STATUSES_FOR_DRAFT_PRINT.contains(ephytoGen?.status)){
          parameters.put("draft", resourceAsStream('/reports/brouillon.jpg'))
        }
        parameters.put("isTemporary", ephytoGen.isTempProtect())
    //    def ephytoItemSubReport = loadJasperReport(ITEM_SUB_REPORT_DIR)
    //    parameters << [ephytoItemSubReport: ephytoItemSubReport]
        parameters
    }

    def resourceAsStream(String resource) {
        //return application.parentContext.getResource(resource).inputStream
        def request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        return request.getServletContext().getResourceAsStream(resource)
    }

    public static InputStream convertResourceAsStream(String resource) {
        try {
            return Environment.current == Environment.DEVELOPMENT ? new FileSystemResource(resource)?.getFile()?.newInputStream() : new ClassPathResource(resource)?.getFile()?.newInputStream()
        } catch (IOException e) {
            return null
        }
    }

    private static class MyJasperReportDef extends JasperReportDef implements Validateable {
        Resource getReport() {
            def path = getFilePath() + ".jasper"
            //src/main/groovy
            def out = Environment.current == Environment.DEVELOPMENT ? new FileSystemResource("src/main/resources/${path}") : new ClassPathResource(path)
            return out
        }

        String getDir() {
            def path = folder + File.separator + FilenameUtils.getPath(name)
            def out = Environment.current == Environment.DEVELOPMENT ? new FileSystemResource("src/main/resources/${path}") : new ClassPathResource(path)
            out.getPath()
        }

        String getResourcesDir(){
            def path = folder + File.separator + FilenameUtils.getPath(name) +"/images/"
            def out = Environment.current == Environment.DEVELOPMENT ? new FileSystemResource("src/main/resources/${path}") : new ClassPathResource(path)
            out.getPath()
        }

    }
}



