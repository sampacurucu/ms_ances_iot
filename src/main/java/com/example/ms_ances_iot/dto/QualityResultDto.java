package com.example.ms_ances_iot.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityResultDto {
    private Long procesoId;
    private String estado;
    private Integer cantidadProducida;
    private String observaciones;
    private String recomendaciones;
}
