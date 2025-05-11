package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.CropSummaryDto;
import com.example.ms_ances_iot.mapper.CropMapper;
import com.example.ms_ances_iot.repository.FarmCropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmCropService {

    private final FarmCropRepository farmCropRepository;
    private final CropMapper cropMapper;

    public List<CropSummaryDto> getCropsByFarm(Long farmId) {
        return farmCropRepository.findByFarm_Id(farmId).stream()
                .map(farmCrop -> cropMapper.toSummaryDTO(farmCrop.getCrop()))
                .toList();
    }
}