<bootstrap:div>
    <bootstrap:div class="export">
        <bootstrap:div class="menuContainer">
            <span class="menuButton">
                <g:link class="pdf" controller="print" action="print${domainName}SearchResult"
                        params="${params << [exportFormat: 'pdf', extension: 'pdf']}">
                    <g:message code="ephyto.pdf.print.label" default="PDF"/>
                </g:link>
            </span>
            <span class="menuButton">
                <g:link class="excel" controller="print" action="print${domainName}SearchResult"
                        params="${params << [exportFormat: 'xls', extension: 'xls']}">
                    <g:message code="ephyto.excel.print.label" default="EXCEL"/>
                </g:link>
            </span>
            <span class="menuButton">
                <g:link class="csv" controller="print" action="print${domainName}SearchResult"
                        params="${params << [exportFormat: 'csv', extension: 'csv']}">
                    <g:message code="ephyto.csv.print.label" default="CSV"/>
                </g:link>
            </span>
        </bootstrap:div>
    </bootstrap:div>
</bootstrap:div>