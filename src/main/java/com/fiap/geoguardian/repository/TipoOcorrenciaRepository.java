package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.TipoOcorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoOcorrenciaRepository extends JpaRepository<TipoOcorrencia, Long> {
    
    @Query("SELECT t FROM TipoOcorrencia t WHERE LOWER(t.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
    Page<TipoOcorrencia> findByDescricaoContainingIgnoreCase(@Param("descricao") String descricao, Pageable pageable);
    
    boolean existsByDescricaoIgnoreCase(String descricao);
}
