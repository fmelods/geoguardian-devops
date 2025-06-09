package com.fiap.geoguardian.service;

import com.fiap.geoguardian.dto.AlertaRequestDTO;
import com.fiap.geoguardian.dto.AlertaResponseDTO;
import com.fiap.geoguardian.model.Alerta;
import com.fiap.geoguardian.model.AreaRisco;
import com.fiap.geoguardian.model.TipoAlerta;
import com.fiap.geoguardian.repository.AlertaRepository;
import com.fiap.geoguardian.repository.AreaRiscoRepository;
import com.fiap.geoguardian.repository.TipoAlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private TipoAlertaRepository tipoAlertaRepository;

    @Autowired
    private AreaRiscoRepository areaRiscoRepository;

    public Page<Alerta> findAll(Pageable pageable) {
        return alertaRepository.findAll(pageable);
    }

    public Page<AlertaResponseDTO> findAllDTO(Pageable pageable) {
        return alertaRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Page<Alerta> findByNivelRisco(Integer nivelRisco, Pageable pageable) {
        return alertaRepository.findByNivelRisco(nivelRisco, pageable);
    }

    public Page<AlertaResponseDTO> findByNivelRiscoDTO(Integer nivelRisco, Pageable pageable) {
        return alertaRepository.findByNivelRisco(nivelRisco, pageable).map(this::convertToDTO);
    }

    public Page<Alerta> findByTipoAlertaId(Long tipoAlertaId, Pageable pageable) {
        return alertaRepository.findByTipoAlertaId(tipoAlertaId, pageable);
    }

    public Page<AlertaResponseDTO> findByTipoAlertaIdDTO(Long tipoAlertaId, Pageable pageable) {
        return alertaRepository.findByTipoAlertaId(tipoAlertaId, pageable).map(this::convertToDTO);
    }

    public Page<Alerta> findByAreaRiscoId(Long areaRiscoId, Pageable pageable) {
        return alertaRepository.findByAreaRiscoId(areaRiscoId, pageable);
    }

    public Page<AlertaResponseDTO> findByAreaRiscoIdDTO(Long areaRiscoId, Pageable pageable) {
        return alertaRepository.findByAreaRiscoId(areaRiscoId, pageable).map(this::convertToDTO);
    }

    public Page<Alerta> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return alertaRepository.findByDataHoraBetween(inicio, fim, pageable);
    }

    public Page<AlertaResponseDTO> findByDataHoraBetweenDTO(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return alertaRepository.findByDataHoraBetween(inicio, fim, pageable).map(this::convertToDTO);
    }

    public Optional<Alerta> findById(Long id) {
        return alertaRepository.findById(id);
    }

    public Optional<AlertaResponseDTO> findByIdDTO(Long id) {
        return alertaRepository.findById(id).map(this::convertToDTO);
    }

    public Alerta save(Alerta alerta) {
        if (!tipoAlertaRepository.existsById(alerta.getTipoAlerta().getId())) {
            throw new RuntimeException("Tipo de alerta não encontrado");
        }

        if (!areaRiscoRepository.existsById(alerta.getAreaRisco().getId())) {
            throw new RuntimeException("Área de risco não encontrada");
        }

        return alertaRepository.save(alerta);
    }

    public AlertaResponseDTO saveDTO(AlertaRequestDTO alertaRequest) {
        TipoAlerta tipoAlerta = tipoAlertaRepository.findById(alertaRequest.getTipoAlertaId())
                .orElseThrow(() -> new RuntimeException("Tipo de alerta não encontrado"));

        AreaRisco areaRisco = areaRiscoRepository.findById(alertaRequest.getAreaRiscoId())
                .orElseThrow(() -> new RuntimeException("Área de risco não encontrada"));

        Alerta alerta = new Alerta();
        alerta.setNivelRisco(alertaRequest.getNivelRisco());
        alerta.setDataHora(LocalDateTime.now());
        alerta.setTipoAlerta(tipoAlerta);
        alerta.setAreaRisco(areaRisco);

        Alerta savedAlerta = alertaRepository.save(alerta);
        return convertToDTO(savedAlerta);
    }

    public Alerta update(Long id, Alerta alerta) {
        Optional<Alerta> existingAlerta = alertaRepository.findById(id);
        if (existingAlerta.isEmpty()) {
            throw new RuntimeException("Alerta não encontrado");
        }

        if (!tipoAlertaRepository.existsById(alerta.getTipoAlerta().getId())) {
            throw new RuntimeException("Tipo de alerta não encontrado");
        }

        if (!areaRiscoRepository.existsById(alerta.getAreaRisco().getId())) {
            throw new RuntimeException("Área de risco não encontrada");
        }

        alerta.setId(id);
        alerta.setDataHora(existingAlerta.get().getDataHora());
        return alertaRepository.save(alerta);
    }

    public AlertaResponseDTO updateDTO(Long id, AlertaRequestDTO alertaRequest) {
        Alerta existingAlerta = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));

        TipoAlerta tipoAlerta = tipoAlertaRepository.findById(alertaRequest.getTipoAlertaId())
                .orElseThrow(() -> new RuntimeException("Tipo de alerta não encontrado"));

        AreaRisco areaRisco = areaRiscoRepository.findById(alertaRequest.getAreaRiscoId())
                .orElseThrow(() -> new RuntimeException("Área de risco não encontrada"));

        existingAlerta.setNivelRisco(alertaRequest.getNivelRisco());
        existingAlerta.setTipoAlerta(tipoAlerta);
        existingAlerta.setAreaRisco(areaRisco);

        Alerta updatedAlerta = alertaRepository.save(existingAlerta);
        return convertToDTO(updatedAlerta);
    }

    public void deleteById(Long id) {
        if (!alertaRepository.existsById(id)) {
            throw new RuntimeException("Alerta não encontrado");
        }
        alertaRepository.deleteById(id);
    }

    public Long countByTipoAlertaId(Long tipoAlertaId) {
        return alertaRepository.countByTipoAlertaId(tipoAlertaId);
    }

    private AlertaResponseDTO convertToDTO(Alerta alerta) {
        return new AlertaResponseDTO(
                alerta.getId(),
                alerta.getNivelRisco(),
                alerta.getDataHora(),
                alerta.getTipoAlerta().getDescricao(),
                alerta.getAreaRisco().getNome()
        );
    }
}
