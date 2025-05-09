package com.example.ms_ances_iot.dto;

// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @AllArgsConstructor
public class FarmDto {
    private Integer id;
    private String nombre;

    public FarmDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}