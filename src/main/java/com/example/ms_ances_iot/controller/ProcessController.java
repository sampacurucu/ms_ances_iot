package com.example.ms_ances_iot.controller;

import org.springframework.web.bind.annotation.*;

import com.example.ms_ances_iot.dto.ProcessDto;
import com.example.ms_ances_iot.service.ProcessService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProcessController {

    private final ProcessService service;

    public ProcessController(ProcessService service) {
        this.service = service;
    }

    @GetMapping("/all/process")
    public List<ProcessDto> getProcesses() {
        return service.getAllProcesses();
    }
}