package com.example.ms_ances_iot.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeConfiguracionSensorDto {
    private String tipo;
    private Map<String, Object> device;
    private Map<String, Object> sensor;
}
