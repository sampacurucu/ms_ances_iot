package com.example.ms_ances_iot.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "novelty")
@Getter
@Setter
public class NoveltyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private String idProducctionArea;
    @ManyToOne
    @JoinColumn(name = "id_producction_area", referencedColumnName = "id")
    private AreaEntity area;
    private String novelty;
    private String executionTime;
    private String recordTime;
}
