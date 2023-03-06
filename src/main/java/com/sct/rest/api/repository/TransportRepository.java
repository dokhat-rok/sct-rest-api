package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<Transport, Long> {

    Transport findByIdentificationNumber(String identificationNumber);
}
