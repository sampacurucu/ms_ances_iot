package com.example.ms_ances_iot.dto;

public class AreaSimpleDto {
    private Integer id;
    private String name;
    private byte[] imagen;

    public AreaSimpleDto(Integer id, String name, byte[] imagen) {
        this.id = id;
        this.name = name;
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImagen() {
        return imagen;
    }
}