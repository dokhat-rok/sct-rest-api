package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.ParkingEntity;
import com.sct.rest.api.model.enums.ParkingStatus;
import com.sct.rest.api.model.enums.ParkingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {

    @Override
    @Query("select parking from ParkingEntity parking " +
            "where parking.status != 'NON_ACTIVE'")
    List<ParkingEntity> findAll();

    @Query("select parking from ParkingEntity parking " +
            "where parking.name = :parkingName and parking.status != 'NON_ACTIVE'")
    Optional<ParkingEntity> findByName(String parkingName);

    @Query("select parking from ParkingEntity parking where (:name is null or parking.name like %:name%)" +
            "and (:type is null or parking.type = :type) " +
            "and (:status is null or parking.status = :status)")
    Page<ParkingEntity> findAllByFilter(Pageable pageable, String name, ParkingType type, ParkingStatus status);
}
