package com.fiap.geoguardian.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "TB_MODELO_SENSOR")
public class ModeloSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODELO_SENSOR")
    private Long id;

    @NotBlank(message = "Fabricante é obrigatório")
    @Size(max = 100, message = "Fabricante deve ter no máximo 100 caracteres")
    @Column(name = "FABRICANTE", nullable = false, length = 100)
    private String fabricante;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Tipo de medição é obrigatório")
    @Size(max = 100, message = "Tipo de medição deve ter no máximo 100 caracteres")
    @Column(name = "TIPO_MEDICAO", nullable = false, length = 100)
    private String tipoMedicao;

    @OneToMany(mappedBy = "modeloSensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Sensor> sensores;

    // Constructors
    public ModeloSensor() {}

    public ModeloSensor(String fabricante, String nome, String tipoMedicao) {
        this.fabricante = fabricante;
        this.nome = nome;
        this.tipoMedicao = tipoMedicao;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoMedicao() {
        return tipoMedicao;
    }

    public void setTipoMedicao(String tipoMedicao) {
        this.tipoMedicao = tipoMedicao;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }
}
