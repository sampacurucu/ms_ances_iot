package com.example.ms_ances_iot.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_ances_iot.entity.ProcessActivityEntity;


public interface ProcessActivityRepository extends JpaRepository<ProcessActivityEntity, Integer> {

    List<ProcessActivityEntity> findByProcessId(Integer processId);
}
