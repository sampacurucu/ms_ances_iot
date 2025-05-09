package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.entity.ActivityEntity;
import com.example.ms_ances_iot.repository.ActivityRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityListRepository;

    public ActivityService(ActivityRepository activityListRepository) {
        this.activityListRepository = activityListRepository;
    }

    public List<ActivityEntity> getAllActivities() {
        return activityListRepository.findAll();
    }
}