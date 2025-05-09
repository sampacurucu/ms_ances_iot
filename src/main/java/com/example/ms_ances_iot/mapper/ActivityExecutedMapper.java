package com.example.ms_ances_iot.mapper;


import com.example.ms_ances_iot.dto.ActivityExecutedDto;
import com.example.ms_ances_iot.entity.ActivityExecutedEntity;
import com.example.ms_ances_iot.entity.ActivityEntity;
import com.example.ms_ances_iot.entity.AreaEntity;

public class ActivityExecutedMapper {

    public static ActivityExecutedEntity toEntity(ActivityExecutedDto dto) {
        
        ActivityExecutedEntity activityExe = new ActivityExecutedEntity();

        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId(Integer.parseInt(dto.getIdActivity())); 
        activityExe.setActivity(activityEntity);

        AreaEntity area = new AreaEntity();
        area.setId(Integer.parseInt(dto.getIdProducctionArea()));
        activityExe.setArea(area);
        
        activityExe.setReasonActivity(dto.getReasonActivity());
        activityExe.setExecutionTime(dto.getExecutionTime());
        activityExe.setRecordTime(dto.getRecordTime());
        return activityExe;
    }
}
