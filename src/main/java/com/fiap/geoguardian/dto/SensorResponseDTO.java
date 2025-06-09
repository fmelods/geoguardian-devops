package com.fiap.geoguardian.dto;

public class SensorResponseDTO {

    private Long id;
    private String uuid;
    private String status;
    private String areaRisco;
    private String modeloSensor;

    // Construtores
    public SensorResponseDTO() {}

    public SensorResponseDTO(Long id, String uuid, String status, String areaRisco, String modeloSensor) {
        this.id = id;
        this.uuid = uuid;
        this.status = status;
        this.areaRisco = areaRisco;
        this.modeloSensor = modeloSensor;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(String areaRisco) {
        this.areaRisco = areaRisco;
    }

    public String getModeloSensor() {
        return modeloSensor;
    }

    public void setModeloSensor(String modeloSensor) {
        this.modeloSensor = modeloSensor;
    }
}
