package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.AreaDto;
import com.example.ms_ances_iot.dto.AreaSimpleDto;
import com.example.ms_ances_iot.dto.AreaWithFarmDto;
import com.example.ms_ances_iot.entity.AreaEntity;
import com.example.ms_ances_iot.mapper.AreaMapper;
import com.example.ms_ances_iot.repository.AreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaEntity> getAllAreas() {
        return areaRepository.findAll();
    }

    public AreaEntity guardarArea(AreaDto dto, MultipartFile imagen) throws IOException {
        byte[] imagenBytes = imagen.getBytes();
        AreaEntity entity = AreaMapper.toEntity(dto, imagenBytes);
        return areaRepository.save(entity);
    }

     public List<AreaWithFarmDto> getAllAreasWithFarmData() {
        List<AreaEntity> areas = areaRepository.findAll();

        return areas.stream()
            .map(area -> new AreaWithFarmDto(
                area.getName(),
                area.getFarm().getNombre(),
                area.getFarm().getProductor(),
                area.getUbicacion(),
                area.getCorrespondencia(),
                area.getImagen()
            ))
            .collect(Collectors.toList());
    }


    public List<AreaSimpleDto> getAreasByFarmId(Integer farmId) {
    List<AreaEntity> areas = areaRepository.findByFarmId(farmId);
    return areas.stream()
        .map(area -> new AreaSimpleDto(area.getId(), area.getName(), area.getImagen()))
        .collect(Collectors.toList());
}

}
