package com.example.ms_ances_iot.rabbitmq;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeCondicionDto {
    private Long idCondition;
    private String operatorCondition;
    private double expectedValue;
    private Long idRule;
}