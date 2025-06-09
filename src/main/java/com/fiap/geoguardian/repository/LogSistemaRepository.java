package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.LogSistema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogSistemaRepository extends JpaRepository<LogSistema, Long> {
    
    Page<LogSistema> findByUsuarioId(Long usuarioId, Pageable pageable);
    
    @Query("SELECT l FROM LogSistema l WHERE LOWER(l.acao) LIKE LOWER(CONCAT('%', :acao, '%'))")
    Page<LogSistema> findByAcaoContainingIgnoreCase(@Param("acao") String acao, Pageable pageable);
    
    @Query("SELECT l FROM LogSistema l WHERE l.dataHora BETWEEN :inicio AND :fim")
    Page<LogSistema> findByDataHoraBetween(@Param("inicio") LocalDateTime inicio, 
                                           @Param("fim") LocalDateTime fim, 
                                           Pageable pageable);
}
