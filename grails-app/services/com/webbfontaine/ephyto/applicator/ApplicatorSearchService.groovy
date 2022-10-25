package com.webbfontaine.ephyto.applicator

import com.webbfontaine.ephyto.constants.EphytoConstants
import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.grails.plugins.search.QueryBuilder
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.query.api.Criteria

@Transactional
@Slf4j("LOGGER")
class ApplicatorSearchService {
    def searchService
    def applicatorWorkflowService
    def springSecurityService

    def getSearchResults(searchCommand, params = null) {
        QueryBuilder queryBuilder = new QueryBuilder(searchCommand)
        def max = getMax(params)
        def totalCount
        def resultList

        try {
            totalCount = getTotalCount(queryBuilder) ?: 0
            resultList = totalCount ? getSearchResult(queryBuilder, max, params) : Collections.emptyList()

        } catch (IllegalArgumentException e) {
            LOGGER.error("Error encountered during Search", e)
            totalCount = 0
            resultList = Collections.emptyList()
        }

        def searchResultMessage = searchService.getSearchResultMessage(resultList?.size(), totalCount)

        if (totalCount) {
            resultList.each { Applicator applicator ->
                applicatorWorkflowService.initOperations(applicator)
            }
        }
        return [actionsTemplatePlugin: 'wf-workflow',
                actionsTemplate      : 'searchResultActions',
                searchCommand        : searchCommand,
                max                  : max,
                resultList           : resultList,
                totalCount           : totalCount,
                searchResultMessage  : searchResultMessage
        ]
    }

    private getSearchResult(QueryBuilder queryBuilder, max, params) {
        Criteria criteria = Applicator.createCriteria()
        def critListParameter = [offset: searchService.getSearchOffset(params)]
        if (max) {
            critListParameter.put('max', max)
        }
        def resultList = criteria.list(critListParameter) {
            queryBuilder.initListCriteria(criteria)
        }
        return resultList
    }

    private getTotalCount(QueryBuilder queryBuilder) {
        Criteria criteria = Applicator.createCriteria()
        criteria.get {
            queryBuilder.initCriteria(criteria)
            projections { count() }
        }
    }

    def getMax(params) {
        def max = null
        if (params.containsKey('max')) {
            if (params.max != null) {
                max = params.max as Long
            }
        } else {
            max = Long.parseLong(EphytoConstants.MAX_RESULTS)
        }
        return max
    }


}
