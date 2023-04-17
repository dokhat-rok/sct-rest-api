package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.TransportEntity;
import com.sct.rest.api.model.enums.Condition;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransportRepository extends JpaRepository<TransportEntity, Long> {

    Optional<TransportEntity> findByIdentificationNumber(String identificationNumber);

    @Query("select transport from TransportEntity transport " +
            "where (:type is null or transport.type = :type) " +
            "and (:status is null or transport.status = :status)")
    List<TransportEntity> findAllByTypeAndStatus(TransportType type, TransportStatus status);

    @Query("select transport from TransportEntity transport " +
            "where (:ident is null or transport.identificationNumber like %:ident%)" +
            "and (:parkName is null or transport.parking.name like %:parkName%)" +
            "and (:condition is null or transport.condition = :condition)" +
            "and (:status is null or transport.status = :status)")
    Page<TransportEntity> findAllByFilter(Pageable pageable,
                                          String ident,
                                          String parkName,
                                          Condition condition,
                                          TransportStatus status);
}
