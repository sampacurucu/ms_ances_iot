package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.SmartPointRequestDto;
import com.example.ms_ances_iot.dto.SmartPointSimpleDto;
import com.example.ms_ances_iot.service.SmartPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SmartPointController {

    private final SmartPointService smartPointService;

    public SmartPointController(SmartPointService smartPointService) {
        this.smartPointService = smartPointService;
    }

    @PostMapping("/new/puntos")
    public ResponseEntity<Map<String, String>> saveSmartPoints(@RequestBody List<SmartPointRequestDto> points) {
        smartPointService.saveAll(points);
        // return ResponseEntity.ok("Puntos guardados correctamente.");
        return ResponseEntity.ok().body(Map.of("mensaje", "Puntos guardados correctamente."));

    }

    @GetMapping("/points/byArea/{areaId}")
    public ResponseEntity<List<SmartPointSimpleDto>> getPuntosByArea(@PathVariable Integer areaId) {
        return ResponseEntity.ok(smartPointService.getSimplePointsByAreaId(areaId));
    }
}