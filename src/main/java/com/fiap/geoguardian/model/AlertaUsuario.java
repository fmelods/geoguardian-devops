package com.fiap.geoguardian.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_ALERTA_USUARIO")
public class AlertaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA_USUARIO")
    private Long id;

    @NotBlank(message = "Modo de envio é obrigatório")
    @Size(max = 50, message = "Modo de envio deve ter no máximo 50 caracteres")
    @Column(name = "MODO_ENVIO", nullable = false, length = 50)
    private String modoEnvio;

    @NotBlank(message = "Confirmação de recebimento é obrigatória")
    @Pattern(regexp = "[SN]", message = "Confirmação de recebimento deve ser S ou N")
    @Column(name = "CONFIRMADO_RECEBIMENTO", nullable = false, length = 1)
    private String confirmadoRecebimento;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @NotNull(message = "Alerta é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALERTA", nullable = false)
    @JsonBackReference
    private Alerta alerta;

    // Constructors
    public AlertaUsuario() {}

    public AlertaUsuario(String modoEnvio, String confirmadoRecebimento, Usuario usuario, Alerta alerta) {
        this.modoEnvio = modoEnvio;
        this.confirmadoRecebimento = confirmadoRecebimento;
        this.usuario = usuario;
        this.alerta = alerta;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModoEnvio() {
        return modoEnvio;
    }

    public void setModoEnvio(String modoEnvio) {
        this.modoEnvio = modoEnvio;
    }

    public String getConfirmadoRecebimento() {
        return confirmadoRecebimento;
    }

    public void setConfirmadoRecebimento(String confirmadoRecebimento) {
        this.confirmadoRecebimento = confirmadoRecebimento;
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
