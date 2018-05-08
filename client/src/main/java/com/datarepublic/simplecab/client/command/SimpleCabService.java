package com.datarepublic.simplecab.client.command;

import java.util.Date;
import java.util.Map;

public interface SimpleCabService {

  void deleteCache();

  Map<String, Integer> getMedallionsSummary(Date pickupDate, String... medallions);
  
  Map<String, Integer> getMedallionsSummary(Date pickupDate, boolean ignoreCache, String... medallions);

  //  ??? getMedallionsSummary(String... medallions, Date pickupDate);

//  ??? getMedallionsSummary(String... medallions, Date pickupDate, boolean ignoreCache);
    

}
