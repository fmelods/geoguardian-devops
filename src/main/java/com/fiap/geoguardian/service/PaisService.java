package com.fiap.geoguardian.service;

import com.fiap.geoguardian.model.Pais;
import com.fiap.geoguardian.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    public Page<Pais> findAll(Pageable pageable) {
        return paisRepository.findAll(pageable);
    }

    public Page<Pais> findByNome(String nome, Pageable pageable) {
        return paisRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Pais> findByNomeContaining(String nome, Pageable pageable) {
        return paisRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Optional<Pais> findById(Long id) {
        return paisRepository.findById(id);
    }

    public Pais save(Pais pais) {
        if (paisRepository.existsByNomeIgnoreCase(pais.getNome())) {
            throw new RuntimeException("Já existe um país com este nome");
        }
        return paisRepository.save(pais);
    }

    public Pais update(Long id, Pais pais) {
        Optional<Pais> existingPais = paisRepository.findById(id);
        if (existingPais.isEmpty()) {
            throw new RuntimeException("País não encontrado");
        }

        if (paisRepository.existsByNomeIgnoreCase(pais.getNome()) &&
                !existingPais.get().getNome().equalsIgnoreCase(pais.getNome())) {
            throw new RuntimeException("Já existe um país com este nome");
        }

        pais.setId(id);
        return paisRepository.save(pais);
    }

    public Pais update(Long id, String nome) {
        Pais existingPais = paisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("País não encontrado"));

        if (paisRepository.existsByNomeIgnoreCase(nome) &&
                !existingPais.getNome().equalsIgnoreCase(nome)) {
            throw new RuntimeException("Já existe um país com este nome");
        }

        existingPais.setNome(nome);
        return paisRepository.save(existingPais);
    }

    public void deleteById(Long id) {
        if (!paisRepository.existsById(id)) {
            throw new RuntimeException("País não encontrado");
        }
        paisRepository.deleteById(id);
    }
}
