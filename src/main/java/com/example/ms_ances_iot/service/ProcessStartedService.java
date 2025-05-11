package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.ProcessStartedDto;
import com.example.ms_ances_iot.dto.ProcessStartedResponseDto;
import com.example.ms_ances_iot.entity.*;
import com.example.ms_ances_iot.mapper.ProcessStartedMapper;
import com.example.ms_ances_iot.mapper.ProcessStartedResponseMapper;
import com.example.ms_ances_iot.repository.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessStartedService {

    private final ProcessStartedRepository processStartedRepository;
    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;
    private final ProcessRepository processRepository;
    private final FarmerRepository farmerRepository;
    private final AreaRepository areaRepository;
    private final ProcessStartedMapper mapper;
    private final ProcessStartedResponseMapper mapperResponse;


    public void saveProcessStarted(ProcessStartedDto dto) {
        FarmEntity finca = farmRepository.findById(dto.getFincaId()).orElseThrow();
        CropEntity cultivo = cropRepository.findById(dto.getCultivoId()).orElseThrow();
        ProcessEntity proceso = processRepository.findById(dto.getProcesoId()).orElseThrow();
        FarmerEntity agricultor = farmerRepository.findById(dto.getAgricultorId()).orElseThrow();
        AreaEntity area = areaRepository.findById(dto.getAreaId()).orElseThrow();

        ProcessStartedEntity entity = mapper.toEntity(dto, finca, cultivo, proceso, agricultor, area);
        processStartedRepository.save(entity);
    }

    public List<ProcessStartedResponseDto> getAllProcessStarted() {
    return processStartedRepository.findAll()
            .stream()
            .map(mapperResponse::toDto)
            .collect(Collectors.toList());
}
}
