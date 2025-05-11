package com.example.ms_ances_iot.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStartedResponseDto {
    private Long id;
    private String nombreProceso;
    private String nombreCultivo;
    private String nombreFinca;
    private String nombreArea;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
