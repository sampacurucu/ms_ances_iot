package com.example.ms_ances_iot.controller;

import org.springframework.web.bind.annotation.*;

import com.example.ms_ances_iot.entity.EquipoEntity;
import com.example.ms_ances_iot.service.EquipoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping("/all/equipos")
    public List<EquipoEntity> getEquipos() {
        return equipoService.getAllEquipos();
    }
}
