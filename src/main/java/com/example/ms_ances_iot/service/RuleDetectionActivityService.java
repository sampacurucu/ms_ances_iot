package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.ReglaExpandidaDto;
import com.example.ms_ances_iot.dto.RuleDetectionActivityDto;
import com.example.ms_ances_iot.entity.AgriculturalActivityExecutedEntity;
import com.example.ms_ances_iot.entity.CondicionEntity;
import com.example.ms_ances_iot.entity.MeasurementEntity;
import com.example.ms_ances_iot.entity.ProcessStartedEntity;
import com.example.ms_ances_iot.entity.RuleDetectionActivityEntity;
import com.example.ms_ances_iot.entity.SensorEntity;
import com.example.ms_ances_iot.entity.SmartPointEntity;
import com.example.ms_ances_iot.mapper.RuleDetectionActivityMapper;
import com.example.ms_ances_iot.repository.AgriculturalActivityExecutedRepository;
import com.example.ms_ances_iot.repository.MeasurementRepository;
import com.example.ms_ances_iot.repository.ProcessStartedRepository;
import com.example.ms_ances_iot.repository.RuleDetectionActivityRepository;
import com.example.ms_ances_iot.repository.SmartPointRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RuleDetectionActivityService {

    private final RuleDetectionActivityRepository reglaRepo;
    private final RuleDetectionActivityMapper mapper;
    private final MeasurementRepository measurementRepository;
    private final AgriculturalActivityExecutedRepository agriculturalActivityExecutedRepository;
    private final ProcessStartedRepository processStartedRepo;
    private final SmartPointRepository smartPointRepository;

    public void guardarReglas(List<RuleDetectionActivityDto> reglasDto) {
        List<RuleDetectionActivityEntity> entidades = reglasDto.stream()
                .map(mapper::toEntity)
                .toList();
        reglaRepo.saveAll(entidades);
    }

    public List<ReglaExpandidaDto> obtenerReglasPorProceso(Long procesoIniciadoId) {
        List<RuleDetectionActivityEntity> reglas = reglaRepo.findByProcesoIniciadoId(procesoIniciadoId);
        return mapper.toExpandedDtoList(reglas);
    }

    public List<String> evaluarReglasDeProceso(Long procesoIniciadoId) {
        ProcessStartedEntity procesoIniciado = processStartedRepo.findById(procesoIniciadoId)
            .orElseThrow(() -> new RuntimeException("Proceso iniciado no encontrado"));

        List<RuleDetectionActivityEntity> reglas = reglaRepo.findByProcesoIniciadoId(procesoIniciadoId);
        List<String> resultados = new ArrayList<>();

        for (RuleDetectionActivityEntity regla : reglas) {
            Long dispositivoId = regla.getDispositivo().getId();

            if (!(regla.getDispositivo() instanceof SensorEntity)) continue;

            List<MeasurementEntity> mediciones = measurementRepository
                .findBySensorIdAndVerificationOrderByTimestampAsc(dispositivoId, "0");

            if (mediciones.size() < 3) {
                resultados.add("⚠ No hay suficientes mediciones para evaluar la regla: " + regla.getNombre());
                continue;
            }

            SmartPointEntity smartPointOpt = smartPointRepository
                .findByDispositivoIdAndAreaId(dispositivoId, procesoIniciado.getArea().getId());

            // Marcar todas las mediciones como verificadas al final del análisis
            Set<MeasurementEntity> medicionesVerificadas = new HashSet<>();

            for (int i = 2; i < mediciones.size(); i++) {
                MeasurementEntity actual = mediciones.get(i);
                boolean actividadRegistrada = false;

                for (int j = 1; j <= 2; j++) { // compara con i-1 y i-2
                    MeasurementEntity anterior = mediciones.get(i - j);
                    // double diferencia = Math.abs(actual.getValue() - anterior.getValue());
                    double diferencia = actual.getValue() - anterior.getValue();

                    if (diferencia == 0.0) continue;

                    List<Boolean> evaluaciones = new ArrayList<>();

                    for (CondicionEntity condicion : regla.getCondiciones()) {
                        double umbral = condicion.getValor();
                        String operador = condicion.getOperador();

                        boolean cumple = switch (operador) {
                            case ">" -> diferencia > umbral;
                            case ">=" -> diferencia >= umbral;
                            case "<" -> diferencia < umbral;
                            case "<=" -> diferencia <= umbral;
                            case "==" -> diferencia == umbral;
                            default -> false;
                        };

                        evaluaciones.add(cumple);
                    }

                    boolean resultadoFinal = "AND".equalsIgnoreCase(regla.getOperadorCondiciones())
                        ? evaluaciones.stream().allMatch(b -> b)
                        : evaluaciones.stream().anyMatch(b -> b);

                if (resultadoFinal && !actividadRegistrada) {
                String mensaje = "Actividad: '" + regla.getActividad().getName() +
                "' detectada con diferencia de " + diferencia +
                " entre medidas " + anterior.getValue() + " y " + actual.getValue();

                resultados.add(mensaje);

                StringBuilder info = new StringBuilder();
                info.append("").append(mensaje).append("\n");
                info.append("Regla: ").append(regla.getNombre()).append("\n");

                for (CondicionEntity condicion : regla.getCondiciones()) {
                    info.append("clearCondición: ")
                        .append(condicion.getPropiedad()).append(" ")
                        .append(condicion.getOperador()).append(" ")
                        .append(condicion.getValor()).append("\n");
                }

                System.out.println(info);

                        AgriculturalActivityExecutedEntity actividadEjecutada = AgriculturalActivityExecutedEntity.builder()
                            .id_proceso_started(procesoIniciado.getId().toString())
                            .id_activity(regla.getActividad().getName())
                            .id_production_area(procesoIniciado.getArea().getId())
                            .id_monitoring_point(smartPointOpt != null ? smartPointOpt.getId() : null)
                            .id_activity_execution_point(smartPointOpt != null ? smartPointOpt.getId() : null)
                            .id_farmer(procesoIniciado.getAgricultor().getNombre())
                            .id_rule_detection_activities(regla.getId())
                            .execution_type("Detectada")
                            .reason_activity(null)
                            .measurement_before(anterior.getValue())
                            .measurement_after(actual.getValue())
                            .date_in(anterior.getTimestamp())
                            .date_end(actual.getTimestamp())
                            .build();

                        agriculturalActivityExecutedRepository.save(actividadEjecutada);
                        actividadRegistrada = true; // solo una por actual
                    }
                }

                // Marcar la medición actual como verificada siempre
                actual.setVerification("1");
                medicionesVerificadas.add(actual);
            }

            // Guardar todas las verificaciones al final (opcional: más eficiente)
            measurementRepository.saveAll(medicionesVerificadas);
        }

        return resultados;
    }

}
