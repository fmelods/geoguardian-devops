package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.AreaRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRiscoRepository extends JpaRepository<AreaRisco, Long> {
    
    @Query("SELECT a FROM AreaRisco a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<AreaRisco> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    Page<AreaRisco> findByTipoAreaId(Long tipoAreaId, Pageable pageable);
    
    Page<AreaRisco> findByLogradouroId(Long logradouroId, Pageable pageable);
    
    @Query("SELECT AVG(a.nivelRisco) FROM Alerta a WHERE a.areaRisco.id = :areaRiscoId")
    Double calcularRiscoMedio(@Param("areaRiscoId") Long areaRiscoId);
}
