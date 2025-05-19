package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.SmartPointRequestDto;
import com.example.ms_ances_iot.dto.SmartPointSimpleDto;
import com.example.ms_ances_iot.entity.*;
import com.example.ms_ances_iot.mapper.SmartPointMapper;
// import com.example.ms_ances_iot.mapper.SmartPointMapper;
import com.example.ms_ances_iot.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmartPointService {

    private final SmartPointRepository smartPointRepository;
    // private final SensorRepository sensorRepository;
    private final AreaRepository areaRepository;
    private final DispositivoRepository dispositivoRepository;

    public SmartPointService(SmartPointRepository smartPointRepository,
            // SensorRepository sensorRepository,
            AreaRepository areaRepository,DispositivoRepository dispositivoRepository) {
        this.smartPointRepository = smartPointRepository;
        // this.sensorRepository = sensorRepository;
        this.areaRepository = areaRepository;
        this.dispositivoRepository = dispositivoRepository;
    }

    public void saveAll(List<SmartPointRequestDto> points) {
        for (SmartPointRequestDto dto : points) {
            // SensorEntity sensor = sensorRepository.findById(Long.parseLong(dto.getDispositivoId()))
            //         .orElseThrow(() -> new RuntimeException("Sensor no encontrado"));

            AreaEntity area = areaRepository.findById(dto.getAreaId())
                    .orElseThrow(() -> new RuntimeException("Área no encontrada"));

            DispositivoEntity dispositivo = dispositivoRepository.findById(Long.parseLong(dto.getDispositivoId()))
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));

            SmartPointEntity point = SmartPointMapper.toEntity(dto, dispositivo, area);
            smartPointRepository.save(point);
        }
    }



    public List<SmartPointSimpleDto> getSimplePointsByAreaId(Integer areaId) {
        List<SmartPointEntity> entities = smartPointRepository.findByAreaId(areaId);
        return entities.stream()
            .map(sp -> new SmartPointSimpleDto(sp.getNombre(), sp.getX(), sp.getY()))
            .toList();
    }
}