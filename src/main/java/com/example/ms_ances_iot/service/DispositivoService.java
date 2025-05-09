package com.example.ms_ances_iot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.dto.DispositivoDto;
import com.example.ms_ances_iot.dto.SensorEquipoDto;
import com.example.ms_ances_iot.dto.SensorResumenDto;
import com.example.ms_ances_iot.entity.SensorEntity;
import com.example.ms_ances_iot.mapper.DispositivoMapper;
import com.example.ms_ances_iot.repository.SensorRepository;

@Service
public class DispositivoService {

    private final SensorRepository sensorRepository;

    public DispositivoService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public SensorEntity guardarDispositivoComoSensor(DispositivoDto dto) {
        SensorEntity sensor = DispositivoMapper.toSensorEntity(dto);
        return sensorRepository.save(sensor);
    }

    public List<SensorEquipoDto> obtenerVistaSensores() {
    return sensorRepository.findAll().stream()
            .map(DispositivoMapper::toVistaDto)
            .collect(Collectors.toList());
    }

    public List<SensorResumenDto> obtenerResumenSensores() {
    return sensorRepository.findAll().stream()
        .map(sensor -> {
            SensorResumenDto dto = new SensorResumenDto();
            dto.setId(sensor.getId());
            dto.setNombre(sensor.getNombre());
            return dto;
        })
        .collect(Collectors.toList());
    }

}