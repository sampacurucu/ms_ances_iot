package com.example.ms_ances_iot.dto;

public class ProcessDto {
    private Integer id;
    private String name;

    // Constructor
    public ProcessDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}