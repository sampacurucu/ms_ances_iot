package com.example.ms_ances_iot.repository;

import com.example.ms_ances_iot.entity.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<CropEntity, Long> {
}
