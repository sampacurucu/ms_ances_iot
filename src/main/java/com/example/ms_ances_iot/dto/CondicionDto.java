package com.example.ms_ances_iot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CondicionDto {
    private String nombre;
    private String operador;
    private String propiedad;
    private double valor;
    private String descripcion;
}
