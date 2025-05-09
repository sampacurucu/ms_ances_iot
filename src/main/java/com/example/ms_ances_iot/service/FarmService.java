package com.example.ms_ances_iot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.dto.FarmDto;
import com.example.ms_ances_iot.mapper.FarmMapper;
import com.example.ms_ances_iot.repository.FarmRepository;

@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public List<FarmDto> getAllFarms() {
        return farmRepository.findAll()
                .stream()
                .map(FarmMapper::toDto)
                .toList();
    }
}