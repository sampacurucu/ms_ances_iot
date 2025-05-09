package com.example.ms_ances_iot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_ances_iot.dto.ProcessActivityDto;
import com.example.ms_ances_iot.dto.ProcessWithActivitiesDto;
import com.example.ms_ances_iot.service.ProcessActivityService;

@RestController
@RequestMapping("/api")
public class ProcessActivityController {

    private final ProcessActivityService processActivityService;

    public ProcessActivityController(ProcessActivityService processActivityService) {
        this.processActivityService = processActivityService;
    }

    @PostMapping("/assign/activities")
    public ResponseEntity<Map<String, String>> assignActivities(@RequestBody ProcessActivityDto request) {
        processActivityService.assignActivitiesToProcess(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Actividades asignadas correctamente.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/processes/with/activities")
    public ResponseEntity<List<ProcessWithActivitiesDto>> getAllProcessesWithActivities() {
            List<ProcessWithActivitiesDto> result = processActivityService.getAllProcessesWithActivities();
        return ResponseEntity.ok(result);
    }
}