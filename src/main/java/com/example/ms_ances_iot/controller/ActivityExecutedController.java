package com.example.ms_ances_iot.controller;

import org.springframework.web.bind.annotation.*;
import com.example.ms_ances_iot.dto.ActivityExecutedDto;
// import com.example.ms_ances_iot.entity.ActivityExecutedEntity;
import com.example.ms_ances_iot.entity.AgriculturalActivityExecutedEntity;
import com.example.ms_ances_iot.service.ActivityExecutedService;
// import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class ActivityExecutedController {

    private final ActivityExecutedService activityService;

    public ActivityExecutedController(ActivityExecutedService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/add/activity_executed")
    // public ActivityExecutedEntity saveActivity(@RequestBody ActivityExecutedDto activityDTO) {
    //     return activityService.saveActivity(activityDTO);
    // }
    public AgriculturalActivityExecutedEntity saveActivity(@RequestBody ActivityExecutedDto activityDTO) {
        return activityService.saveActivity(activityDTO);
    }
}