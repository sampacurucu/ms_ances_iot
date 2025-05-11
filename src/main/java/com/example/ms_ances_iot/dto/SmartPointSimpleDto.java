package com.example.ms_ances_iot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmartPointSimpleDto {
    private String nombre;
    private double x;
    private double y;
}