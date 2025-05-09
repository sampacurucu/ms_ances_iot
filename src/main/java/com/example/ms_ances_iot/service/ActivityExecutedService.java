package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.entity.ActivityExecutedEntity;
import com.example.ms_ances_iot.mapper.ActivityExecutedMapper;
import com.example.ms_ances_iot.repository.ActivityExecutedRepository;
import org.springframework.stereotype.Service;
import com.example.ms_ances_iot.dto.ActivityExecutedDto;




@Service
public class ActivityExecutedService {

    private final ActivityExecutedRepository activityRepository;

    public ActivityExecutedService(ActivityExecutedRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public ActivityExecutedEntity saveActivity(ActivityExecutedDto activityDTO) {
        ActivityExecutedEntity activity = ActivityExecutedMapper.toEntity(activityDTO);
        return activityRepository.save(activity);
    }
}
