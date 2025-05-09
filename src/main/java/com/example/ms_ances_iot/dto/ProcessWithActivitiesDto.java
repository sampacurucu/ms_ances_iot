package com.example.ms_ances_iot.dto;

import java.util.List;

public class ProcessWithActivitiesDto {
    private String processName;
    private List<String> activityNames;

    // Getters y setters
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public List<String> getActivityNames() {
        return activityNames;
    }

    public void setActivityNames(List<String> activityNames) {
        this.activityNames = activityNames;
    }
}