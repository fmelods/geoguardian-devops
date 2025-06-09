package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    
    @Query("SELECT e FROM Estado e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Estado> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    Page<Estado> findByPaisId(Long paisId, Pageable pageable);
    
    boolean existsByNomeIgnoreCaseAndPaisId(String nome, Long paisId);
}
