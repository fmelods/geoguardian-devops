package com.fiap.geoguardian.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "TB_SENSOR")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SENSOR")
    private Long id;

    @NotBlank(message = "UUID é obrigatório")
    @Size(max = 100, message = "UUID deve ter no máximo 100 caracteres")
    @Column(name = "UUID", nullable = false, length = 100, unique = true)
    private String uuid;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    @NotNull(message = "Área de risco é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AREA_RISCO", nullable = false)
    @JsonBackReference
    private AreaRisco areaRisco;

    @NotNull(message = "Modelo do sensor é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MODELO_SENSOR", nullable = false)
    @JsonIgnore // evita recursão infinita ao serializar
    private ModeloSensor modeloSensor;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicaoSensor> medicoes;

    // Constructors
    public Sensor() {}

    public Sensor(String uuid, String status, AreaRisco areaRisco, ModeloSensor modeloSensor) {
        this.uuid = uuid;
        this.status = status;
        this.areaRisco = areaRisco;
        this.modeloSensor = modeloSensor;
    }

    // Getters and Setters
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

    public AreaRisco getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(AreaRisco areaRisco) {
        this.areaRisco = areaRisco;
    }

    public ModeloSensor getModeloSensor() {
        return modeloSensor;
    }

    public void setModeloSensor(ModeloSensor modeloSensor) {
        this.modeloSensor = modeloSensor;
    }

    public List<MedicaoSensor> getMedicoes() {
        return medicoes;
    }

    public void setMedicoes(List<MedicaoSensor> medicoes) {
        this.medicoes = medicoes;
    }
}
