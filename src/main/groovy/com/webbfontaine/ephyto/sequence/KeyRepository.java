/*
 * Copyrights 2002-2014 Webb Fontaine
 * Developer: Sargis Harutyunyan
 * Date: 04 avr. 2014
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
package com.webbfontaine.ephyto.sequence;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("CheckStyle")
@Repository
public class KeyRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyRepository.class);

    private JdbcTemplate jdbcTemplate;

    public long findLatestTrNumber(int year) {
        Date yearStart = new Date(new LocalDateTime(year, 1, 1, 0, 0, 0, 0).toDate().getTime());
        LOGGER.info("Using request date after or equal {}", yearStart);

        Long result = jdbcTemplate.query("SELECT MAX(REQ_SEQ) FROM CERTIFICATE_GENERAL WHERE REQ_DAT >= ? and REQ_YER = ?", new Object[]{yearStart, year}, new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                long max = 0;
                if (rs.next()) {
                    max = rs.getLong(1);
                }
                return max;
            }
        });

        LOGGER.info("Latest Tr Number for year {} is {}", year, result);
        return result;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
