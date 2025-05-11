package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "farm_crop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmCropEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private FarmEntity farm;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private CropEntity crop;
}
