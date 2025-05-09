package com.example.ms_ances_iot.dto;

// import lombok.Getter;
// import lombok.Setter;


// @Getter
// @Setter

public class AreaDto {
    private String name;
    private String fincaId;
    private String ubicacion;
    private String correspondencia;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFincaId() {
        return fincaId;
    }

    public void setFincaId(String fincaId) {
        this.fincaId = fincaId;
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
}
