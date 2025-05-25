package com.example.ms_ances_iot.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionCondicionSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ConfiguracionCondicionSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void enviar(MensajeCondicionDto condicion) {
        try {
            String json = objectMapper.writeValueAsString(condicion);
            rabbitTemplate.convertAndSend(RabbitMQConfig.CONFIG_CONDICION_QUEUE, json);
            System.out.println("✅ Condición enviada: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}