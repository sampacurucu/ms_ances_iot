package com.example.ms_ances_iot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispositivoDto {
    private String nombre;
    private String marca;
    private String referencia;
    private String modelo;
    private String propiedad;
    private String fechaMantenimiento;
    private double precision;
    private double frecuencia;
    private String descripcion;

    private String direccionDispositivo;
    private String ipGateway;
    private String protocolo;

    private boolean disponeBateria;
    private String nombreBateria;
    private double capacidadBateria;

    private String equipoId;
    private String tipo;
}