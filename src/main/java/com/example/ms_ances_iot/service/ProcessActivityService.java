package com.example.ms_ances_iot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.dto.ProcessActivityDto;
import com.example.ms_ances_iot.dto.ProcessWithActivitiesDto;
import com.example.ms_ances_iot.entity.ActivityEntity;
import com.example.ms_ances_iot.entity.ProcessActivityEntity;
import com.example.ms_ances_iot.entity.ProcessEntity;
import com.example.ms_ances_iot.repository.ActivityRepository;
import com.example.ms_ances_iot.repository.ProcessActivityRepository;
import com.example.ms_ances_iot.repository.ProcessRepository;

@Service
public class ProcessActivityService {

    private final ProcessRepository processRepository;
    private final ActivityRepository activityRepository;
    private final ProcessActivityRepository processActivityRepository;

    public ProcessActivityService(ProcessRepository processRepository,
        ActivityRepository activityRepository,
        ProcessActivityRepository processActivityRepository) {
        this.processRepository = processRepository;
        this.activityRepository = activityRepository;
        this.processActivityRepository = processActivityRepository;
    }

    public void assignActivitiesToProcess(ProcessActivityDto request) {
        ProcessEntity process = processRepository.findById(request.getProcessId())
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));

        for (Integer activityId : request.getActivityIds()) {
            ActivityEntity activity = activityRepository.findById(activityId)
                    .orElseThrow(() -> new RuntimeException("Actividad no encontrada: " + activityId));

            ProcessActivityEntity relation = new ProcessActivityEntity();
            relation.setProcess(process);
            relation.setActivity(activity);

            processActivityRepository.save(relation);
        }
    }

    public List<ProcessWithActivitiesDto> getAllProcessesWithActivities() {
    List<ProcessEntity> allProcesses = processRepository.findAll();

    List<ProcessWithActivitiesDto> result = new ArrayList<>();

    for (ProcessEntity process : allProcesses) {
        List<ProcessActivityEntity> relations = processActivityRepository.findByProcessId(process.getId());

        List<String> activityNames = relations.stream()
                .map(r -> r.getActivity().getName())
                .toList();

        ProcessWithActivitiesDto dto = new ProcessWithActivitiesDto();
        dto.setProcessName(process.getName());
        dto.setActivityNames(activityNames);

        result.add(dto);
    }

    return result;
}


    
}