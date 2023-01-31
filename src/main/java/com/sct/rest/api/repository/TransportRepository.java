package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Transport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransportRepository extends CrudRepository<Transport, Long>{
    @Query("select t from Transport t where t.identificationNumber = :identificationNumber")
    Transport findByIdentificationNumber(String identificationNumber);
}
