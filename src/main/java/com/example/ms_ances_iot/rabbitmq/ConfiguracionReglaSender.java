package com.example.ms_ances_iot.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionReglaSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ConfiguracionReglaSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void enviar(MensajeReglaEnergeticaDto mensaje) {
        try {
            String json = objectMapper.writeValueAsString(mensaje);
            rabbitTemplate.convertAndSend(RabbitMQConfig.CONFIG_REGLA_QUEUE, json);
            System.out.println("📤 Regla enviada: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}