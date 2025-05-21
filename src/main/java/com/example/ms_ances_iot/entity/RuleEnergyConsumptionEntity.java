package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rule_energy_consumption")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleEnergyConsumptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String nombre;
    private int frecuencia;
    private String operadorCondiciones;
    private String propiedad;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "proceso_iniciado_id")
    private ProcessStartedEntity procesoIniciado;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private DispositivoEntity dispositivo;

    @OneToMany(mappedBy = "reglaEnergetica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CondicionEntity> condiciones;
}
