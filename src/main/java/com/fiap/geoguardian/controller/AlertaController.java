package com.fiap.geoguardian.controller;

import com.fiap.geoguardian.dto.AlertaRequestDTO;
import com.fiap.geoguardian.dto.AlertaResponseDTO;
import com.fiap.geoguardian.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Alertas", description = "Operações relacionadas aos alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    @Operation(summary = "Listar todos os alertas", description = "Retorna uma lista de alertas")
    public ResponseEntity<List<AlertaResponseDTO>> findAll(
            @RequestParam(required = false) Integer nivelRisco,
            @RequestParam(required = false) Long tipoAlertaId,
            @RequestParam(required = false) Long areaRiscoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<AlertaResponseDTO> alertas;
        if (inicio != null && fim != null) {
            alertas = alertaService.findByDataHoraBetweenDTO(inicio, fim, pageable).getContent();
        } else if (nivelRisco != null) {
            alertas = alertaService.findByNivelRiscoDTO(nivelRisco, pageable).getContent();
        } else if (tipoAlertaId != null) {
            alertas = alertaService.findByTipoAlertaIdDTO(tipoAlertaId, pageable).getContent();
        } else if (areaRiscoId != null) {
            alertas = alertaService.findByAreaRiscoIdDTO(areaRiscoId, pageable).getContent();
        } else {
            alertas = alertaService.findAllDTO(pageable).getContent();
        }

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID", description = "Retorna um alerta específico pelo ID")
    public ResponseEntity<AlertaResponseDTO> findById(@PathVariable Long id) {
        Optional<AlertaResponseDTO> alerta = alertaService.findByIdDTO(id);
        return alerta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo alerta", description = "Cria um novo alerta")
    public ResponseEntity<AlertaResponseDTO> create(@Valid @RequestBody AlertaRequestDTO alertaRequest) {
        try {
            AlertaResponseDTO savedAlerta = alertaService.saveDTO(alertaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAlerta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alerta", description = "Atualiza um alerta existente")
    public ResponseEntity<AlertaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AlertaRequestDTO alertaRequest) {
        try {
            AlertaResponseDTO updatedAlerta = alertaService.updateDTO(id, alertaRequest);
            return ResponseEntity.ok(updatedAlerta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar alerta", description = "Remove um alerta")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            alertaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estatisticas/tipo/{tipoAlertaId}")
    @Operation(summary = "Contar alertas por tipo", description = "Retorna a quantidade de alertas por tipo")
    public ResponseEntity<Long> countByTipoAlertaId(@PathVariable Long tipoAlertaId) {
        Long count = alertaService.countByTipoAlertaId(tipoAlertaId);
        return ResponseEntity.ok(count);
    }
}
