package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "farmer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String cedula;
    private String telefono;
    private String correo;
}
