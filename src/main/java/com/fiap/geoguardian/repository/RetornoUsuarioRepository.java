package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.RetornoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RetornoUsuarioRepository extends JpaRepository<RetornoUsuario, Long> {
    
    Page<RetornoUsuario> findByUsuarioId(Long usuarioId, Pageable pageable);
    
    Page<RetornoUsuario> findByAlertaId(Long alertaId, Pageable pageable);
    
    @Query("SELECT r FROM RetornoUsuario r WHERE LOWER(r.tipoRetorno) LIKE LOWER(CONCAT('%', :tipoRetorno, '%'))")
    Page<RetornoUsuario> findByTipoRetornoContainingIgnoreCase(@Param("tipoRetorno") String tipoRetorno, Pageable pageable);
    
    @Query("SELECT r FROM RetornoUsuario r WHERE r.dataHora BETWEEN :inicio AND :fim")
    Page<RetornoUsuario> findByDataHoraBetween(@Param("inicio") LocalDateTime inicio, 
                                               @Param("fim") LocalDateTime fim, 
                                               Pageable pageable);
}
