package com.example.ms_ances_iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_ances_iot.entity.SensorEntity;

public interface SensorRepository extends JpaRepository<SensorEntity, Long> {
}