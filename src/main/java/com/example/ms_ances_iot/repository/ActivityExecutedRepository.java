package com.example.ms_ances_iot.repository;

// import com.example.miproyecto.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_ances_iot.entity.ActivityExecutedEntity;

public interface ActivityExecutedRepository extends JpaRepository<ActivityExecutedEntity, Long> {
}