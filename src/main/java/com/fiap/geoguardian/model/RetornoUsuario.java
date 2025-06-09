package com.fiap.geoguardian.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RETORNO_USUARIO")
public class RetornoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RETORNO")
    private Long id;

    @NotBlank(message = "Tipo de retorno é obrigatório")
    @Size(max = 100, message = "Tipo de retorno deve ter no máximo 100 caracteres")
    @Column(name = "TIPO_RETORNO", nullable = false, length = 100)
    private String tipoRetorno;

    @NotNull(message = "Data e hora são obrigatórias")
    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @NotNull(message = "Alerta é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALERTA", nullable = false)
    private Alerta alerta;

    // Construtores
    public RetornoUsuario() {
        this.dataHora = LocalDateTime.now();
    }

    public RetornoUsuario(String tipoRetorno, Usuario usuario, Alerta alerta) {
        this();
        this.tipoRetorno = tipoRetorno;
        this.usuario = usuario;
        this.alerta = alerta;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }
}
