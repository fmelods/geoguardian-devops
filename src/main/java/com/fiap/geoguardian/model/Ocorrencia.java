package com.fiap.geoguardian.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_OCORRENCIA")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OCORRENCIA")
    private Long id;

    @NotNull(message = "Data da ocorrência é obrigatória")
    @Column(name = "DATA_OCORRENCIA", nullable = false)
    private LocalDateTime dataOcorrencia;

    @Lob
    @Column(name = "OBSERVACAO")
    private String observacao;

    @NotNull(message = "Confirmação é obrigatória")
    @Pattern(regexp = "[SN]", message = "Confirmação deve ser S ou N")
    @Column(name = "CONFIRMADA", nullable = false, length = 1)
    private String confirmada;

    @NotNull(message = "Tipo de ocorrência é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OCORRENCIA", nullable = false)
    @JsonBackReference
    private TipoOcorrencia tipoOcorrencia;

    @NotNull(message = "Área de risco é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AREA_RISCO", nullable = false)
    @JsonBackReference
    private AreaRisco areaRisco;

    // Constructors
    public Ocorrencia() {
        this.dataOcorrencia = LocalDateTime.now();
    }

    public Ocorrencia(String observacao, String confirmada, TipoOcorrencia tipoOcorrencia, AreaRisco areaRisco) {
        this();
        this.observacao = observacao;
        this.confirmada = confirmada;
        this.tipoOcorrencia = tipoOcorrencia;
        this.areaRisco = areaRisco;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(String confirmada) {
        this.confirmada = confirmada;
    }

    public TipoOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public AreaRisco getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(AreaRisco areaRisco) {
        this.areaRisco = areaRisco;
    }
}
