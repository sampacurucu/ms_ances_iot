package com.example.ms_ances_iot.repository;

import com.example.ms_ances_iot.entity.RuleDetectionActivityEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleDetectionActivityRepository extends JpaRepository<RuleDetectionActivityEntity, Long> {
    List<RuleDetectionActivityEntity> findByProcesoIniciadoId(Long procesoIniciadoId);
}
