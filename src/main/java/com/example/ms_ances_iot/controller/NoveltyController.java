package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.NoveltyDto;
import com.example.ms_ances_iot.entity.NoveltyEntity;
import com.example.ms_ances_iot.service.NoveltyService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NoveltyController {

    private final NoveltyService noveltyService;

    public NoveltyController(NoveltyService reportService) {
        this.noveltyService = reportService;
    }
@PostMapping("/add/novelty")
    public NoveltyEntity createNovelty(@RequestBody NoveltyDto reportDTO) {
        return noveltyService.saveNovelty(reportDTO);
    }
}
