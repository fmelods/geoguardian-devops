package com.fiap.geoguardian.dto;

import java.time.LocalDateTime;

public class AlertaResponseDTO {

    private Long id;
    private Integer nivelRisco;
    private LocalDateTime dataHora;
    private String tipoAlerta;
    private String areaRisco;

    // Construtores
    public AlertaResponseDTO() {}

    public AlertaResponseDTO(Long id, Integer nivelRisco, LocalDateTime dataHora, String tipoAlerta, String areaRisco) {
        this.id = id;
        this.nivelRisco = nivelRisco;
        this.dataHora = dataHora;
        this.tipoAlerta = tipoAlerta;
        this.areaRisco = areaRisco;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(Integer nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(String areaRisco) {
        this.areaRisco = areaRisco;
    }
}
