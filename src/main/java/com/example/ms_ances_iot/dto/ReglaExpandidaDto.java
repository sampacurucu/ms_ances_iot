package com.example.ms_ances_iot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglaExpandidaDto {
    private String tipo;
    private String nombreRegla;
    private String actividad;
    private String propiedad;
    private String operadorYValor;
    private String nombreDispositivo;
}
