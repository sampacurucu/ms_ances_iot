package com.example.ms_ances_iot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.dto.ProcessDto;
import com.example.ms_ances_iot.repository.ProcessRepository;

@Service
public class ProcessService {

    private final ProcessRepository repository;

    public ProcessService(ProcessRepository repository) {
        this.repository = repository;
    }

    public List<ProcessDto> getAllProcesses() {
        return repository.findAll()
            .stream()
            .map(p -> new ProcessDto(p.getId(), p.getName()))
            .collect(Collectors.toList());
    }
}