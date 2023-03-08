package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Transport;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransportRepository extends JpaRepository<Transport, Long> {

    Optional<Transport> findByIdentificationNumber(String identificationNumber);

    List<Transport> findAllByType(TransportType type);

    List<Transport> findAllByStatus(TransportStatus status);

    List<Transport> findAllByTypeAndStatus(TransportType type, TransportStatus status);
}
