package com.example.ms_ances_iot.repository;

import com.example.ms_ances_iot.entity.SmartPointEntity;

import java.util.List;
// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartPointRepository extends JpaRepository<SmartPointEntity, Long> {

    List<SmartPointEntity> findByAreaId(Integer areaId);
    SmartPointEntity findByDispositivoIdAndAreaId(Long dispositivoId, Integer areaId);

}