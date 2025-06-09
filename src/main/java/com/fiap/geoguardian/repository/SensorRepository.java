package com.fiap.geoguardian.repository;

import com.fiap.geoguardian.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    
    Optional<Sensor> findByUuid(String uuid);
    
    @Query("SELECT s FROM Sensor s WHERE LOWER(s.status) LIKE LOWER(CONCAT('%', :status, '%'))")
    Page<Sensor> findByStatusContainingIgnoreCase(@Param("status") String status, Pageable pageable);
    
    Page<Sensor> findByAreaRiscoId(Long areaRiscoId, Pageable pageable);
    
    Page<Sensor> findByModeloSensorId(Long modeloSensorId, Pageable pageable);
    
    boolean existsByUuid(String uuid);
}
