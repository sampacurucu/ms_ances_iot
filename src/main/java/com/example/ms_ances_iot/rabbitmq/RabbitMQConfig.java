package com.example.ms_ances_iot.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nombre de la cola ya creada en CloudAMQP
    public static final String CONFIG_SENSOR_QUEUE = "raspberry1/configuracion/sensor";
    @Bean
    public Queue configuracionSensorQueue() {
        return new Queue(CONFIG_SENSOR_QUEUE, true);
    }


    public static final String CONFIG_REGLA_QUEUE = "raspberry1/configuracion/regla";
    @Bean
    public Queue configuracionReglaQueue() {
        return new Queue(CONFIG_REGLA_QUEUE, true);
    }

    public static final String CONFIG_CONDICION_QUEUE = "raspberry1/configuracion/condicion";
    @Bean
    public Queue configuracionCondicionQueue() {
        return new Queue(CONFIG_CONDICION_QUEUE, true);
    }

    public static final String MEDICION_QUEUE = "raspberry1/EnviarMediciones";
    @Bean
    public Queue medicionesQueue() {
        return new Queue(MEDICION_QUEUE, true);
    }

    public static final String MEDICION_QUEUE_2 = "raspberry2/EnviarMediciones";
    @Bean
    public Queue medicionesQueue2() {
        return new Queue(MEDICION_QUEUE_2, true);
    }

    public static final String MEDICION_QUEUE_3 = "raspberry3/EnviarMediciones";
    @Bean
    public Queue medicionesQueue3() {
        return new Queue(MEDICION_QUEUE_3, true);
    }

}