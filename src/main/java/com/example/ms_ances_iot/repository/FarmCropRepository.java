package com.example.ms_ances_iot.repository;

import com.example.ms_ances_iot.entity.FarmCropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmCropRepository extends JpaRepository<FarmCropEntity, Long> {
    List<FarmCropEntity> findByFarm_Id(Long farmId);
}