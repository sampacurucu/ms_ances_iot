package com.example.ms_ances_iot.rabbitmq;

import lombok.*;

// import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeReglaEnergeticaDto {
    private Map<String, Object> rule;
}