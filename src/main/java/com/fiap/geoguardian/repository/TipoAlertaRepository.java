package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.TipoAlerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAlertaRepository extends JpaRepository<TipoAlerta, Long> {
    
    @Query("SELECT t FROM TipoAlerta t WHERE LOWER(t.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
    Page<TipoAlerta> findByDescricaoContainingIgnoreCase(@Param("descricao") String descricao, Pageable pageable);
    
    boolean existsByDescricaoIgnoreCase(String descricao);
}
