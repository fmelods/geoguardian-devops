package com.fiap.geoguardian.service;

import com.fiap.geoguardian.dto.SensorRequestDTO;
import com.fiap.geoguardian.dto.SensorResponseDTO;
import com.fiap.geoguardian.model.Sensor;
import com.fiap.geoguardian.model.AreaRisco;
import com.fiap.geoguardian.model.ModeloSensor;
import com.fiap.geoguardian.repository.AreaRiscoRepository;
import com.fiap.geoguardian.repository.ModeloSensorRepository;
import com.fiap.geoguardian.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private AreaRiscoRepository areaRiscoRepository;

    @Autowired
    private ModeloSensorRepository modeloSensorRepository;

    // Métodos originais
    public Page<Sensor> findAll(Pageable pageable) {
        return sensorRepository.findAll(pageable);
    }

    public Page<Sensor> findByStatus(String status, Pageable pageable) {
        return sensorRepository.findByStatusContainingIgnoreCase(status, pageable);
    }

    public Page<Sensor> findByAreaRiscoId(Long areaRiscoId, Pageable pageable) {
        return sensorRepository.findByAreaRiscoId(areaRiscoId, pageable);
    }

    public Page<Sensor> findByModeloSensorId(Long modeloSensorId, Pageable pageable) {
        return sensorRepository.findByModeloSensorId(modeloSensorId, pageable);
    }

    public Optional<Sensor> findById(Long id) {
        return sensorRepository.findById(id);
    }

    public Optional<Sensor> findByUuid(String uuid) {
        return sensorRepository.findByUuid(uuid);
    }

    public Sensor save(Sensor sensor) {
        if (sensorRepository.existsByUuid(sensor.getUuid())) {
            throw new RuntimeException("Já existe um sensor com este UUID");
        }

        if (!areaRiscoRepository.existsById(sensor.getAreaRisco().getId())) {
            throw new RuntimeException("Área de risco não encontrada");
        }

        if (!modeloSensorRepository.existsById(sensor.getModeloSensor().getId())) {
            throw new RuntimeException("Modelo de sensor não encontrado");
        }

        return sensorRepository.save(sensor);
    }

    public Sensor update(Long id, Sensor sensor) {
        Optional<Sensor> existingSensor = sensorRepository.findById(id);
        if (existingSensor.isEmpty()) {
            throw new RuntimeException("Sensor não encontrado");
        }

        if (sensorRepository.existsByUuid(sensor.getUuid()) &&
                !existingSensor.get().getUuid().equals(sensor.getUuid())) {
            throw new RuntimeException("Já existe um sensor com este UUID");
        }

        if (!areaRiscoRepository.existsById(sensor.getAreaRisco().getId())) {
            throw new RuntimeException("Área de risco não encontrada");
        }

        if (!modeloSensorRepository.existsById(sensor.getModeloSensor().getId())) {
            throw new RuntimeException("Modelo de sensor não encontrado");
        }

        sensor.setId(id);
        return sensorRepository.save(sensor);
    }

    public void deleteById(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new RuntimeException("Sensor não encontrado");
        }
        sensorRepository.deleteById(id);
    }

    // Métodos DTO
    public Page<SensorResponseDTO> findAllDTO(Pageable pageable) {
        return sensorRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Page<SensorResponseDTO> findByStatusDTO(String status, Pageable pageable) {
        return sensorRepository.findByStatusContainingIgnoreCase(status, pageable).map(this::convertToDTO);
    }

    public Page<SensorResponseDTO> findByAreaRiscoIdDTO(Long areaRiscoId, Pageable pageable) {
        return sensorRepository.findByAreaRiscoId(areaRiscoId, pageable).map(this::convertToDTO);
    }

    public Page<SensorResponseDTO> findByModeloSensorIdDTO(Long modeloSensorId, Pageable pageable) {
        return sensorRepository.findByModeloSensorId(modeloSensorId, pageable).map(this::convertToDTO);
    }

    public Optional<SensorResponseDTO> findByIdDTO(Long id) {
        return sensorRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<SensorResponseDTO> findByUuidDTO(String uuid) {
        return sensorRepository.findByUuid(uuid).map(this::convertToDTO);
    }

    public SensorResponseDTO saveDTO(SensorRequestDTO sensorRequest) {
        if (sensorRepository.existsByUuid(sensorRequest.getUuid())) {
            throw new RuntimeException("Já existe um sensor com este UUID");
        }

        AreaRisco areaRisco = areaRiscoRepository.findById(sensorRequest.getAreaRiscoId())
                .orElseThrow(() -> new RuntimeException("Área de risco não encontrada"));

        ModeloSensor modeloSensor = modeloSensorRepository.findById(sensorRequest.getModeloSensorId())
                .orElseThrow(() -> new RuntimeException("Modelo de sensor não encontrado"));

        Sensor sensor = new Sensor();
        sensor.setUuid(sensorRequest.getUuid());
        sensor.setStatus(sensorRequest.getStatus());
        sensor.setAreaRisco(areaRisco);
        sensor.setModeloSensor(modeloSensor);

        Sensor savedSensor = sensorRepository.save(sensor);
        return convertToDTO(savedSensor);
    }

    public SensorResponseDTO updateDTO(Long id, SensorRequestDTO sensorRequest) {
        Sensor existingSensor = sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        if (sensorRepository.existsByUuid(sensorRequest.getUuid()) &&
                !existingSensor.getUuid().equals(sensorRequest.getUuid())) {
            throw new RuntimeException("Já existe um sensor com este UUID");
        }

        AreaRisco areaRisco = areaRiscoRepository.findById(sensorRequest.getAreaRiscoId())
                .orElseThrow(() -> new RuntimeException("Área de risco não encontrada"));

        ModeloSensor modeloSensor = modeloSensorRepository.findById(sensorRequest.getModeloSensorId())
                .orElseThrow(() -> new RuntimeException("Modelo de sensor não encontrado"));

        existingSensor.setUuid(sensorRequest.getUuid());
        existingSensor.setStatus(sensorRequest.getStatus());
        existingSensor.setAreaRisco(areaRisco);
        existingSensor.setModeloSensor(modeloSensor);

        Sensor updatedSensor = sensorRepository.save(existingSensor);
        return convertToDTO(updatedSensor);
    }

    private SensorResponseDTO convertToDTO(Sensor sensor) {
        return new SensorResponseDTO(
                sensor.getId(),
                sensor.getUuid(),
                sensor.getStatus(),
                sensor.getAreaRisco().getNome(),
                sensor.getModeloSensor().getNome()
        );
    }
}
