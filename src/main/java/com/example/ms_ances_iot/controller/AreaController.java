package com.example.ms_ances_iot.controller;

import com.example.ms_ances_iot.dto.AreaDto;
import com.example.ms_ances_iot.dto.AreaSimpleDto;
import com.example.ms_ances_iot.dto.AreaWithFarmDto;
import com.example.ms_ances_iot.entity.AreaEntity;
import com.example.ms_ances_iot.service.AreaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping ("/all/areas")
    public List<AreaEntity> getAreas() {
        return areaService.getAllAreas();
    }

    // @PostMapping ("/new/area")
    @PostMapping(value = "/new/area", consumes = {"multipart/form-data"})
    public ResponseEntity<AreaEntity> guardarArea(
        @RequestPart("area") AreaDto areaDto,
        @RequestPart("imagen") MultipartFile imagen) {
        try {
           
            AreaEntity saved = areaService.guardarArea(areaDto, imagen);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all/areasandfarms")
    public List<AreaWithFarmDto> getAreasWithFarmData() {
        return areaService.getAllAreasWithFarmData();
    }

    @GetMapping("/areas/byfarm/{farmId}")
    public ResponseEntity<List<AreaSimpleDto>> getAreasByFarmId(@PathVariable Integer farmId) {
        List<AreaSimpleDto> areas = areaService.getAreasByFarmId(farmId);
        return ResponseEntity.ok(areas);
}

}

