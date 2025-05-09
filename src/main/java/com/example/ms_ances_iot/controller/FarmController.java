package com.example.ms_ances_iot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_ances_iot.dto.FarmDto;
import com.example.ms_ances_iot.service.FarmService;

@RestController
@RequestMapping("/api")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping("/all/farms")
    public List<FarmDto> getFarms() {
        return farmService.getAllFarms();
    }
}