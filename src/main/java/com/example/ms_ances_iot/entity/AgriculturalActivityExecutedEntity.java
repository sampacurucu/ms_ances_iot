package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agricultural_activity_executed")
@Getter
@Setter
// @NoArgsConstructor
@AllArgsConstructor
@Builder

public class AgriculturalActivityExecutedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String id_proceso;
    private String id_activity;
    private Integer id_production_area;
    private Long id_monitoring_point;
    private Long id_activity_execution_point;
    private String id_farmer;
    private Long id_rule_detection_activities;
    private String execution_type;
    private String reason_activity;
    private Double measurement_after;
    private Double measurement_before;
    private String date_end;
    private String date_in;  
    
}
