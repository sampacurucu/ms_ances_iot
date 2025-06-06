package com.example.ms_ances_iot.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
// import java.util.concurrent.atomic.AtomicLong;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.messaging.handler.annotation.Header;
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

    private static final int INACTIVIDAD_MS = 3000;
    // private final AtomicLong tiempoTotalGlobal = new AtomicLong(0);

    private Instant tiempoInicioReal = null;
    private volatile Instant tiempoFinReal = null;



    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.objectMapper = new ObjectMapper();
    }

    // ==== Variables por cada cola ====
    private final ColaContext[] colas = new ColaContext[] {
        new ColaContext("cola1"), new ColaContext("cola2"), new ColaContext("cola3"),
        new ColaContext("cola4"), new ColaContext("cola5"), new ColaContext("cola6")
    };

    @RabbitListener(queues = RabbitMQConfig.MEDICION_QUEUE)
    public void recibirCola1(String mensaje) { procesarMedicion(mensaje, colas[0]); }

    @RabbitListener(queues = RabbitMQConfig.MEDICION_QUEUE_2)
    public void recibirCola2(String mensaje) { procesarMedicion(mensaje, colas[1]); }

    @RabbitListener(queues = RabbitMQConfig.MEDICION_QUEUE_3)
    public void recibirCola3(String mensaje) { procesarMedicion(mensaje, colas[2]); }

    @RabbitListener(queues = RabbitMQConfig.MEDICION_QUEUE_4)
    public void recibirCola4(String mensaje) { procesarMedicion(mensaje, colas[3]); }

    @RabbitListener(queues = RabbitMQConfig.MEDICION_QUEUE_5)
    public void recibirCola5(String mensaje) { procesarMedicion(mensaje, colas[4]); }

    @RabbitListener(queues = RabbitMQConfig.MEDICION_QUEUE_6)
    public void recibirCola6(String mensaje) { procesarMedicion(mensaje, colas[5]); }

    private void procesarMedicion(String mensajeJson, ColaContext contexto) {
        try {
            Map<String, Object> data = objectMapper.readValue(mensajeJson, new TypeReference<>() {});
            Long idSensor = Long.valueOf(data.get("idSensor").toString());
            SensorEntity sensor = sensorRepository.findById(idSensor)
                .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + idSensor));

            MeasurementEntity medicion = MeasurementEntity.builder()
                .idMeasurement(Long.valueOf(data.get("idMeasurement").toString()))
                .sensor(sensor)
                .value(Double.valueOf(data.get("value").toString()))
                .timestamp(data.get("timestamp").toString())
                .verification(data.get("verification").toString())
                .build();

            measurementRepository.save(medicion);
            if (tiempoInicioReal == null) {
                synchronized (this) {
                    if (tiempoInicioReal == null) {
                        tiempoInicioReal = Instant.now();
                        System.out.println(">>> Inicio global registrado: " + tiempoInicioReal);
                    }
                }
            }

            if (contexto.contador.get() == 0) {
                contexto.inicio = Instant.now();
            }

            contexto.contador.incrementAndGet();

            if (contexto.future != null) contexto.future.cancel(false);

            contexto.future = contexto.scheduler.schedule(() -> cerrarLote(contexto), INACTIVIDAD_MS, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            System.err.println("Error al procesar medición en " + contexto.nombreCola + ": " + e.getMessage());
        }
    }

    private void cerrarLote(ColaContext contexto) {
        Instant fin = Instant.now();
        int total = contexto.contador.getAndSet(0);
        long duracion = Duration.between(contexto.inicio, fin).getSeconds();

        contexto.tiempoTotal += duracion;
        // tiempoTotalGlobal.addAndGet(duracion); 

        System.out.println("Se consumieron " + total + " mediciones desde [" + contexto.nombreCola + "] en " + duracion + " s.");
        System.out.println("Tiempo total acumulado: " + contexto.tiempoTotal + " s (" + (contexto.tiempoTotal / 60.0) + " min)");
        // System.out.println(">>> Tiempo total global acumulado: " + tiempoTotalGlobal.get() + " s (" + (tiempoTotalGlobal.get() / 60.0) + " min)\n");

        boolean todasInactivas = true;
        for (ColaContext c : colas) {
            if (c.contador.get() > 0 || (c.future != null && !c.future.isDone())) {
                todasInactivas = false;
                break;
            }
        }

        synchronized (this) {
        if (todasInactivas && tiempoFinReal == null) {
            tiempoFinReal = Instant.now();
            long duracionFinal = Duration.between(tiempoInicioReal, tiempoFinReal).getSeconds();
            System.out.println("✓ Tiempo TOTAL REAL (inicio → inactividad total): " 
                + duracionFinal + " s (" + (duracionFinal / 60.0) + " min)");
        }
    }

    }

    private static class ColaContext {
        final String nombreCola;
        final AtomicInteger contador = new AtomicInteger(0);
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> future;
        Instant inicio;
        long tiempoTotal = 0;

        ColaContext(String nombreCola) {
            this.nombreCola = nombreCola;
        }
    }
} 

// package com.example.ms_ances_iot.service;

// import java.time.Duration;
// import java.time.Instant;
// import java.util.Map;
// import java.util.concurrent.Executors;
// import java.util.concurrent.ScheduledExecutorService;
// import java.util.concurrent.ScheduledFuture;
// import java.util.concurrent.TimeUnit;
// import java.util.concurrent.atomic.AtomicInteger;

// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.messaging.handler.annotation.Header;
// import org.springframework.stereotype.Service;

// import com.example.ms_ances_iot.entity.MeasurementEntity;
// import com.example.ms_ances_iot.entity.SensorEntity;
// import com.example.ms_ances_iot.rabbitmq.RabbitMQConfig;
// import com.example.ms_ances_iot.repository.MeasurementRepository;
// import com.example.ms_ances_iot.repository.SensorRepository;
// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @Service
// public class MeasurementService {

//     private final MeasurementRepository measurementRepository;
//     private final ObjectMapper objectMapper;
//     private final SensorRepository sensorRepository;

//     private final AtomicInteger contadorMensajes = new AtomicInteger(0);
//     private Instant inicioLote;
//     private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//     private ScheduledFuture<?> future;
//     private static final int INACTIVIDAD_MS = 3000;

//     private String nombreColaActual;
//     private long tiempoTotalAcumulado = 0;   

//     public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
//         this.measurementRepository = measurementRepository;
//         this.sensorRepository = sensorRepository;
//         this.objectMapper = new ObjectMapper();
//     }

//     @RabbitListener(queues = {
//         RabbitMQConfig.MEDICION_QUEUE,
//         RabbitMQConfig.MEDICION_QUEUE_2
//         // RabbitMQConfig.MEDICION_QUEUE_3,
//         // RabbitMQConfig.MEDICION_QUEUE_4,
//         // RabbitMQConfig.MEDICION_QUEUE_5,
//         // RabbitMQConfig.MEDICION_QUEUE_6
//     })
//     public void recibirMedicion(String mensajeJson, @Header("amqp_receivedRoutingKey") String routingKey) {
        
//         try {
//             Map<String, Object> data = objectMapper.readValue(mensajeJson, new TypeReference<>() {});

//             Long idSensor = Long.valueOf(data.get("idSensor").toString());
//             SensorEntity sensor = sensorRepository.findById(idSensor)
//                     .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + idSensor));

//             MeasurementEntity medicion = MeasurementEntity.builder()
//                     .idMeasurement(Long.valueOf(data.get("idMeasurement").toString()))
//                     .sensor(sensor)
//                     // .idSensor(idSensor)
//                     .value(Double.valueOf(data.get("value").toString()))
//                     .timestamp(data.get("timestamp").toString())
//                     .verification(data.get("verification").toString())
//                     .build();

//                     // System.out.println("Medición recibida → ID: " + medicion.getIdMeasurement() +
//                     // ", Sensor: " + sensor.getNombre() +
//                     // ", Valor: " + medicion.getValue() +
//                     // ", Timestamp: " + medicion.getTimestamp());

//             measurementRepository.save(medicion);
            
//             if (contadorMensajes.get() == 0) {
//                 inicioLote = Instant.now();
//                 nombreColaActual = routingKey;
//             }
//             contadorMensajes.incrementAndGet();

//             if (future != null) future.cancel(false);
//             future = scheduler.schedule(() -> cerrarLote(), INACTIVIDAD_MS, TimeUnit.MILLISECONDS);


//         } catch (Exception e) {
//             System.err.println("Error al procesar medición: " + e.getMessage());
//         }
//     }

//     private void cerrarLote() {
//         Instant fin = Instant.now();
//         int total = contadorMensajes.getAndSet(0);
//         long duracion = Duration.between(inicioLote, fin).getSeconds();

//         tiempoTotalAcumulado += duracion;
//         System.out.println("Se consumieron " + total + " mediciones desde la cola [" + nombreColaActual + "] en " + duracion + " s.");
//         System.out.println(" Tiempo total acumulado: " + tiempoTotalAcumulado + " s (" + (tiempoTotalAcumulado / 60.0) + " min)");

//     }
    
// }
