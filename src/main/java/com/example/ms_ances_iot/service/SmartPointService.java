package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.SmartPointRequestDto;
import com.example.ms_ances_iot.entity.*;
import com.example.ms_ances_iot.mapper.SmartPointMapper;
import com.example.ms_ances_iot.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmartPointService {

    private final SmartPointRepository smartPointRepository;
    private final SensorRepository sensorRepository;
    private final AreaRepository areaRepository;

    public SmartPointService(SmartPointRepository smartPointRepository,
                             SensorRepository sensorRepository,
                             AreaRepository areaRepository) {
        this.smartPointRepository = smartPointRepository;
        this.sensorRepository = sensorRepository;
        this.areaRepository = areaRepository;
    }

    public void saveAll(List<SmartPointRequestDto> points) {
        for (SmartPointRequestDto dto : points) {
            SensorEntity sensor = sensorRepository.findById(Long.parseLong(dto.getDispositivoId()))
                    .orElseThrow(() -> new RuntimeException("Sensor no encontrado"));

            AreaEntity area = areaRepository.findById(dto.getAreaId())
                    .orElseThrow(() -> new RuntimeException("Área no encontrada"));

            SmartPointEntity point = SmartPointMapper.toEntity(dto, sensor, area);
            smartPointRepository.save(point);
        }
    }
}