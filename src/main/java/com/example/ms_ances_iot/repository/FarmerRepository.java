package com.example.ms_ances_iot.repository;

import com.example.ms_ances_iot.entity.FarmerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<FarmerEntity, Long> {
}