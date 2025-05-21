package com.example.ms_ances_iot.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleDetectionActivityDto {
    private Long procesoIniciadoId;
    private String tipo;
    private String nombre;
    private Long actividadId;
    private String operadorCondiciones;
    private Long dispositivoId;
    private String propiedad;
    private String descripcion;
    private List<CondicionDto> condiciones;
}
