package com.example.ms_ances_iot.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionSensorSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ConfiguracionSensorSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void enviar(MensajeConfiguracionSensorDto mensaje) {
        try {
            String json = objectMapper.writeValueAsString(mensaje);
            rabbitTemplate.convertAndSend(RabbitMQConfig.CONFIG_SENSOR_QUEUE, json);
            System.out.println("Enviado JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

