package com.fiap.geoguardian.controller;

import com.fiap.geoguardian.dto.SensorRequestDTO;
import com.fiap.geoguardian.dto.SensorResponseDTO;
import com.fiap.geoguardian.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensores")
@Tag(name = "Sensores", description = "Operações relacionadas aos sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping
    @Operation(summary = "Listar todos os sensores", description = "Retorna uma lista de sensores")
    public ResponseEntity<List<SensorResponseDTO>> findAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long areaRiscoId,
            @RequestParam(required = false) Long modeloSensorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<SensorResponseDTO> sensores;
        if (status != null) {
            sensores = sensorService.findByStatusDTO(status, pageable).getContent();
        } else if (areaRiscoId != null) {
            sensores = sensorService.findByAreaRiscoIdDTO(areaRiscoId, pageable).getContent();
        } else if (modeloSensorId != null) {
            sensores = sensorService.findByModeloSensorIdDTO(modeloSensorId, pageable).getContent();
        } else {
            sensores = sensorService.findAllDTO(pageable).getContent();
        }

        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sensor por ID", description = "Retorna um sensor específico pelo ID")
    public ResponseEntity<SensorResponseDTO> findById(@PathVariable Long id) {
        Optional<SensorResponseDTO> sensor = sensorService.findByIdDTO(id);
        return sensor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar sensor por UUID", description = "Retorna um sensor específico pelo UUID")
    public ResponseEntity<SensorResponseDTO> findByUuid(@PathVariable String uuid) {
        Optional<SensorResponseDTO> sensor = sensorService.findByUuidDTO(uuid);
        return sensor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo sensor", description = "Cria um novo sensor")
    public ResponseEntity<SensorResponseDTO> create(@Valid @RequestBody SensorRequestDTO sensorRequest) {
        try {
            SensorResponseDTO savedSensor = sensorService.saveDTO(sensorRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSensor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar sensor", description = "Atualiza um sensor existente")
    public ResponseEntity<SensorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SensorRequestDTO sensorRequest) {
        try {
            SensorResponseDTO updatedSensor = sensorService.updateDTO(id, sensorRequest);
            return ResponseEntity.ok(updatedSensor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar sensor", description = "Remove um sensor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            sensorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
