package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.FarmerSummaryDto;
import com.example.ms_ances_iot.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @GetMapping("/all/farmers")
    public List<FarmerSummaryDto> getAllFarmers() {
        return farmerService.getAllFarmers();
    }
}