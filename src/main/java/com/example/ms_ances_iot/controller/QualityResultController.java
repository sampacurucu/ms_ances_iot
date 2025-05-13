package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.QualityResultDto;
import com.example.ms_ances_iot.service.QualityResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class QualityResultController {

    private final QualityResultService service;

    @PostMapping("/new/result/quality")
    public ResponseEntity<Map<String, String>> registerResult(@RequestBody QualityResultDto dto) {
        service.saveResult(dto);
        return ResponseEntity.ok(Map.of("message", "Resultado de calidad registrado correctamente"));
    }
}
