package com.example.ms_ances_iot.service;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.ms_ances_iot.entity.MeasurementEntity;
import com.example.ms_ances_iot.entity.SensorEntity;
import com.example.ms_ances_iot.rabbitmq.RabbitMQConfig;
import com.example.ms_ances_iot.repository.MeasurementRepository;
import com.example.ms_ances_iot.repository.SensorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// import aj.org.objectweb.asm.TypeReference;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ObjectMapper objectMapper;
    private final SensorRepository sensorRepository;

    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
         // Inicializar ObjectMapper
         // Esto es necesario para convertir JSON a objetos Java
         // y viceversa.
        this.objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = RabbitMQConfig.MEDICION_QUEUE)
    public void recibirMedicion(String mensajeJson) {
        try {
            // Convertir JSON recibido a un Map
            Map<String, Object> data = objectMapper.readValue(mensajeJson, new TypeReference<>() {});

            Long idSensor = Long.valueOf(data.get("idSensor").toString());
            SensorEntity sensor = sensorRepository.findById(idSensor)
                    .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + idSensor));

            MeasurementEntity medicion = MeasurementEntity.builder()
                    .idMeasurement(Long.valueOf(data.get("idMeasurement").toString()))
                    // .idSensor(Long.valueOf(data.get("idSensor").toString()))
                    .sensor(sensor)
                    .value(Double.valueOf(data.get("value").toString()))
                    .timestamp(data.get("timestamp").toString())
                    .verification(data.get("verification").toString())
                    .build();

            measurementRepository.save(medicion);
            System.out.println("✅ Medición recibida y guardada: " + medicion);

        } catch (Exception e) {
            System.err.println("❌ Error al procesar medición: " + e.getMessage());
        }
    }
    
}
