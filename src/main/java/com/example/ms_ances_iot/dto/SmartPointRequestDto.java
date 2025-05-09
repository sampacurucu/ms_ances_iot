package com.example.ms_ances_iot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmartPointRequestDto {
    private String nombre;
    private String referencia;
    private boolean interior;
    private double altura;
    private String descripcion;
    private String dispositivoId;
    private Integer areaId;
    private double x;
    private double y;
}