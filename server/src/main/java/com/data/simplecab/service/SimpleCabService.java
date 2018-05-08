package com.data.simplecab.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SimpleCabService {

    Integer getCountByMedallionAndPickupDatetime(String medallionId, Date pickupDate);

    void clearCache();

    Map<String, Integer> getCountByMedallionsAndPickupDatetime(List<String> medallionIds, Date pickupDate);

    Integer loadCSV(String filepath);

}
