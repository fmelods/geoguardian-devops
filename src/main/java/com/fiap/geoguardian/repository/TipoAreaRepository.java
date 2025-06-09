package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.TipoArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAreaRepository extends JpaRepository<TipoArea, Long> {
    
    @Query("SELECT t FROM TipoArea t WHERE LOWER(t.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
    Page<TipoArea> findByDescricaoContainingIgnoreCase(@Param("descricao") String descricao, Pageable pageable);
    
    boolean existsByDescricaoIgnoreCase(String descricao);
}
