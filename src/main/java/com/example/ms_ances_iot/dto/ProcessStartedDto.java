package com.example.ms_ances_iot.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStartedDto {
    private Long fincaId;
    private Long cultivoId;
    private Integer procesoId;
    private Long agricultorId;
    private Integer areaId;
    private LocalDate fechaInicio;
    private boolean estado;
}
