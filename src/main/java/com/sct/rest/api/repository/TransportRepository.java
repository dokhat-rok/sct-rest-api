package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransportRepository extends JpaRepository<Transport, Long> {
    @Query("select t from Transport t where t.identificationNumber = :identificationNumber")
    Transport findByIdentificationNumber(String identificationNumber);
}
