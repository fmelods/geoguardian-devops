package com.fiap.geoguardian.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AlertaRequestDTO {

    @NotNull(message = "Nível de risco é obrigatório")
    @Min(value = 1, message = "Nível de risco deve ser entre 1 e 5")
    @Max(value = 5, message = "Nível de risco deve ser entre 1 e 5")
    private Integer nivelRisco;

    @NotNull(message = "Tipo de alerta é obrigatório")
    private Long tipoAlertaId;

    @NotNull(message = "Área de risco é obrigatória")
    private Long areaRiscoId;

    // Getters e Setters
    public Integer getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(Integer nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public Long getTipoAlertaId() {
        return tipoAlertaId;
    }

    public void setTipoAlertaId(Long tipoAlertaId) {
        this.tipoAlertaId = tipoAlertaId;
    }

    public Long getAreaRiscoId() {
        return areaRiscoId;
    }

    public void setAreaRiscoId(Long areaRiscoId) {
        this.areaRiscoId = areaRiscoId;
    }
}
