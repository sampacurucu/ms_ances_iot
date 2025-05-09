package com.example.ms_ances_iot.service;

import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.entity.EquipoEntity;
import com.example.ms_ances_iot.repository.EquipoRepository;

import java.util.List;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<EquipoEntity> getAllEquipos() {
        return equipoRepository.findAll();
    }
}
