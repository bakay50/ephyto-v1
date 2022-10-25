package com.webbfontaine.ephyto.common

import com.webbfontaine.ephyto.TypeCastUtils
import com.webbfontaine.ephyto.command.SearchCommand
import com.webbfontaine.ephyto.gen.EphytoGen
import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.jasper.JasperExportFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import javax.print.PrintException
import javax.servlet.http.HttpServletResponse

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 18/04/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
//@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class PrintController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintController.class)
    private static final String EPHYTOGEN_PRINT_NAME="ephytoGen"
    private static final String EPHYTOGEN_DRAFT_PRINT_NAME="ephytoGenDraft"
    private static final String EPHYTOGEN_LIST_PRINT_NAME="ephytoGenSearchResult"


    def printService
    def conversationService
    def ephytoGenSearchService


    def printDraft() {
        EphytoGen ephytoGen = params.conversationId?conversationService.mergeEphytoGenConversationInstance():EphytoGen.get(TypeCastUtils.toLong(params.id))

        try {

            def result = printService.generateReport(EPHYTOGEN_DRAFT_PRINT_NAME,
                    printService.setEphytoGenReportParameters(ephytoGen), JasperExportFormat.PDF_FORMAT,
                    [printService.createPrintFields(ephytoGen)])
            writeToResponse(EPHYTOGEN_DRAFT_PRINT_NAME, result?.printResult, response,'pdf')
        } catch (PrintException p) {
            LOGGER.error("Error during print", p)
        }
    }
    def print() {
        EphytoGen ephytoGen = params.conversationId?conversationService.mergeEphytoGenConversationInstance():EphytoGen.get(TypeCastUtils.toLong(params.id))

        try {

            def result = printService.generateReport(EPHYTOGEN_PRINT_NAME,
                    printService.setEphytoGenReportParameters(ephytoGen), JasperExportFormat.PDF_FORMAT,
                    [printService.createPrintFields(ephytoGen)])
            writeToResponse(EPHYTOGEN_PRINT_NAME, result?.printResult, response,'pdf')
        } catch (PrintException p) {
            LOGGER.error("Error during print", p)
        }
    }

    def printEphytoGenSearchResult(SearchCommand searchCommand) {
        def searchResultModel = ephytoGenSearchService.getSearchResults(searchCommand, params)
        printSearchResult(params, searchResultModel, EPHYTOGEN_LIST_PRINT_NAME)
    }


    def printSearchResult(params, searchResultModel, String fileName) {
        def fileFormat = "${params?.exportFormat?.toUpperCase()}_FORMAT"
        try {
            def result = printService.generateReport(fileName, [:],
                    JasperExportFormat."${fileFormat}", searchResultModel.resultList)
            writeToResponse(fileName, result?.printResult, response,fileFormat)
        } catch (PrintException p) {
            LOGGER.error("Error during print", p)
        }
    }

    private def writeToResponse(String name, def printResult, HttpServletResponse response, def fileFormat) {
        def fileName = name +'-'+ TypeCastUtils.getCurrentDateTime()
        def outputStream = null
        try {

            response.contentType = printResult.fileFormat.mimeTyp
            response.setHeader 'Content-disposition', "attachment; filename=\"${fileName}.${printResult.fileFormat.extension}\""
            response.setCharacterEncoding("UTF-8")

            if(fileFormat == 'CSV_FORMAT'){ // for excel microsoft
                response.outputStream.write(0xEF)
                response.outputStream.write(0xBB)
                response.outputStream.write(0xBF)
            }
            outputStream = response.outputStream
            outputStream << printResult.content
        } catch (Exception ex) {
            throw new PrintException(ex)
        } finally {
            if (outputStream) {
                try {
                    outputStream.flush()
                    outputStream.close()
                }catch (Exception ex) {
                    //LOGGER.info('', ex)
                }
            }
        }
    }
}
