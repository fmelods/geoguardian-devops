package com.fiap.geoguardian.dto;

import java.util.List;

public class UsuarioRequestDTO {
    private String nome;
    private String email;
    private String senha;
    private Long tipoUsuarioId; // ID simples, sem objeto aninhado
    private List<Long> alertasIds;
    private List<Long> retornosIds;

    public UsuarioRequestDTO() {}

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(Long tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public List<Long> getAlertasIds() {
        return alertasIds;
    }

    public void setAlertasIds(List<Long> alertasIds) {
        this.alertasIds = alertasIds;
    }

    public List<Long> getRetornosIds() {
        return retornosIds;
    }

    public void setRetornosIds(List<Long> retornosIds) {
        this.retornosIds = retornosIds;
    }
}
