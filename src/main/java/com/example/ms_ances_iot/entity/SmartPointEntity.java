package com.example.ms_ances_iot.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "smart_points")
public class SmartPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String referencia;
    private boolean interior;
    private double altura;
    private String descripcion;
    private double x;
    private double y;

    // @ManyToOne
    // @JoinColumn(name = "dispositivo_id")
    // private SensorEntity dispositivo;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private DispositivoEntity dispositivo;


    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;
}