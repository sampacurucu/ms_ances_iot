package com.example.ms_ances_iot.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.dto.DispositivoDto;
import com.example.ms_ances_iot.dto.DispositivoResumenDto;
import com.example.ms_ances_iot.dto.SensorEquipoDto;
// import com.example.ms_ances_iot.dto.SensorResumenDto;
import com.example.ms_ances_iot.entity.DispositivoEntity;
import com.example.ms_ances_iot.entity.ProcessStartedEntity;
import com.example.ms_ances_iot.entity.SensorEntity;
import com.example.ms_ances_iot.entity.SmartPointEntity;
import com.example.ms_ances_iot.mapper.DispositivoMapper;
import com.example.ms_ances_iot.repository.DispositivoRepository;
import com.example.ms_ances_iot.repository.ProcessStartedRepository;
import com.example.ms_ances_iot.repository.SensorRepository;
import com.example.ms_ances_iot.repository.SmartPointRepository;

@Service
public class DispositivoService {

    private final SensorRepository sensorRepository;
    private final DispositivoRepository dispositivoRepository;
    private final ProcessStartedRepository processStartedRepository;
    private final SmartPointRepository smartPointRepository;
    private final DispositivoMapper dispositivoMapper;

    public DispositivoService(SensorRepository sensorRepository, DispositivoRepository dispositivoRepository,
        ProcessStartedRepository processStartedRepository, SmartPointRepository smartPointRepository, DispositivoMapper dispositivoMapper ) {
        this.sensorRepository = sensorRepository;
        this.dispositivoRepository = dispositivoRepository;
        this.processStartedRepository = processStartedRepository;
        this.smartPointRepository = smartPointRepository;
        this.dispositivoMapper = dispositivoMapper;
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

    // public List<SensorResumenDto> obtenerResumenSensores() {
    // return sensorRepository.findAll().stream()
    //     .map(sensor -> {
    //         SensorResumenDto dto = new SensorResumenDto();
    //         dto.setId(sensor.getId());
    //         dto.setNombre(sensor.getNombre());
    //         return dto;
    //     })
    //     .collect(Collectors.toList());
    // }

     public List<DispositivoResumenDto> obtenerResumenDispositivos() {
        List<DispositivoEntity> dispositivos = dispositivoRepository.findAll();

        return dispositivos.stream().map(dispositivo -> {
            DispositivoResumenDto dto = new DispositivoResumenDto();
            dto.setId(dispositivo.getId());

            if (dispositivo instanceof SensorEntity sensor) {
                dto.setNombre(sensor.getNombre());
            } else {
                dto.setNombre(null);
            }

            return dto;
        }).toList();
    }

    public List<DispositivoResumenDto> obtenerSensoresPorProceso(Long processStartedId) {
    ProcessStartedEntity proceso = processStartedRepository.findById(processStartedId)
            .orElseThrow(() -> new RuntimeException("Proceso iniciado no encontrado"));

    Integer areaId = proceso.getArea().getId(); // ← ya es Long

    return smartPointRepository.findByAreaId(areaId).stream()
            .map(SmartPointEntity::getDispositivo)
            .filter(Objects::nonNull)
            .map(dispositivoMapper::toDto)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

}