package com.example.ms_ances_iot.mapper;

import com.example.ms_ances_iot.dto.*;
import com.example.ms_ances_iot.entity.*;
import com.example.ms_ances_iot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RuleEnergyConsumptionMapper {

    private final ProcessStartedRepository processStartedRepo;
    private final DispositivoRepository dispositivoRepo;

    public RuleEnergyConsumptionEntity toEntity(RuleEnergyConsumptionDto dto) {
        ProcessStartedEntity proceso = processStartedRepo.findById(dto.getProcesoIniciadoId())
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));

        DispositivoEntity dispositivo = dispositivoRepo.findById(dto.getDispositivoId())
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));

        RuleEnergyConsumptionEntity entity = RuleEnergyConsumptionEntity.builder()
                .tipo(dto.getTipo())
                .nombre(dto.getNombre())
                .frecuencia(dto.getFrecuencia())
                .operadorCondiciones(dto.getOperadorCondiciones())
                .propiedad(dto.getPropiedad())
                .descripcion(dto.getDescripcion())
                .procesoIniciado(proceso)
                .dispositivo(dispositivo)
                .build();

        List<CondicionEntity> condiciones = dto.getCondiciones().stream()
                .map(c -> CondicionEntity.builder()
                        .nombre(c.getNombre())
                        .operador(c.getOperador())
                        .propiedad(c.getPropiedad())
                        .valor(c.getValor())
                        .descripcion(c.getDescripcion())
                        .reglaEnergetica(entity)
                        .build())
                .toList();

        entity.setCondiciones(condiciones);
        return entity;
    }

    public List<ReglaEnergeticaExpandidaDto> toExpandedDtoList(List<RuleEnergyConsumptionEntity> reglas) {
        List<ReglaEnergeticaExpandidaDto> resultado = new ArrayList<>();

        for (RuleEnergyConsumptionEntity regla : reglas) {
            String tipo = regla.getTipo();
            String nombre = regla.getNombre();
            int frecuencia = regla.getFrecuencia();
            String propiedad = regla.getPropiedad();

            String nombreDispositivo = (regla.getDispositivo() instanceof SensorEntity sensor)
                    ? sensor.getNombre() : "Desconocido";

            for (CondicionEntity condicion : regla.getCondiciones()) {
                String operadorValor = condicion.getOperador() + condicion.getValor();
                resultado.add(ReglaEnergeticaExpandidaDto.builder()
                        .tipo(tipo)
                        .nombreRegla(nombre)
                        .frecuencia(frecuencia)
                        .propiedad(propiedad)
                        .operadorYValor(operadorValor)
                        .nombreDispositivo(nombreDispositivo)
                        .build());
            }
        }

        return resultado;
    }
}
