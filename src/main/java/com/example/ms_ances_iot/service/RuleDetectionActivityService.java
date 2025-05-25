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
                        resultados.add("✔ Actividad '" + regla.getActividad().getName() +
                                "' detectada con diferencia de " + diferencia +
                                " entre medidas " + anterior.getIdMeasurement() + " y " + actual.getIdMeasurement());

                        AgriculturalActivityExecutedEntity actividadEjecutada = AgriculturalActivityExecutedEntity.builder()
                            .id_activity(regla.getActividad().getName())
                            .id_production_area(procesoIniciado.getArea().getId())
                            .id_monitoring_point(smartPointOpt != null ? smartPointOpt.getId() : null)
                            .id_activity_execution_point(smartPointOpt != null ? smartPointOpt.getId() : null)
                            .id_farmer(procesoIniciado.getAgricultor().getNombre())
                            .id_rule_detection_activities(regla.getId())
                            .execution_type("Detectada")
                            .reason_activity("")
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


    // public List<String> evaluarReglasDeProceso(Long procesoIniciadoId) {
    //     ProcessStartedEntity procesoIniciado = processStartedRepo.findById(procesoIniciadoId)
    //         .orElseThrow(() -> new RuntimeException("Proceso iniciado no encontrado"));

    //     List<RuleDetectionActivityEntity> reglas = reglaRepo.findByProcesoIniciadoId(procesoIniciadoId);
    //     List<String> resultados = new ArrayList<>();

    //     for (RuleDetectionActivityEntity regla : reglas) {
    //         Long dispositivoId = regla.getDispositivo().getId();

    //         if (!(regla.getDispositivo() instanceof SensorEntity)) continue;

    //         List<MeasurementEntity> mediciones = measurementRepository
    //             .findBySensorIdAndVerificationOrderByTimestampAsc(dispositivoId, "0");

    //         if (mediciones.size() < 3) {
    //             resultados.add("⚠ No hay suficientes mediciones para evaluar la regla: " + regla.getNombre());
    //             continue;
    //         }

    //         for (int i = 2; i < mediciones.size(); i++) {
    //             MeasurementEntity actual = mediciones.get(i);
    //             SmartPointEntity smartPointOpt = smartPointRepository
    //                 .findByDispositivoIdAndAreaId(dispositivoId, procesoIniciado.getArea().getId());

    //             for (int j = 1; j <= 2; j++) { // compara con i-1 e i-2
    //                 MeasurementEntity anterior = mediciones.get(i - j);
    //                 double diferencia = Math.abs(actual.getValue() - anterior.getValue());

    //                 List<Boolean> evaluaciones = new ArrayList<>();

    //                 for (CondicionEntity condicion : regla.getCondiciones()) {
    //                     double umbral = condicion.getValor();
    //                     String operador = condicion.getOperador();

    //                     boolean cumple = switch (operador) {
    //                         case ">" -> diferencia > umbral;
    //                         case ">=" -> diferencia >= umbral;
    //                         case "<" -> diferencia < umbral;
    //                         case "<=" -> diferencia <= umbral;
    //                         case "==" -> diferencia == umbral;
    //                         default -> false;
    //                     };

    //                     evaluaciones.add(cumple);
    //                 }

    //                 boolean resultadoFinal;
    //                 if ("AND".equalsIgnoreCase(regla.getOperadorCondiciones())) {
    //                     resultadoFinal = evaluaciones.stream().allMatch(b -> b);
    //                 } else {
    //                     resultadoFinal = evaluaciones.stream().anyMatch(b -> b);
    //                 }

    //                 if (resultadoFinal) {
    //                     resultados.add("✔ Actividad '" + regla.getActividad().getName() +
    //                             "' detectada con diferencia de " + diferencia +
    //                             " entre medidas " + anterior.getIdMeasurement() + " y " + actual.getIdMeasurement());

    //                     // Actualizar verificación solo una vez
    //                     actual.setVerification("1");
    //                     measurementRepository.save(actual);

    //                     AgriculturalActivityExecutedEntity actividadEjecutada = AgriculturalActivityExecutedEntity.builder()
    //                         .id_activity(regla.getActividad().getName())
    //                         .id_production_area(procesoIniciado.getArea().getId())
    //                         .id_monitoring_point(smartPointOpt != null ? smartPointOpt.getId() : null)
    //                         .id_activity_execution_point(smartPointOpt != null ? smartPointOpt.getId() : null)
    //                         .id_farmer(procesoIniciado.getAgricultor().getNombre())
    //                         .id_rule_detection_activities(regla.getId())
    //                         .execution_type("Detectada")
    //                         .reason_activity("")
    //                         .measurement_before(anterior.getValue())
    //                         .measurement_after(actual.getValue())
    //                         .date_in(anterior.getTimestamp())
    //                         .date_end(actual.getTimestamp())
    //                         .build();

    //                     agriculturalActivityExecutedRepository.save(actividadEjecutada);
    //                 }
    //             }
    //         }
    //     }

    //     return resultados;
    // }


    // public List<String> evaluarReglasDeProceso(Long procesoIniciadoId) {

    //     ProcessStartedEntity procesoIniciado = processStartedRepo.findById(procesoIniciadoId)
    //     .orElseThrow(() -> new RuntimeException("Proceso iniciado no encontrado"));

    //     List<RuleDetectionActivityEntity> reglas = reglaRepo.findByProcesoIniciadoId(procesoIniciadoId);
    //     List<String> resultados = new ArrayList<>();

    //     for (RuleDetectionActivityEntity regla : reglas) {
    //         Long dispositivoId = regla.getDispositivo().getId();

    //         if (!(regla.getDispositivo() instanceof SensorEntity)) continue;

    //         // List<MeasurementEntity> mediciones = measurementRepository
    //         //         .findBySensorIdOrderByTimestampAsc(dispositivoId);
    //         List<MeasurementEntity> mediciones = measurementRepository
    //         .findBySensorIdAndVerificationOrderByTimestampAsc(dispositivoId, "0");

    //         if (mediciones.size() < 3) {
    //             resultados.add("⚠ No hay suficientes mediciones para evaluar la regla: " + regla.getNombre());
    //             continue;
    //         }

    //         boolean seDetecto = false;

    //         for (int i = 2; i < mediciones.size(); i++) {
    //             double valorActual = mediciones.get(i).getValue();
    //             double valorAnterior = mediciones.get(i - 1).getValue();
    //             double diferencia = Math.abs(valorActual - valorAnterior);

    //             List<Boolean> evaluaciones = new ArrayList<>();

    //             for (CondicionEntity condicion : regla.getCondiciones()) {
    //                 double umbral = condicion.getValor();
    //                 String operador = condicion.getOperador();

    //                 boolean cumple = switch (operador) {
    //                     case ">" -> diferencia > umbral;
    //                     case ">=" -> diferencia >= umbral;
    //                     case "<" -> diferencia < umbral;
    //                     case "<=" -> diferencia <= umbral;
    //                     case "==" -> diferencia == umbral;
    //                     default -> false;
    //                 };

    //                 evaluaciones.add(cumple);
    //             }

    //             boolean resultadoFinal;
    //             if ("AND".equalsIgnoreCase(regla.getOperadorCondiciones())) {
    //                 resultadoFinal = evaluaciones.stream().allMatch(b -> b);
    //             } else { // default OR
    //                 resultadoFinal = evaluaciones.stream().anyMatch(b -> b);
    //             }

    //             if (resultadoFinal) {
    //                 resultados.add("✔ Actividad '" + regla.getActividad().getName() +
    //                         "' detectada con diferencia de " + diferencia +
    //                         " en " + mediciones.get(i).getTimestamp());
    //                 seDetecto = true;
    //                 // MeasurementEntity medicion = mediciones.get(i);
    //                 // medicion.setVerification("1");
    //                 // measurementRepository.save(medicion);

    //                 SmartPointEntity smartPointOpt = smartPointRepository
    //                 .findByDispositivoIdAndAreaId(dispositivoId, procesoIniciado.getArea().getId());

    //                 AgriculturalActivityExecutedEntity actividadEjecutada = AgriculturalActivityExecutedEntity.builder()
    //                 .id_activity(regla.getActividad().getName())
    //                 .id_production_area(procesoIniciado.getArea().getId())
    //                 .id_monitoring_point(smartPointOpt.getId())
    //                 .id_activity_execution_point(smartPointOpt.getId())
    //                 .id_farmer(procesoIniciado.getAgricultor().getNombre())
    //                 .id_rule_detection_activities(regla.getId())
    //                 .execution_type("Detectada")
    //                 .reason_activity("")
    //                 .measurement_before(mediciones.get(i - 1).getValue())
    //                 .measurement_after(mediciones.get(i).getValue())
    //                 .date_in(mediciones.get(i-1).getTimestamp())
    //                 .date_end(mediciones.get(i).getTimestamp())

    //                 .build();
    //                 this.agriculturalActivityExecutedRepository.save(actividadEjecutada);

    //             }
    //         }

    //         if (!seDetecto) {
    //             resultados.add("✘ Actividad '" + regla.getActividad().getName() + "' NO detectada");
    //         }
    //     }

    //     return resultados;
    // }


    // public List<String> evaluarReglasDeProceso(Long procesoIniciadoId) {
    //     List<RuleDetectionActivityEntity> reglas = reglaRepo.findByProcesoIniciadoId(procesoIniciadoId);
    //     List<String> resultados = new ArrayList<>();

    //     for (RuleDetectionActivityEntity regla : reglas) {
    //         Long dispositivoId = regla.getDispositivo().getId();

    //         // Solo aplicamos si es sensor
    //         if (!(regla.getDispositivo() instanceof SensorEntity)) continue;

    //         List<MeasurementEntity> mediciones = measurementRepository
    //                 .findBySensorIdOrderByTimestampAsc(dispositivoId);

    //         if (mediciones.size() < 3) {
    //             resultados.add("⚠ No hay suficientes mediciones para evaluar la regla: " + regla.getNombre());
    //             continue;
    //         }

    //         // Tomar solo la primera condición (por ahora)
    //         CondicionEntity condicion = regla.getCondiciones().get(0);
    //         double umbral = condicion.getValor();
    //         String operador = condicion.getOperador(); // ejemplo: ">"

    //         boolean seDetectoAlMenosUnaVez = false;

    //         for (int i = 2; i < mediciones.size(); i++) {
    //             double valorActual = mediciones.get(i).getValue();
    //             double valorAnterior = mediciones.get(i - 1).getValue();
    //             double diferencia = Math.abs(valorActual - valorAnterior);

    //             boolean cumple = switch (operador) {
    //                 case ">" -> diferencia > umbral;
    //                 case ">=" -> diferencia >= umbral;
    //                 case "<" -> diferencia < umbral;
    //                 case "<=" -> diferencia <= umbral;
    //                 case "==" -> diferencia == umbral;
    //                 default -> false;
    //             };

    //             if (cumple) {
    //                 resultados.add("✔ Actividad '" + regla.getActividad().getName() +
    //                         "' detectada con diferencia de " + diferencia +
    //                         " en " + mediciones.get(i).getTimestamp());
    //                 seDetectoAlMenosUnaVez = true;
    //             }
    //         }

    //         if (!seDetectoAlMenosUnaVez) {
    //             resultados.add("✘ Actividad '" + regla.getActividad().getName() + "' NO detectada");
    //         }
    //     }

    //     return resultados;
    // }
}
