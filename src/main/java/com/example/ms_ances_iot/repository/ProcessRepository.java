package com.example.ms_ances_iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_ances_iot.entity.ProcessEntity;

public interface ProcessRepository extends JpaRepository<ProcessEntity, Integer> {
}