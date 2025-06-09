package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.TipoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {

    @Query("SELECT t FROM TipoUsuario t WHERE LOWER(t.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
    Page<TipoUsuario> findByDescricaoContainingIgnoreCase(@Param("descricao") String descricao, Pageable pageable);

    Optional<TipoUsuario> findByDescricao(String descricao);

    boolean existsByDescricaoIgnoreCase(String descricao);
}
