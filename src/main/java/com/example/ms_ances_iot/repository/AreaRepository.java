package com.example.ms_ances_iot.repository;
import com.example.ms_ances_iot.entity.AreaEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<AreaEntity, Integer> {

    List<AreaEntity> findByFarmId(Integer farmId);

}
