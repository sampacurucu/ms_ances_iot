package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quality_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "process_started_id")
    private ProcessStartedEntity procesoIniciado;

    private String estado;

    private Integer cantidadProducida;

    private String observaciones;

    private String recomendaciones;
}
