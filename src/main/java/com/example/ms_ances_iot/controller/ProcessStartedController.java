package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.ActivitySummaryDto;
import com.example.ms_ances_iot.dto.ProcessStartedDto;
import com.example.ms_ances_iot.dto.ProcessStartedResponseDto;
import com.example.ms_ances_iot.service.ProcessStartedService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class ProcessStartedController {

    private final ProcessStartedService service;

    @PostMapping("/new/process/started")
    public ResponseEntity<Map<String, String>> startProcess(@RequestBody ProcessStartedDto dto) {
        service.saveProcessStarted(dto);
        // return ResponseEntity.ok("Proceso iniciado correctamente");
        return ResponseEntity.ok(Map.of("message", "Proceso iniciado correctamente"));
    }

    @GetMapping("/all/process/started")
    public ResponseEntity<List<ProcessStartedResponseDto>> getAllStartedProcesses() {
        return ResponseEntity.ok(service.getAllProcessStarted());
    }

    @PatchMapping("/update/process/started/{id}")
    public ResponseEntity<Map<String, String>> updateFechaFin(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String fechaFinStr = body.get("fechaFin");
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);
        service.updateFechaFin(id, fechaFin);
        return ResponseEntity.ok(Map.of("message", "Fecha de fin actualizada correctamente"));
    }

    @GetMapping("/activities/process/started/{id}")
    public List<ActivitySummaryDto> getActivitiesByProcessStarted(@PathVariable Long id) {
        return service.getActivitiesByProcessStartedId(id);
    }


}
