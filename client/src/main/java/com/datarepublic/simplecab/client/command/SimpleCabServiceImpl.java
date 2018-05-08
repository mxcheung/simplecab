package com.datarepublic.simplecab.client.command;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class SimpleCabServiceImpl implements SimpleCabService {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final DateFormat format = new SimpleDateFormat(YYYY_MM_DD);

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCabServiceImpl.class);

    public static final String MIGRATE_BASE_CONTEXT_LOCAL = "http://localhost:8080/cab";
    public static final String DELETE_CACHE_MAPPING = "/clearCache";
    private static final String COUNT_BY_MEDALLIONS_AND_PICKUP_DATE_MAPPING = "/countByMedallionsAndPickupDate";
    private RestTemplate restTemplate;
    
    
    

    public SimpleCabServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void deleteCache() {
        LOGGER.info("deleteCache start....");
         String url = MIGRATE_BASE_CONTEXT_LOCAL + DELETE_CACHE_MAPPING;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(getHeader());

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                String.class);

        LOGGER.info("deleteCache Status {}", response.getStatusCode());

    }

    @Override
    public Map<String, Integer> getMedallionsSummary(Date pickupDate, String... medallions) {
        return getMedallionsSummary(pickupDate, false, medallions);
    }

    @Override
    public Map<String, Integer> getMedallionsSummary(Date pickupDate, boolean ignoreCache, String... medallions) {
        LOGGER.info("getMedallionsSummary pickupDate : {} , ignoreCache : {}, medallions : {} ....", pickupDate,
                ignoreCache, medallions);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(MIGRATE_BASE_CONTEXT_LOCAL + COUNT_BY_MEDALLIONS_AND_PICKUP_DATE_MAPPING)
                .queryParam("pickupDate", format.format(pickupDate));

        for (String medallionId : medallions) {
            builder.queryParam("medallionIds", medallionId);
        }
        HttpEntity<?> entity = new HttpEntity<>(getHeader());
        ParameterizedTypeReference<Map<String, Integer>> typeRef = new ParameterizedTypeReference<Map<String, Integer>>() {
        };
        ResponseEntity<Map<String, Integer>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                entity, typeRef);
        LOGGER.info("getMedallionsSummary Status {}", response.getStatusCode());
        LOGGER.info("getMedallionsSummary Body {}", response.getBody());
        return response.getBody();
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }

}
