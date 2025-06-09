package com.fiap.geoguardian.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_ALERTA")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Long id;

    @NotNull(message = "Nível de risco é obrigatório")
    @Min(value = 1, message = "Nível de risco deve ser entre 1 e 5")
    @Max(value = 5, message = "Nível de risco deve ser entre 1 e 5")
    @Column(name = "NIVEL_RISCO", nullable = false)
    private Integer nivelRisco;

    @NotNull(message = "Data e hora são obrigatórias")
    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "Tipo de alerta é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_ALERTA", nullable = false)
    @JsonBackReference
    private TipoAlerta tipoAlerta;

    @NotNull(message = "Área de risco é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AREA_RISCO", nullable = false)
    @JsonBackReference
    private AreaRisco areaRisco;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<AlertaUsuario> alertasUsuario;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<RetornoUsuario> retornosUsuario;

    public Alerta() {
        this.dataHora = LocalDateTime.now();
    }

    public Alerta(Integer nivelRisco, TipoAlerta tipoAlerta, AreaRisco areaRisco) {
        this();
        this.nivelRisco = nivelRisco;
        this.tipoAlerta = tipoAlerta;
        this.areaRisco = areaRisco;
    }

// Getters and Setters
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
    
    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }
    
    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }
    
    public AreaRisco getAreaRisco() {
        return areaRisco;
    }
    
    public void setAreaRisco(AreaRisco areaRisco) {
        this.areaRisco = areaRisco;
    }
    
    public List<AlertaUsuario> getAlertasUsuario() {
        return alertasUsuario;
    }
    
    public void setAlertasUsuario(List<AlertaUsuario> alertasUsuario) {
        this.alertasUsuario = alertasUsuario;
    }
    
    public List<RetornoUsuario> getRetornosUsuario() {
        return retornosUsuario;
    }
    
    public void setRetornosUsuario(List<RetornoUsuario> retornosUsuario) {
        this.retornosUsuario = retornosUsuario;
    }
}
