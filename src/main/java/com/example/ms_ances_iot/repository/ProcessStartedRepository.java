package com.example.ms_ances_iot.repository;

import com.example.ms_ances_iot.entity.ProcessStartedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessStartedRepository extends JpaRepository<ProcessStartedEntity, Long> {

    ProcessStartedEntity findByAreaIdAndEstado(Integer areaId, Boolean estado);
}
