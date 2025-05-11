package com.example.ms_ances_iot.service;


import com.example.ms_ances_iot.dto.FarmerSummaryDto;
import com.example.ms_ances_iot.mapper.FarmerMapper;
import com.example.ms_ances_iot.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final FarmerMapper farmerMapper;

    public List<FarmerSummaryDto> getAllFarmers() {
        return farmerRepository.findAll().stream()
                .map(farmerMapper::toSummaryDto)
                .toList();
    }
}