package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer duracion;
    private String tipo;
    private String descripcion;
}