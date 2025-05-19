package com.example.ms_ances_iot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_ances_iot.dto.DispositivoDto;
import com.example.ms_ances_iot.dto.DispositivoResumenDto;
import com.example.ms_ances_iot.dto.SensorEquipoDto;
// import com.example.ms_ances_iot.dto.SensorResumenDto;
import com.example.ms_ances_iot.entity.SensorEntity;
import com.example.ms_ances_iot.service.DispositivoService;

@RestController
@RequestMapping("/api")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @PostMapping("/new/dispositivo/sensor")
    public ResponseEntity<?> crearDispositivo(@RequestBody DispositivoDto dispositivoDto) {
        SensorEntity sensorGuardado = dispositivoService.guardarDispositivoComoSensor(dispositivoDto);
        return ResponseEntity.ok(sensorGuardado);
    }

    @GetMapping("/view/dispositivos/sensors")
public ResponseEntity<List<SensorEquipoDto>> getVistaSensores() {
    return ResponseEntity.ok(dispositivoService.obtenerVistaSensores());
    }

    // @GetMapping("/dispositivos/sensors/resumen")
    // public ResponseEntity<List<SensorResumenDto>> getResumenSensores() {
    //     return ResponseEntity.ok(dispositivoService.obtenerResumenSensores());
    // }

    @GetMapping("/dispositivos/resumen")
    public ResponseEntity<List<DispositivoResumenDto>> getResumenDispositivos() {
        return ResponseEntity.ok(dispositivoService.obtenerResumenDispositivos());
    }

    @GetMapping("/dispositivos/sensores/proceso/{id}")
    public List<DispositivoResumenDto> obtenerSensoresPorProceso(@PathVariable("id") Long procesoId) {
        return dispositivoService.obtenerSensoresPorProceso(procesoId);
    }

}