package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.ReglaExpandidaDto;
import com.example.ms_ances_iot.dto.RuleDetectionActivityDto;
import com.example.ms_ances_iot.service.RuleDetectionActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class RuleDetectionActivityController {

    private final RuleDetectionActivityService service;

    @PostMapping ("/new/reglas")
    public ResponseEntity<Map<String, String>> guardarReglas(@RequestBody List<RuleDetectionActivityDto> reglas) {
        service.guardarReglas(reglas);
        // return ResponseEntity.ok("Reglas guardadas correctamente");
        return ResponseEntity.ok(Map.of("message", "Reglas guardadas correctamente"));

    }

    @GetMapping("/reglas/proceso/{procesoId}")
    public ResponseEntity<List<ReglaExpandidaDto>> getReglasPorProceso(@PathVariable Long procesoId) {
        List<ReglaExpandidaDto> reglas = service.obtenerReglasPorProceso(procesoId);
        return ResponseEntity.ok(reglas);
    }

}
