package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Logradouro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {
    
    @Query("SELECT l FROM Logradouro l WHERE LOWER(l.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Logradouro> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    Page<Logradouro> findByBairroId(Long bairroId, Pageable pageable);
    
    boolean existsByNomeIgnoreCaseAndBairroId(String nome, Long bairroId);
}
