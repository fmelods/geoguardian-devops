package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    
    @Query("SELECT c FROM Cidade c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Cidade> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    Page<Cidade> findByEstadoId(Long estadoId, Pageable pageable);
    
    boolean existsByNomeIgnoreCaseAndEstadoId(String nome, Long estadoId);
}
