package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.QualityResultDto;
import com.example.ms_ances_iot.entity.ProcessStartedEntity;
import com.example.ms_ances_iot.entity.QualityResultEntity;
import org.springframework.stereotype.Component;

@Component
public class QualityResultMapper {

    public QualityResultEntity toEntity(QualityResultDto dto, ProcessStartedEntity proceso) {
        return QualityResultEntity.builder()
                .procesoIniciado(proceso)
                .estado(dto.getEstado())
                .cantidadProducida(dto.getCantidadProducida())
                .observaciones(dto.getObservaciones())
                .recomendaciones(dto.getRecomendaciones())
                .build();
    }
}
