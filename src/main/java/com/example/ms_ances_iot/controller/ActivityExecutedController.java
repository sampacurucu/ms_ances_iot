package com.example.ms_ances_iot.controller;

import org.springframework.web.bind.annotation.*;
import com.example.ms_ances_iot.dto.ActivityExecutedDto;
import com.example.ms_ances_iot.entity.ActivityExecutedEntity;
import com.example.ms_ances_iot.service.ActivityExecutedService;
// import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/add/activity_executed")
public class ActivityExecutedController {

    private final ActivityExecutedService activityService;

    public ActivityExecutedController(ActivityExecutedService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ActivityExecutedEntity saveActivity(@RequestBody ActivityExecutedDto activityDTO) {
        return activityService.saveActivity(activityDTO);
    }
}