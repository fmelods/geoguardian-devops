package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Ocorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    
    Page<Ocorrencia> findByTipoOcorrenciaId(Long tipoOcorrenciaId, Pageable pageable);
    
    Page<Ocorrencia> findByAreaRiscoId(Long areaRiscoId, Pageable pageable);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.confirmada = :confirmada")
    Page<Ocorrencia> findByConfirmada(@Param("confirmada") String confirmada, Pageable pageable);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.dataOcorrencia BETWEEN :inicio AND :fim")
    Page<Ocorrencia> findByDataOcorrenciaBetween(@Param("inicio") LocalDateTime inicio, 
                                                 @Param("fim") LocalDateTime fim, 
                                                 Pageable pageable);
    
    @Query("SELECT COUNT(o) FROM Ocorrencia o WHERE o.areaRisco.logradouro.bairro.cidade.id = :cidadeId")
    Long countOcorrenciasPorCidade(@Param("cidadeId") Long cidadeId);
}
