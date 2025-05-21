package com.example.ms_ances_iot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglaEnergeticaExpandidaDto {
    private String tipo;
    private String nombreRegla;
    private int frecuencia;
    private String propiedad;
    private String operadorYValor;
    private String nombreDispositivo;
}
