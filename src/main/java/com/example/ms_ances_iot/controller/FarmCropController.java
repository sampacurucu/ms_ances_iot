package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.CropSummaryDto;
import com.example.ms_ances_iot.service.FarmCropService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FarmCropController {

    private final FarmCropService farmCropService;

    @GetMapping("/crops/byFarm/{farmId}")
    public List<CropSummaryDto> getCropsByFarmId(@PathVariable Long farmId) {
        return farmCropService.getCropsByFarm(farmId);
    }
}