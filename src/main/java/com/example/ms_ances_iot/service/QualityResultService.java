package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.QualityResultDto;
import com.example.ms_ances_iot.entity.ProcessStartedEntity;
import com.example.ms_ances_iot.entity.QualityResultEntity;
import com.example.ms_ances_iot.mapper.QualityResultMapper;
import com.example.ms_ances_iot.repository.ProcessStartedRepository;
import com.example.ms_ances_iot.repository.QualityResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QualityResultService {

    private final QualityResultRepository qualityResultRepository;
    private final ProcessStartedRepository processStartedRepository;
    private final QualityResultMapper mapper;

    public void saveResult(QualityResultDto dto) {
        ProcessStartedEntity proceso = processStartedRepository.findById(dto.getProcesoId())
                .orElseThrow(() -> new RuntimeException("Proceso iniciado no encontrado"));

        QualityResultEntity result = mapper.toEntity(dto, proceso);


        qualityResultRepository.save(result);
    }
}
