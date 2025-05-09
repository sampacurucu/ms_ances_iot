package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.SmartPointRequestDto;
import com.example.ms_ances_iot.entity.AreaEntity;
import com.example.ms_ances_iot.entity.SensorEntity;
import com.example.ms_ances_iot.entity.SmartPointEntity;

public class SmartPointMapper {

    public static SmartPointEntity toEntity(SmartPointRequestDto dto, SensorEntity sensor, AreaEntity area) {
        SmartPointEntity entity = new SmartPointEntity();
        entity.setNombre(dto.getNombre());
        entity.setReferencia(dto.getReferencia());
        entity.setInterior(dto.isInterior());
        entity.setAltura(dto.getAltura());
        entity.setDescripcion(dto.getDescripcion());
        entity.setX(dto.getX());
        entity.setY(dto.getY());
        entity.setDispositivo(sensor);
        entity.setArea(area);
        return entity;
    }
}