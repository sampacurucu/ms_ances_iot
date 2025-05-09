package com.example.ms_ances_iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_ances_iot.entity.ActivityEntity;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {
}