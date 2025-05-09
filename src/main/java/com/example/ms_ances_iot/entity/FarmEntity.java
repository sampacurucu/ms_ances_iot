package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

@Entity
@Table(name = "farm")
// @Getter
// @Setter
public class FarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String ubicacion;
    private String areaTamano;
    private String tipoSuelo;
    private String productor;
    private String descripcion;

    //getters y setters manuales
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getAreaTamano() {
        return areaTamano;
    }

    public void setAreaTamano(String areaTamano) {
        this.areaTamano = areaTamano;
    }

    public String getTipoSuelo() {
        return tipoSuelo;
    }

    public void setTipoSuelo(String tipoSuelo) {
        this.tipoSuelo = tipoSuelo;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}