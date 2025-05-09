package com.example.ms_ances_iot.dto;

import java.util.List;

public class ProcessActivityDto {
    private Integer processId;
    private List<Integer> activityIds;

    // Getters y setters
    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public List<Integer> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<Integer> activityIds) {
        this.activityIds = activityIds;
    }
}
