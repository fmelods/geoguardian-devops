package com.fiap.geoguardian.controller;

import com.fiap.geoguardian.dto.PaisRequestDTO;
import com.fiap.geoguardian.dto.PaisResponseDTO;
import com.fiap.geoguardian.model.Pais;
import com.fiap.geoguardian.service.PaisService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paises")
@Tag(name = "Países", description = "Operações relacionadas aos países")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping
    @Operation(summary = "Listar todos os países", description = "Retorna uma lista de países")
    public ResponseEntity<List<PaisResponseDTO>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<PaisResponseDTO> paises;
        if (nome != null) {
            paises = paisService.findByNomeContaining(nome, pageable)
                    .getContent()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            paises = paisService.findAll(pageable)
                    .getContent()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(paises);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar país por ID", description = "Retorna um país específico pelo ID")
    public ResponseEntity<PaisResponseDTO> findById(@PathVariable Long id) {
        Optional<Pais> pais = paisService.findById(id);
        return pais.map(p -> ResponseEntity.ok(convertToDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo país", description = "Cria um novo país")
    public ResponseEntity<PaisResponseDTO> create(@Valid @RequestBody PaisRequestDTO paisRequest) {
        try {
            Pais pais = new Pais();
            pais.setNome(paisRequest.getNome());
            Pais savedPais = paisService.save(pais);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPais));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar país", description = "Atualiza um país existente")
    public ResponseEntity<PaisResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PaisRequestDTO paisRequest) {
        try {
            Pais updatedPais = paisService.update(id, paisRequest.getNome());
            return ResponseEntity.ok(convertToDTO(updatedPais));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar país", description = "Remove um país")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            paisService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private PaisResponseDTO convertToDTO(Pais pais) {
        return new PaisResponseDTO(pais.getId(), pais.getNome());
    }
}
