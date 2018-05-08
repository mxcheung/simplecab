package com.datarepublic.simplecab.server.repository;

import java.util.Date;

public interface SimpleCabRepository {

    Integer getCountByMedallionAndPickupDatetime(String medallionId, Date pickupDate);

    void clearCabTripData();

    Integer loadCSV(String filepath);

    void clearCache();

}
