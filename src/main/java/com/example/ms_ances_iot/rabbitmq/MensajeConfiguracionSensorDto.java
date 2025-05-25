// package com.example.ms_ances_iot.dto;
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


// package com.example.ms_ances_iot.rabbitmq;

// import java.util.Map;

// public class MensajeConfiguracionSensorDto {

//     private String tipo;
//     private Map<String, Object> device;
//     private Map<String, Object> sensor;

//     public MensajeConfiguracionSensorDto() {}

//     public MensajeConfiguracionSensorDto(String tipo, Map<String, Object> device, Map<String, Object> sensor) {
//         this.tipo = tipo;
//         this.device = device;
//         this.sensor = sensor;
//     }

//     public String getTipo() {
//         return tipo;
//     }

//     public void setTipo(String tipo) {
//         this.tipo = tipo;
//     }

//     public Map<String, Object> getDevice() {
//         return device;
//     }

//     public void setDevice(Map<String, Object> device) {
//         this.device = device;
//     }

//     public Map<String, Object> getSensor() {
//         return sensor;
//     }

//     public void setSensor(Map<String, Object> sensor) {
//         this.sensor = sensor;
//     }

//     @Override
//     public String toString() {
//         return "MensajeConfiguracionSensor{" +
//                 "tipo='" + tipo + '\'' +
//                 ", device=" + device +
//                 ", sensor=" + sensor +
//                 '}';
//     }
// }
