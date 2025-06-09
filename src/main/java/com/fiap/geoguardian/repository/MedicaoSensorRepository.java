package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.MedicaoSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicaoSensorRepository extends JpaRepository<MedicaoSensor, Long> {
    
    Page<MedicaoSensor> findBySensorId(Long sensorId, Pageable pageable);
    
    @Query("SELECT m FROM MedicaoSensor m WHERE m.sensor.id = :sensorId AND m.dataHora BETWEEN :inicio AND :fim")
    Page<MedicaoSensor> findBySensorIdAndDataHoraBetween(@Param("sensorId") Long sensorId, 
                                                         @Param("inicio") LocalDateTime inicio, 
                                                         @Param("fim") LocalDateTime fim, 
                                                         Pageable pageable);
    
    @Query("SELECT AVG(m.valor) FROM MedicaoSensor m WHERE m.sensor.id = :sensorId")
    Double calcularMediaValorPorSensor(@Param("sensorId") Long sensorId);
}
