package com.example.ms_ances_iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AreaWithFarmDto {
    private String areaName;
    private String farmNombre;
    private String productor;
    private String ubicacion;
    private String correspondencia;
    private byte[] imagen;
}