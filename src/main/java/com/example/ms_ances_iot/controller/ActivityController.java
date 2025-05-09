package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.entity.ActivityEntity;
import com.example.ms_ances_iot.service.ActivityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/all/activity")
public class ActivityController {

    private final ActivityService activityListService;

    public ActivityController(ActivityService activityListService) {
        this.activityListService = activityListService;
    }

    @GetMapping
    public List<ActivityEntity> getActivities() {
        return activityListService.getAllActivities();
    }
}

