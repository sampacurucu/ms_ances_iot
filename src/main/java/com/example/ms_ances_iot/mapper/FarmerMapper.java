package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.FarmerSummaryDto;
import com.example.ms_ances_iot.entity.FarmerEntity;
import org.springframework.stereotype.Component;

@Component
public class FarmerMapper {

    public FarmerSummaryDto toSummaryDto(FarmerEntity farmer) {
        return new FarmerSummaryDto(farmer.getId(), farmer.getNombre());
    }
}