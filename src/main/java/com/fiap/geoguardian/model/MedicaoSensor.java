package com.fiap.geoguardian.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MEDICAO_SENSOR")
public class MedicaoSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICAO")
    private Long id;

    @NotNull(message = "Valor é obrigatório")
    @Column(name = "VALOR", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "Data e hora são obrigatórias")
    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "Sensor é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SENSOR", nullable = false)
    @JsonBackReference // Evita loop infinito na serialização
    private Sensor sensor;

    // Construtores
    public MedicaoSensor() {
        this.dataHora = LocalDateTime.now();
    }

    public MedicaoSensor(BigDecimal valor, Sensor sensor) {
        this();
        this.valor = valor;
        this.sensor = sensor;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
