package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Pais;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    
    @Query("SELECT p FROM Pais p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Pais> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    boolean existsByNomeIgnoreCase(String nome);
}
