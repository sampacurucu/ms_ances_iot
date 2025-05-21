package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.*;
import com.example.ms_ances_iot.entity.RuleEnergyConsumptionEntity;
import com.example.ms_ances_iot.mapper.RuleEnergyConsumptionMapper;
import com.example.ms_ances_iot.repository.RuleEnergyConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleEnergyConsumptionService {

    private final RuleEnergyConsumptionRepository repo;
    private final RuleEnergyConsumptionMapper mapper;

    public void guardarReglasEnergeticas(List<RuleEnergyConsumptionDto> reglasDto) {
        List<RuleEnergyConsumptionEntity> entidades = reglasDto.stream()
                .map(mapper::toEntity)
                .toList();
        repo.saveAll(entidades);
    }

    public List<ReglaEnergeticaExpandidaDto> obtenerPorProceso(Long procesoId) {
        List<RuleEnergyConsumptionEntity> reglas = repo.findByProcesoIniciadoId(procesoId);
        return mapper.toExpandedDtoList(reglas);
    }
}
