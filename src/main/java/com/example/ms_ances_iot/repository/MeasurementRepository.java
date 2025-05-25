package com.example.ms_ances_iot.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_ances_iot.entity.MeasurementEntity;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {
    // List<MeasurementEntity> findBySensorIdOrderByTimestampAsc(Long sensorId);
    List<MeasurementEntity> findBySensorIdAndVerificationOrderByTimestampAsc(Long sensorId, String verification);

}

