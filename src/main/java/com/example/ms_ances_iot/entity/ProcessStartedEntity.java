package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "process_started")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessStartedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "finca_id")
    private FarmEntity finca;

    @ManyToOne
    @JoinColumn(name = "cultivo_id")
    private CropEntity cultivo;

    @ManyToOne
    @JoinColumn(name = "proceso_id")
    private ProcessEntity proceso;

    @ManyToOne
    @JoinColumn(name = "agricultor_id")
    private FarmerEntity agricultor;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    private LocalDate fechaInicio;
    private LocalDate fechaFin; // null al iniciar
    private boolean estado;
}
