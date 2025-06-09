package com.fiap.geoguardian.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SensorRequestDTO {

    @NotBlank(message = "UUID é obrigatório")
    private String uuid;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "Área de risco é obrigatória")
    private Long areaRiscoId;

    @NotNull(message = "Modelo de sensor é obrigatório")
    private Long modeloSensorId;

    // Getters e Setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAreaRiscoId() {
        return areaRiscoId;
    }

    public void setAreaRiscoId(Long areaRiscoId) {
        this.areaRiscoId = areaRiscoId;
    }

    public Long getModeloSensorId() {
        return modeloSensorId;
    }

    public void setModeloSensorId(Long modeloSensorId) {
        this.modeloSensorId = modeloSensorId;
    }
}
