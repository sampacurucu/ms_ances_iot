package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.ProcessStartedResponseDto;
import com.example.ms_ances_iot.entity.ProcessStartedEntity;
import org.springframework.stereotype.Component;

@Component
public class ProcessStartedResponseMapper {

    public ProcessStartedResponseDto toDto(ProcessStartedEntity entity) {
        return new ProcessStartedResponseDto(
                entity.getId(),
                entity.getProceso().getName(),
                entity.getCultivo().getNombre(),
                entity.getFinca().getNombre(),
                entity.getArea().getName(),
                entity.getFechaInicio(),
                entity.getFechaFin()
        );
    }
}
