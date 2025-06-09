package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.ModeloSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloSensorRepository extends JpaRepository<ModeloSensor, Long> {
    
    @Query("SELECT m FROM ModeloSensor m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<ModeloSensor> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    @Query("SELECT m FROM ModeloSensor m WHERE LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :fabricante, '%'))")
    Page<ModeloSensor> findByFabricanteContainingIgnoreCase(@Param("fabricante") String fabricante, Pageable pageable);
    
    @Query("SELECT m FROM ModeloSensor m WHERE LOWER(m.tipoMedicao) LIKE LOWER(CONCAT('%', :tipoMedicao, '%'))")
    Page<ModeloSensor> findByTipoMedicaoContainingIgnoreCase(@Param("tipoMedicao") String tipoMedicao, Pageable pageable);
}
