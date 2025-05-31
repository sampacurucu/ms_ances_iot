package com.example.ms_ances_iot.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.entity.MeasurementEntity;
import com.example.ms_ances_iot.entity.SensorEntity;
import com.example.ms_ances_iot.rabbitmq.RabbitMQConfig;
import com.example.ms_ances_iot.repository.MeasurementRepository;
import com.example.ms_ances_iot.repository.SensorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ObjectMapper objectMapper;
    private final SensorRepository sensorRepository;

    private final AtomicInteger contadorMensajes = new AtomicInteger(0);
    private Instant inicioLote;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;
    private static final int INACTIVIDAD_MS = 3000;

    private String nombreColaActual;
    private long tiempoTotalAcumulado = 0;   

    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = {
        RabbitMQConfig.MEDICION_QUEUE,
        RabbitMQConfig.MEDICION_QUEUE_2,
        RabbitMQConfig.MEDICION_QUEUE_3
    })
    public void recibirMedicion(String mensajeJson, @Header("amqp_receivedRoutingKey") String routingKey) {
        
        try {
            Map<String, Object> data = objectMapper.readValue(mensajeJson, new TypeReference<>() {});

            Long idSensor = Long.valueOf(data.get("idSensor").toString());
            SensorEntity sensor = sensorRepository.findById(idSensor)
                    .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + idSensor));

            MeasurementEntity medicion = MeasurementEntity.builder()
                    .idMeasurement(Long.valueOf(data.get("idMeasurement").toString()))
                    .sensor(sensor)
                    // .idSensor(idSensor)
                    .value(Double.valueOf(data.get("value").toString()))
                    .timestamp(data.get("timestamp").toString())
                    .verification(data.get("verification").toString())
                    .build();

                    // System.out.println("Medición recibida → ID: " + medicion.getIdMeasurement() +
                    // ", Sensor: " + sensor.getNombre() +
                    // ", Valor: " + medicion.getValue() +
                    // ", Timestamp: " + medicion.getTimestamp());

            measurementRepository.save(medicion);
            
            if (contadorMensajes.get() == 0) {
                inicioLote = Instant.now();
                nombreColaActual = routingKey;
            }
            contadorMensajes.incrementAndGet();

            if (future != null) future.cancel(false);
            future = scheduler.schedule(() -> cerrarLote(), INACTIVIDAD_MS, TimeUnit.MILLISECONDS);


        } catch (Exception e) {
            System.err.println("Error al procesar medición: " + e.getMessage());
        }
    }

    private void cerrarLote() {
        Instant fin = Instant.now();
        int total = contadorMensajes.getAndSet(0);
        long duracion = Duration.between(inicioLote, fin).getSeconds();

        tiempoTotalAcumulado += duracion;
        System.out.println("Se consumieron " + total + " mediciones desde la cola [" + nombreColaActual + "] en " + duracion + " s.");
        System.out.println(" Tiempo total acumulado: " + tiempoTotalAcumulado + " s (" + (tiempoTotalAcumulado / 60.0) + " min)");

    }
    
}
