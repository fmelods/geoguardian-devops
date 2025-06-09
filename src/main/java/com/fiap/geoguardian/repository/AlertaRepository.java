package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    
    Page<Alerta> findByNivelRisco(Integer nivelRisco, Pageable pageable);
    
    Page<Alerta> findByTipoAlertaId(Long tipoAlertaId, Pageable pageable);
    
    Page<Alerta> findByAreaRiscoId(Long areaRiscoId, Pageable pageable);
    
    @Query("SELECT a FROM Alerta a WHERE a.dataHora BETWEEN :inicio AND :fim")
    Page<Alerta> findByDataHoraBetween(@Param("inicio") LocalDateTime inicio, 
                                       @Param("fim") LocalDateTime fim, 
                                       Pageable pageable);
    
    @Query("SELECT COUNT(a) FROM Alerta a WHERE a.tipoAlerta.id = :tipoAlertaId")
    Long countByTipoAlertaId(@Param("tipoAlertaId") Long tipoAlertaId);
}
