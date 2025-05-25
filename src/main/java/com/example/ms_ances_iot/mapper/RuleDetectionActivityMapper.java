package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.ReglaExpandidaDto;
// import com.example.ms_ances_iot.dto.CondicionDto;
import com.example.ms_ances_iot.dto.RuleDetectionActivityDto;
import com.example.ms_ances_iot.entity.*;
import com.example.ms_ances_iot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RuleDetectionActivityMapper {

    private final ProcessStartedRepository processStartedRepo;
    private final ActivityRepository activityRepo;
    private final DispositivoRepository dispositivoRepo;

    public RuleDetectionActivityEntity toEntity(RuleDetectionActivityDto dto) {
        ProcessStartedEntity proceso = processStartedRepo.findById(dto.getProcesoIniciadoId())
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));

        ActivityEntity actividad = activityRepo.findById(dto.getActividadId().intValue())
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        DispositivoEntity dispositivo = dispositivoRepo.findById(dto.getDispositivoId())
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));

        RuleDetectionActivityEntity entity = RuleDetectionActivityEntity.builder()
                .tipo(dto.getTipo())
                .nombre(dto.getNombre())
                .operadorCondiciones(dto.getOperadorCondiciones())
                .propiedad(dto.getPropiedad())
                .descripcion(dto.getDescripcion())
                .procesoIniciado(proceso)
                .actividad(actividad)
                .dispositivo(dispositivo)
                .build();

        List<CondicionEntity> condiciones = dto.getCondiciones().stream()
                .map(c -> CondicionEntity.builder()
                        .nombre(c.getNombre())
                        .operador(c.getOperador())
                        .propiedad(c.getPropiedad())
                        .valor(c.getValor())
                        .descripcion(c.getDescripcion())
                        .regla(entity) // importante
                        .build())
                .toList();

        entity.setCondiciones(condiciones);
        return entity;
    }

    public List<ReglaExpandidaDto> toExpandedDtoList(List<RuleDetectionActivityEntity> reglas) {
        List<ReglaExpandidaDto> resultado = new ArrayList<>();

        for (RuleDetectionActivityEntity regla : reglas) {
                String tipo = regla.getTipo();
                String nombre = regla.getNombre();
                String actividad = regla.getActividad().getName();
                String propiedad = regla.getPropiedad();
                Long dispositivoId = regla.getDispositivo() != null ? regla.getDispositivo().getId() : null;

                  // Obtener nombre del dispositivo si es un sensor
                String nombreDispositivo = "";
                if (regla.getDispositivo() instanceof SensorEntity sensor) {
                nombreDispositivo = sensor.getNombre();
                } else {
                nombreDispositivo = "Desconocido";
                }

                for (CondicionEntity condicion : regla.getCondiciones()) {
                String operadorValor = condicion.getOperador() + condicion.getValor();
                resultado.add(ReglaExpandidaDto.builder()
                        .tipo(tipo)
                        .nombreRegla(nombre)
                        .actividad(actividad)
                        .propiedad(propiedad)
                        .operadorYValor(operadorValor)
                        .nombreDispositivo(nombreDispositivo)
                        .dispositivoId(dispositivoId)
                        .build());
                }
        }
        return resultado;
   }
}
