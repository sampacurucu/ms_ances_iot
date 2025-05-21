package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.*;
import com.example.ms_ances_iot.service.RuleEnergyConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class RuleEnergyConsumptionController {

    private final RuleEnergyConsumptionService service;

    @PostMapping ("/new/reglas/energy")
    public ResponseEntity<Map<String, String>> guardarReglasEnergeticas(@RequestBody List<RuleEnergyConsumptionDto> reglas) {
        service.guardarReglasEnergeticas(reglas);
        // return ResponseEntity.ok("Reglas energéticas guardadas correctamente");
        return ResponseEntity.ok(Map.of("message", "Reglas guardadas correctamente"));
    }

    // @GetMapping("/proceso/{id}")
    @GetMapping("/reglas/energy/proceso/{id}")
    public ResponseEntity<List<ReglaEnergeticaExpandidaDto>> obtenerPorProceso(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorProceso(id));
    }
}
