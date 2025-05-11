package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.CropSummaryDto;
import com.example.ms_ances_iot.entity.CropEntity;
import org.springframework.stereotype.Component;

@Component
public class CropMapper {
    public CropSummaryDto toSummaryDTO(CropEntity crop) {
        return new CropSummaryDto(crop.getId(), crop.getNombre());
    }
}
