package com.example.ms_ances_iot.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActivityExecutedDto {

    private String idProducctionArea;
    private String idActivity;
    private String reasonActivity;
    private String executionTime;
    private String recordTime;

}

