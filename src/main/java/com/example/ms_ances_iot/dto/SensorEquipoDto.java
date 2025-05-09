package com.example.ms_ances_iot.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorEquipoDto {
    private String tipo;
    private String nombre;
    private String propiedad;
    private String nombreEquipo;
    private String direccionDispositivo;
    private String ipGateway;
}
