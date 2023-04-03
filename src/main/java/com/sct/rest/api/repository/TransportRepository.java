package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.TransportEntity;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransportRepository extends JpaRepository<TransportEntity, Long> {

    Optional<TransportEntity> findByIdentificationNumber(String identificationNumber);

    List<TransportEntity> findAllByType(TransportType type);

    List<TransportEntity> findAllByStatus(TransportStatus status);

    List<TransportEntity> findAllByTypeAndStatus(TransportType type, TransportStatus status);
}
