package com.example.ms_ances_iot.repository;

import com.example.ms_ances_iot.entity.RuleEnergyConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleEnergyConsumptionRepository extends JpaRepository<RuleEnergyConsumptionEntity, Long> {
    List<RuleEnergyConsumptionEntity> findByProcesoIniciadoId(Long procesoIniciadoId);
}
