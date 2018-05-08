package com.datarepublic.simplecab.server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cab")
public class SimpleCabController {

    @Autowired
    private SimpleCabService simpleCabService;

    @GetMapping("/countByMedallionAndPickupDate")
    public Integer getCountByMedallionAndPickupDatetime(@RequestParam(value = "medallionId") String medallionId,
            @RequestParam(value = "pickupDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickupDate) {
        return simpleCabService.getCountByMedallionAndPickupDatetime(medallionId, java.sql.Date.valueOf(pickupDate));
    }

    @GetMapping("/countByMedallionsAndPickupDate")
    public Map<String, Integer> getCountByMedallionsAndPickupDatetime(
            @RequestParam(value = "medallionIds") List<String> medallionIds,
            @RequestParam(value = "pickupDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickupDate) {
        return simpleCabService.getCountByMedallionsAndPickupDatetime(medallionIds, java.sql.Date.valueOf(pickupDate));
    }

    @PostMapping("/loadCSV")
    public Integer loadCsv(@RequestParam(value = "filepath") String filepath) {
        return simpleCabService.loadCSV(filepath);
    }

    @PostMapping("/clearCache")
    public void clearCache() {
        simpleCabService.clearCache();
    }

}
