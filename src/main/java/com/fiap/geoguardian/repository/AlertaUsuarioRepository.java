package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.AlertaUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaUsuarioRepository extends JpaRepository<AlertaUsuario, Long> {
    
    Page<AlertaUsuario> findByUsuarioId(Long usuarioId, Pageable pageable);
    
    Page<AlertaUsuario> findByAlertaId(Long alertaId, Pageable pageable);
    
    @Query("SELECT a FROM AlertaUsuario a WHERE a.confirmadoRecebimento = :confirmado")
    Page<AlertaUsuario> findByConfirmadoRecebimento(@Param("confirmado") String confirmado, Pageable pageable);
    
    @Query("SELECT a FROM AlertaUsuario a WHERE LOWER(a.modoEnvio) LIKE LOWER(CONCAT('%', :modoEnvio, '%'))")
    Page<AlertaUsuario> findByModoEnvioContainingIgnoreCase(@Param("modoEnvio") String modoEnvio, Pageable pageable);
}
