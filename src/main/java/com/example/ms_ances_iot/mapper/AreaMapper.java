package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.AreaDto;
import com.example.ms_ances_iot.entity.AreaEntity;
import com.example.ms_ances_iot.entity.FarmEntity;

public class AreaMapper {
    public static AreaEntity toEntity(AreaDto dto, byte[] imagenBytes) {
        AreaEntity entity = new AreaEntity();
        entity.setName(dto.getName());

        // entity.setFincaId(dto.getFincaId());

        FarmEntity farmEntity = new FarmEntity();
        farmEntity.setId(Integer.parseInt(dto.getFincaId()));
        entity.setFarm(farmEntity);

        entity.setUbicacion(dto.getUbicacion());
        entity.setCorrespondencia(dto.getCorrespondencia());
        entity.setImagen(imagenBytes);
        return entity;
    }
}
