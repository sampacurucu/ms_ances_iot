package com.example.ms_ances_iot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "activity_executed")
public class ActivityExecutedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id autogenerado en la BD

    // private String idProducctionArea;
    @ManyToOne
    @JoinColumn(name = "id_producction_area", referencedColumnName = "id")
    private AreaEntity area;
    @ManyToOne
    @JoinColumn(name = "id_activity", referencedColumnName = "id")
    private ActivityEntity activity;
    // private String idActivity;
    private String reasonActivity;
    private String executionTime;
    private String recordTime;

    
}
