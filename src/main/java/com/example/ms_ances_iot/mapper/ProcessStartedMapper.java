package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.ProcessStartedDto;
import com.example.ms_ances_iot.entity.*;
import org.springframework.stereotype.Component;

@Component
public class ProcessStartedMapper {

    public ProcessStartedEntity toEntity(ProcessStartedDto dto,
        FarmEntity finca,
        CropEntity cultivo,
        ProcessEntity proceso,
        FarmerEntity agricultor,
        AreaEntity area) {
        return ProcessStartedEntity.builder()
                .finca(finca)
                .cultivo(cultivo)
                .proceso(proceso)
                .agricultor(agricultor)
                .area(area)
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(null) // Se inicia vacío
                .estado(dto.isEstado())
                .build();
    }
}
