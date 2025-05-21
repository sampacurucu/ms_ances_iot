package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "condicion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CondicionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String operador;
    private String propiedad;
    private double valor;
    private String descripcion;

    // @ManyToOne
    // @JoinColumn(name = "regla_id")
    // private RuleDetectionActivityEntity regla;
    @ManyToOne
    @JoinColumn(name = "regla_deteccion_id")
    private RuleDetectionActivityEntity regla;

    @ManyToOne
    @JoinColumn(name = "regla_energia_id")
    private RuleEnergyConsumptionEntity reglaEnergetica;
}
