package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.ParkingEntity;
import com.sct.rest.api.model.enums.ParkingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {

    Optional<ParkingEntity> findByName(String parkingName);

    @Query("select parking from ParkingEntity parking where (:name is null or parking.name like %:name%)" +
            "and (:type is null or parking.type = :type)")
    Page<ParkingEntity> findAllByFilter(Pageable pageable, String name, ParkingType type);
}
