/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webbfontaine.ephyto.gen

/**
 *
 * @author sylla
 */
import com.webbfontaine.ephyto.command.SearchCommand
import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.grails.plugins.search.QueryBuilder
import grails.orm.HibernateCriteriaBuilder
import org.springframework.transaction.annotation.Transactional
import org.grails.datastore.mapping.query.api.Criteria
import org.hibernate.transform.AliasToBeanResultTransformer
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import javax.annotation.PreDestroy
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Transactional
class EphytoGenSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EphytoGenSearchService.class)
    static transactional = false

    private volatile static Collection<String> allResultFields;

    def grailsApplication

    def searchService
    def ephytoGenBpmService
    def ephytoSecurityService
    DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy")
    private final ExecutorService searchThreadPool = Executors.newFixedThreadPool(20);

    private getTotalCount(SearchCommand searchCommand, QueryBuilder queryBuilder) {
        Criteria countCriteria = EphytoGen.createCriteria()
        return countCriteria.get {
            queryBuilder.initCountCriteria(countCriteria)
            applyEphytoGenCriteria(countCriteria, searchCommand)
            applyRoleSpecificRestrictions(countCriteria)
            ephytoSecurityService.applyUserSpecificRestrictions(searchCommand, countCriteria as HibernateCriteriaBuilder)
            projections {
                countDistinct("id")
            }
        }
    }

    private getSearchResult(QueryBuilder queryBuilder, SearchCommand searchCommand, params) {
        def listCriteria = EphytoGen.createCriteria()
        def searchLimit = grailsApplication.config.searchConfig.limit
        def curTotalCount=getTotalCount(searchCommand, queryBuilder)
        def maxProp = params?.action?.contains(EphytoConstants.SEARCH_ACTION) ? searchService.searchMax : curTotalCount > searchLimit ? searchLimit : null
        def resultList = listCriteria.list(max: maxProp, offset: searchService.getSearchOffset(params)) {
            queryBuilder.initListCriteria(listCriteria)
            applyEphytoGenCriteria(listCriteria, searchCommand)
            applyRoleSpecificRestrictions(listCriteria)
            ephytoSecurityService.applyUserSpecificRestrictions(searchCommand, listCriteria as HibernateCriteriaBuilder)

            resultTransformer(new AliasToBeanResultTransformer(EphytoGen.class))
            projections {
                distinct("id")
                resultFields.each {
                    property(it, it)
                }
            }
        }

        return resultList
    }


    def getSearchResults(searchCommand, params) {

        QueryBuilder queryBuilder = new QueryBuilder(searchCommand)

        Future total = searchThreadPool.submit(
                new TotalCountResolver(SecurityContextHolder.context, searchCommand, queryBuilder)
        )

        Future results = searchThreadPool.submit(
                new ResultResolver(SecurityContextHolder.context, searchCommand, queryBuilder, params)
        )

        def totalCount
        def resultList

        try {
            totalCount = total.get()
            resultList = totalCount ? results.get() : Collections.emptyList()
        } catch (InterruptedException | ExecutionException e) {
            totalCount = 0
            resultList = Collections.emptyList()
        }


        resultList.each { EphytoGen ephytoGen ->
            ephytoGenBpmService.initOperations(ephytoGen)
        }


        def searchResultMessage = searchService.getSearchResultMessage(resultList?.size(), totalCount)

        return [
                actionsTemplate        : '/ephytoGen/search/searchResultActions',
                searchCommand          : searchCommand,
                max                    : searchService.searchMax,
                resultList             : resultList,
                totalCount             : totalCount,
                paramForUniqueOperation: true,
                searchResultMessage    : searchResultMessage]
    }


    def applyEphytoGenCriteria(Criteria criteria, SearchCommand searchCommand) {
        LOGGER.info("applyEphytoGenCriteria Methode")
        def isNull = {
            return it == null && !"".equals(it)
        }

        criteria.and {
            if (!isNull(searchCommand.userReference)) {
                if ("equals".equals(searchCommand.userReference)) {
                    criteria.eq('userReference', searchCommand.userReference)
                }
            }
            if (!isNull(searchCommand.disinfectionCertificateRef) || !isNull(searchCommand.applicatorCode) || !isNull(searchCommand.disinfectionCertificateDate) || !isNull(searchCommand.disinfectionCertificateDateTo)) {
                itemTreatments {
                    criteria.or {
                        if (!isNull(searchCommand?.applicatorCode)) {
                            criteria.eq('applicatorCode', searchCommand?.applicatorCode)
                        }
                        if (!isNull(searchCommand?.disinfectionCertificateRef)) {
                            criteria.eq('disinfectionCertificateRef', searchCommand?.disinfectionCertificateRef)
                        }
                        if (!isNull(searchCommand?.disinfectionCertificateDate) || !isNull(searchCommand?.disinfectionCertificateDateTo)) {
                            if ("equals".equals(searchCommand.op_disinfectionCertificateDate)) {
                                criteria.eq('disinfectionCertificateDate', searchCommand?.disinfectionCertificateDate)
                            } else if ("less than".equals(searchCommand.op_disinfectionCertificateDate.trim())) {
                                criteria.lt('disinfectionCertificateDate', searchCommand?.disinfectionCertificateDate)
                            } else if ("greater than".equals(searchCommand.op_disinfectionCertificateDate)) {
                                criteria.gt('disinfectionCertificateDate', searchCommand?.disinfectionCertificateDate)
                            } else if ("between".equals(searchCommand.op_disinfectionCertificateDate)) {
                                criteria.between('disinfectionCertificateDate', searchCommand?.disinfectionCertificateDate, searchCommand?.disinfectionCertificateDateTo)
                            }
                        }
                    }
                }

            }
        }
    }

    private static Collection<String> getResultFields() {
        if (!allResultFields) {
            synchronized (EphytoGenSearchService.class) {
                if (allResultFields == null) {
                    allResultFields = SearchCommand.resultFields.collect { it.getName() } + ['id']
                }
            }
        }
        return allResultFields
    }


    def applyRoleSpecificRestrictions(Criteria criteria) {
        LOGGER.info("Executing applyRoleSpecificRestrictions in EphytoGenSearchService")

//        if(EphytoSecurityServiceUtils.isGovSup() || EphytoSecurityServiceUtils.isGovOfficer()){
//            LOGGER.info("APPLYING CRITERIA FOR STORED")
//            criteria.and{
//                ne("status", "Stored")
//            }
//        }

    }

    public class TotalCountResolver implements Callable {
        SecurityContext securityContext
        QueryBuilder queryBuilder
        SearchCommand searchCommand

        TotalCountResolver(SecurityContext securityContext, SearchCommand searchCommand, QueryBuilder queryBuilder) {
            this.securityContext = securityContext
            this.searchCommand = searchCommand
            this.queryBuilder = queryBuilder
        }

        @Override
        Object call() throws Exception {
            def result = null
            try {
                SecurityContextHolder.setContext(securityContext)
                result = getTotalCount(searchCommand, queryBuilder)

            } finally {
                SecurityContextHolder.clearContext()
            }

            return result
        }

    }

    public class ResultResolver implements Callable {
        SecurityContext securityContext
        SearchCommand searchCommand
        QueryBuilder queryBuilder
        Map params

        ResultResolver(SecurityContext securityContext, SearchCommand searchCommand, QueryBuilder queryBuilder, Map params) {
            this.securityContext = securityContext
            this.searchCommand = searchCommand
            this.queryBuilder = queryBuilder
            this.params = params
        }

        @Override
        Object call() throws Exception {
            def result = null
            try {
                SecurityContextHolder.setContext(securityContext)
                result = getSearchResult(queryBuilder, searchCommand, params)
            } finally {
                SecurityContextHolder.clearContext()
            }

            return result
        }
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        searchThreadPool.shutdown()
    }
}

