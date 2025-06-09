package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Method to find users by name containing a string (case insensitive)
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    
    // Method to find users by tipo usuario id (using relationship navigation)
    Page<Usuario> findByTipoUsuarioId(Long tipoUsuarioId, Pageable pageable);
    
    // Method to find user by email
    Optional<Usuario> findByEmail(String email);
    
    // Method to check if user exists by email
    boolean existsByEmail(String email);
    
    // Alternative query methods (if you need more complex queries)
    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario.id = :tipoUsuarioId")
    Page<Usuario> findByTipoUsuarioIdCustom(@Param("tipoUsuarioId") Long tipoUsuarioId, Pageable pageable);
    
    @Query("SELECT u FROM Usuario u WHERE UPPER(u.nome) LIKE UPPER(CONCAT('%', :nome, '%'))")
    Page<Usuario> findByNomeContainingIgnoreCaseCustom(@Param("nome") String nome, Pageable pageable);
}