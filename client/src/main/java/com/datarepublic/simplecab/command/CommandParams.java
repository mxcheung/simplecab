package com.datarepublic.simplecab.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "deleteCache", "ignoreCache", "inputFileName", "pickupDate", "ids" })

public class CommandParams {

    private Boolean deleteCache = false;
    private Boolean ignoreCache = false;
    private String inputFileName;
    private String pickupDate;
    private String[] medallionIds;
    
    public Boolean getDeleteCache() {
        return deleteCache;
    }
    public void setDeleteCache(Boolean deleteCache) {
        this.deleteCache = deleteCache;
    }
    public Boolean getIgnoreCache() {
        return ignoreCache;
    }
    public void setIgnoreCache(Boolean ignoreCache) {
        this.ignoreCache = ignoreCache;
    }
    public String getInputFileName() {
        return inputFileName;
    }
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }
    public String getPickupDate() {
        return pickupDate;
    }
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
    public String[] getMedallionIds() {
        return medallionIds;
    }
    public void setMedallionIds(String[] medallionIds) {
        this.medallionIds = medallionIds;
    }

    
    

}
