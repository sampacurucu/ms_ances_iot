package com.example.ms_ances_iot.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule_detection_activity")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RuleDetectionActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String nombre;
    private String operadorCondiciones;
    private String propiedad;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "proceso_iniciado_id")
    private ProcessStartedEntity procesoIniciado;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private ActivityEntity actividad;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private DispositivoEntity dispositivo;

    @OneToMany(mappedBy = "regla", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CondicionEntity> condiciones;
}
