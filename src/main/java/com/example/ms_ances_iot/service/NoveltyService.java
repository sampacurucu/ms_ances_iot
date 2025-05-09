package com.example.ms_ances_iot.service;


import com.example.ms_ances_iot.repository.NoveltyRepository;
import com.example.ms_ances_iot.dto.NoveltyDto;
import com.example.ms_ances_iot.entity.NoveltyEntity;
import com.example.ms_ances_iot.mapper.NoveltyMapper;

import org.springframework.stereotype.Service;
@Service
public class NoveltyService {

    private final NoveltyRepository noveltyRepository;

    public NoveltyService(NoveltyRepository noveltyRepository) {
        this.noveltyRepository = noveltyRepository;
    }

    public NoveltyEntity saveNovelty(NoveltyDto reportDTO) {
        NoveltyEntity reportEntity = NoveltyMapper.toEntity(reportDTO);
        return noveltyRepository.save(reportEntity);
    }
}
