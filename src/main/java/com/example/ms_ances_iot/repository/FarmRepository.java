package com.example.ms_ances_iot.repository;
import com.example.ms_ances_iot.entity.FarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<FarmEntity, Long> {
}
