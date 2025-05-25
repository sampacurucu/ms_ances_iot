package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "measurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementEntity {
    @Id
    private Long idMeasurement;

    // private Long idSensor;
    @ManyToOne
    @JoinColumn(name = "idSensor", referencedColumnName = "id")
    private SensorEntity sensor;
    private Double value;

    private String timestamp; // o LocalDateTime si lo prefieres
    private String verification;
}
