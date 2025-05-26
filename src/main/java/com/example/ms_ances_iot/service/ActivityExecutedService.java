package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.entity.ActivityEntity;
// import com.example.ms_ances_iot.entity.ActivityExecutedEntity;
import com.example.ms_ances_iot.entity.AgriculturalActivityExecutedEntity;
import com.example.ms_ances_iot.entity.ProcessStartedEntity;
import com.example.ms_ances_iot.mapper.ActivityExecutedMapper;
import com.example.ms_ances_iot.repository.ActivityRepository;
// import com.example.ms_ances_iot.repository.ActivityExecutedRepository;
import com.example.ms_ances_iot.repository.AgriculturalActivityExecutedRepository;
import com.example.ms_ances_iot.repository.ProcessStartedRepository;

import org.springframework.stereotype.Service;
import com.example.ms_ances_iot.dto.ActivityExecutedDto;




@Service
public class ActivityExecutedService {

    // private final ActivityExecutedRepository activityRepository;

    // public ActivityExecutedService(ActivityExecutedRepository activityRepository) {
    //     this.activityRepository = activityRepository;
    // }

    // public ActivityExecutedEntity saveActivity(ActivityExecutedDto activityDTO) {
    //     ActivityExecutedEntity activity = ActivityExecutedMapper.toEntity(activityDTO);
    //     return activityRepository.save(activity);
    // }
    private final AgriculturalActivityExecutedRepository activityRepository;
    private final ActivityRepository activityRepo;
    private final ProcessStartedRepository processStartedRepo;

    public ActivityExecutedService(AgriculturalActivityExecutedRepository activityRepository, 
        ActivityRepository activityRepo, ProcessStartedRepository processStartedRepo) {
        this.activityRepository = activityRepository;
        this.activityRepo = activityRepo;
        this.processStartedRepo = processStartedRepo;
    }

    public AgriculturalActivityExecutedEntity saveActivity(ActivityExecutedDto activityDTO) {
        
        // ActivityEntity activity = activityRepo.findById(Integer.parseInt(activityDTO.getIdActivity()));
        ActivityEntity activity = activityRepo.findById(Integer.parseInt(activityDTO.getIdActivity())).orElse(null);

        ProcessStartedEntity procesoIniciado = processStartedRepo.findByAreaIdAndEstado(Integer.parseInt(activityDTO.getIdProducctionArea()), true);

        
        AgriculturalActivityExecutedEntity activityE = ActivityExecutedMapper.toNewEntity(activityDTO);
        activityE.setId_proceso(procesoIniciado.getProceso().getId().toString());
        activityE.setId_activity(activity.getName());
        return activityRepository.save(activityE);
    }
}
