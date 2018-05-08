package com.data.simplecab.command;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutor.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final SimpleCabService simpleCabService;
    private ObjectMapper mapper = getObjectMapper();

    public CommandExecutor(SimpleCabService simpleCabService) {
        this.simpleCabService = simpleCabService;
    }

    public void execute(CommandParams params) throws JsonProcessingException {
        String paramStr = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        LOGGER.info("\nInput: {}", paramStr);
        if (params.getDeleteCache()) {
            LOGGER.info("Deleting cache ...");
            deleteCache();
        }
        if (params.getMedallionIds() != null) {
            LOGGER.info("getMedallionSummary ...");
            Map<String, Integer> result = getMedallionSummary(params);
            printResult(params, result);
        }
    }

    private Map<String, Integer> getMedallionSummary(CommandParams params) throws JsonProcessingException {
        Objects.requireNonNull(params.getPickupDate(), "Pick up date is required");
        LocalDate pickupDate = LocalDate.parse(params.getPickupDate(), formatter);
        return simpleCabService.getMedallionsSummary(java.sql.Date.valueOf(pickupDate), params.getIgnoreCache(),
                params.getMedallionIds());
    }

    private void printResult(CommandParams params, Map<String, Integer> result) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        String paramStr = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        sb.append("\nInput: " + paramStr);
        sb.append("\nOutput: ");
        for (String name : result.keySet()) {
            String key = name.toString();
            String value = result.get(name).toString();
            sb.append("\n medallionId : " + key + " count : " + value);
        }
        LOGGER.info("\n" + sb.toString());
    }

    private void deleteCache() {
        LOGGER.info("delete cache");
        simpleCabService.deleteCache();
    }

    private ObjectMapper getObjectMapper() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

}
