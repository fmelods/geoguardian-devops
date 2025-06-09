package com.fiap.geoguardian.service;

import com.fiap.geoguardian.model.Estado;
import com.fiap.geoguardian.repository.EstadoRepository;
import com.fiap.geoguardian.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Autowired
    private PaisRepository paisRepository;
    
    public Page<Estado> findAll(Pageable pageable) {
        return estadoRepository.findAll(pageable);
    }
    
    public Page<Estado> findByNome(String nome, Pageable pageable) {
        return estadoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }
    
    public Page<Estado> findByPaisId(Long paisId, Pageable pageable) {
        return estadoRepository.findByPaisId(paisId, pageable);
    }
    
    public Optional<Estado> findById(Long id) {
        return estadoRepository.findById(id);
    }
    
    public Estado save(Estado estado) {
        if (!paisRepository.existsById(estado.getPais().getId())) {
            throw new RuntimeException("País não encontrado");
        }
        
        if (estadoRepository.existsByNomeIgnoreCaseAndPaisId(estado.getNome(), estado.getPais().getId())) {
            throw new RuntimeException("Já existe um estado com este nome neste país");
        }
        
        return estadoRepository.save(estado);
    }
    
    public Estado update(Long id, Estado estado) {
        Optional<Estado> existingEstado = estadoRepository.findById(id);
        if (existingEstado.isEmpty()) {
            throw new RuntimeException("Estado não encontrado");
        }
        
        if (!paisRepository.existsById(estado.getPais().getId())) {
            throw new RuntimeException("País não encontrado");
        }
        
        if (estadoRepository.existsByNomeIgnoreCaseAndPaisId(estado.getNome(), estado.getPais().getId()) && 
            (!existingEstado.get().getNome().equalsIgnoreCase(estado.getNome()) || 
             !existingEstado.get().getPais().getId().equals(estado.getPais().getId()))) {
            throw new RuntimeException("Já existe um estado com este nome neste país");
        }
        
        estado.setId(id);
        return estadoRepository.save(estado);
    }
    
    public void deleteById(Long id) {
        if (!estadoRepository.existsById(id)) {
            throw new RuntimeException("Estado não encontrado");
        }
        estadoRepository.deleteById(id);
    }
}
