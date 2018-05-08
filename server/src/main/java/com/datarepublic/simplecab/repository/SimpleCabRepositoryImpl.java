package com.datarepublic.simplecab.repository;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleCabRepositoryImpl implements SimpleCabRepository {

    private static final String TRUNCATE_TABLE_CAB_TRIP_DATA = "truncate table cab_trip_data";

    private static final String COUNT_BY_MEDALLION_AND_PICKUP_DATE = "SELECT count(*) FROM cab_trip_data WHERE medallion = ? and date(pickup_datetime) = ? ";

    private static final Logger log = LoggerFactory.getLogger(SimpleCabRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    
    @Autowired
    public SimpleCabRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Cacheable("cabTrips")
    public Integer getCountByMedallionAndPickupDatetime(String medallionId, Date pickupDate) {
        log.info("getCountByMedallionAndPickupDatetime medallionId : {} pickupDate : {} ", medallionId, pickupDate);
        return jdbcTemplate.queryForObject(COUNT_BY_MEDALLION_AND_PICKUP_DATE, new Object[] { medallionId, pickupDate },
                Integer.class);
    }

    
    @Override
    public void clearCabTripData() {
        log.info("truncate table cab_trip_data ");
        jdbcTemplate.execute(TRUNCATE_TABLE_CAB_TRIP_DATA);
    }

    
    
    @Override
    public Integer loadCSV(String filepath) {
        log.info("loadCSV filepath : {}  ", filepath);
        String tableName = "cab_trip_data";
        String INFILE_COLUMN_SEPARATION_CHAR = ",";
        String sql = "LOAD DATA LOCAL INFILE '" + filepath + "' into table " + tableName
                + " COLUMNS TERMINATED BY '" + INFILE_COLUMN_SEPARATION_CHAR + "' ENCLOSED BY '\"'";
        int  result = jdbcTemplate.update(sql);
        return result;
    }

    
    
    @Override
    @CacheEvict(value = "cabTrips", allEntries = true)
    public void clearCache() {
        // Intentionally blank
    }


}
