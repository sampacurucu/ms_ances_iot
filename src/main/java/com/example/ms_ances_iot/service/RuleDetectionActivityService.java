package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.ReglaExpandidaDto;
import com.example.ms_ances_iot.dto.RuleDetectionActivityDto;
import com.example.ms_ances_iot.entity.RuleDetectionActivityEntity;
import com.example.ms_ances_iot.mapper.RuleDetectionActivityMapper;
import com.example.ms_ances_iot.repository.RuleDetectionActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleDetectionActivityService {

    private final RuleDetectionActivityRepository reglaRepo;
    private final RuleDetectionActivityMapper mapper;

    public void guardarReglas(List<RuleDetectionActivityDto> reglasDto) {
        List<RuleDetectionActivityEntity> entidades = reglasDto.stream()
                .map(mapper::toEntity)
                .toList();
        reglaRepo.saveAll(entidades);
    }

    public List<ReglaExpandidaDto> obtenerReglasPorProceso(Long procesoIniciadoId) {
        List<RuleDetectionActivityEntity> reglas = reglaRepo.findByProcesoIniciadoId(procesoIniciadoId);
        return mapper.toExpandedDtoList(reglas);
    }

}
