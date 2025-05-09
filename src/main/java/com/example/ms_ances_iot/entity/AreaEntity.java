package com.example.ms_ances_iot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "areas")
public class AreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    // private String fincaId;
    private String ubicacion;
    private String correspondencia;

    //relacion con la finca
    
    @ManyToOne
    // @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "finca_id", referencedColumnName = "id")
    private FarmEntity farm;

    // @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    // Getters y Setters manuales

    public void setFarm(FarmEntity farm) {
        this.farm = farm;
    }

    public FarmEntity getFarm() {
        return farm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCorrespondencia() {
        return correspondencia;
    }

    public void setCorrespondencia(String correspondencia) {
        this.correspondencia = correspondencia;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

  
}
