package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "dispositivos")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DispositivoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccionDispositivo;
    private String ipGateway;
    private String protocolo;
}