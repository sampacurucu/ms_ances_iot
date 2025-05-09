package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "sensors")
public class SensorEntity extends DispositivoEntity {

    private String nombre;
    private String marca;
    private String referencia;
    private String modelo;
    private String propiedad;
    private LocalDate fechaMantenimiento;
    private double precision;
    private double frecuencia;
    private String descripcion;

    private boolean disponeBateria;
    private String nombreBateria;
    private double capacidadBateria;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "equipo_id", referencedColumnName = "id")
    private EquipoEntity equipo;
}
