package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.FarmDto;
import com.example.ms_ances_iot.entity.FarmEntity;

public class FarmMapper {

    public static FarmDto toDto(FarmEntity farm) {
        return new FarmDto(farm.getId(), farm.getNombre());
    }
}
