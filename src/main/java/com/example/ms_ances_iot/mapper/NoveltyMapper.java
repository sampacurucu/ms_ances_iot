
package com.example.ms_ances_iot.mapper;
import com.example.ms_ances_iot.dto.NoveltyDto;
import com.example.ms_ances_iot.entity.AreaEntity;
import com.example.ms_ances_iot.entity.NoveltyEntity;


public class NoveltyMapper {

    public static NoveltyEntity toEntity(NoveltyDto dto) {
        NoveltyEntity report = new NoveltyEntity();

        // Crear AreaEntity a partir del id recibido en el DTO
        AreaEntity area = new AreaEntity();
        area.setId(Integer.parseInt(dto.getIdProducctionArea())); //  importante: convertir de String a Integer
        report.setArea(area);

        report.setNovelty(dto.getNovelty());
        report.setExecutionTime(dto.getExecutionTime());
        report.setRecordTime(dto.getRecordTime());
        return report;
    }

}
