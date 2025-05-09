package com.example.ms_ances_iot.mapper;

import java.time.LocalDate;

import com.example.ms_ances_iot.dto.DispositivoDto;
import com.example.ms_ances_iot.dto.SensorEquipoDto;
import com.example.ms_ances_iot.entity.EquipoEntity;
import com.example.ms_ances_iot.entity.SensorEntity;

public class DispositivoMapper {

    public static SensorEntity toSensorEntity(DispositivoDto dto) {
        SensorEntity sensor = new SensorEntity();

        // Campos de la clase padre
        sensor.setDireccionDispositivo(dto.getDireccionDispositivo());
        sensor.setIpGateway(dto.getIpGateway());
        sensor.setProtocolo(dto.getProtocolo());

        // Campos específicos del sensor
        sensor.setNombre(dto.getNombre());
        sensor.setMarca(dto.getMarca());
        sensor.setReferencia(dto.getReferencia());
        sensor.setModelo(dto.getModelo());
        sensor.setPropiedad(dto.getPropiedad());
        sensor.setFechaMantenimiento(LocalDate.parse(dto.getFechaMantenimiento()));
        sensor.setPrecision(dto.getPrecision());
        sensor.setFrecuencia(dto.getFrecuencia());
        sensor.setDescripcion(dto.getDescripcion());
        sensor.setDisponeBateria(dto.isDisponeBateria());
        sensor.setNombreBateria(dto.getNombreBateria());
        sensor.setCapacidadBateria(dto.getCapacidadBateria());
        sensor.setTipo(dto.getTipo());

        // Relación con Equipo
        EquipoEntity equipo = new EquipoEntity();
        equipo.setId(Integer.parseInt(dto.getEquipoId()));
        sensor.setEquipo(equipo);

        return sensor;
    }

    public static SensorEquipoDto toVistaDto(SensorEntity sensor) {
        SensorEquipoDto dto = new SensorEquipoDto();
        dto.setTipo(sensor.getTipo());
        dto.setNombre(sensor.getNombre());
        dto.setPropiedad(sensor.getPropiedad());
        dto.setNombreEquipo(sensor.getEquipo() != null ? sensor.getEquipo().getName() : null);
        dto.setDireccionDispositivo(sensor.getDireccionDispositivo());
        dto.setIpGateway(sensor.getIpGateway());
        return dto;
    }
}