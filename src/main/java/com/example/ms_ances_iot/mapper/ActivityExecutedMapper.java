package com.example.ms_ances_iot.mapper;


import com.example.ms_ances_iot.dto.ActivityExecutedDto;
import com.example.ms_ances_iot.entity.ActivityExecutedEntity;
import com.example.ms_ances_iot.entity.AgriculturalActivityExecutedEntity;
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

    public static AgriculturalActivityExecutedEntity toNewEntity(ActivityExecutedDto dto) {
        
        
        return AgriculturalActivityExecutedEntity.builder()
                
                .id_production_area(Integer.parseInt(dto.getIdProducctionArea()))
                .id_monitoring_point(null)
                .id_activity_execution_point(null)
                .id_farmer("Usuario")
                .id_rule_detection_activities(null)
                .execution_type("Manual") // por ejemplo
                .reason_activity(dto.getReasonActivity())
                .measurement_before(null)
                .measurement_after(null)
                .date_in(dto.getExecutionTime())
                .date_end(null)
                .build();
    }
}
