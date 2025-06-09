package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Bairro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Long> {
    
    @Query("SELECT b FROM Bairro b WHERE LOWER(b.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Bairro> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    Page<Bairro> findByCidadeId(Long cidadeId, Pageable pageable);
    
    boolean existsByNomeIgnoreCaseAndCidadeId(String nome, Long cidadeId);
}
