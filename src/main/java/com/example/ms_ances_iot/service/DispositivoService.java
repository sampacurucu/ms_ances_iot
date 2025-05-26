package com.example.ms_ances_iot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.example.ms_ances_iot.rabbitmq.ConfiguracionSensorSender;
import com.example.ms_ances_iot.rabbitmq.MensajeConfiguracionSensorDto;
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
    private final ConfiguracionSensorSender configuracionSensorSender;

    public DispositivoService(SensorRepository sensorRepository, DispositivoRepository dispositivoRepository,
        ProcessStartedRepository processStartedRepository, 
        SmartPointRepository smartPointRepository,
        DispositivoMapper dispositivoMapper, ConfiguracionSensorSender configuracionSensorSender ) {
        this.sensorRepository = sensorRepository;
        this.dispositivoRepository = dispositivoRepository;
        this.processStartedRepository = processStartedRepository;
        this.smartPointRepository = smartPointRepository;
        this.dispositivoMapper = dispositivoMapper;
        this.configuracionSensorSender = configuracionSensorSender;
    }

    public SensorEntity guardarDispositivoComoSensor(DispositivoDto dto) {
        SensorEntity sensor = DispositivoMapper.toSensorEntity(dto);
        SensorEntity guardado = sensorRepository.save(sensor);

        // === Construir el objeto "device" ===
        Map<String, Object> device = new HashMap<>();
        device.put("idLocalDevice", guardado.getId());
        device.put("idGateway", guardado.getIpGateway());
        device.put("connectionStatus", "online"); 
        device.put("batteryLevel", 100.0);


        // === Construir el objeto "sensor" ===
        Map<String, Object> sensorMap = new HashMap<>();
        sensorMap.put("idSensor", guardado.getId());
        sensorMap.put("idDevice", guardado.getId());
        sensorMap.put("frequency", guardado.getFrecuencia());

        // === Armar el mensaje completo ===
        MensajeConfiguracionSensorDto mensaje = new MensajeConfiguracionSensorDto();
        mensaje.setTipo("config_sensor");
        mensaje.setDevice(device);
        mensaje.setSensor(sensorMap);

        // === Enviar a la cola ===
        configuracionSensorSender.enviar(mensaje);

        return guardado;
    }


    public List<SensorEquipoDto> obtenerVistaSensores() {
    return sensorRepository.findAll().stream()
            .map(DispositivoMapper::toVistaDto)
            .collect(Collectors.toList());
    }

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