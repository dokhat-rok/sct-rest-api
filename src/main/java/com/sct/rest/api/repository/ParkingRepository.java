package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {

    Optional<ParkingEntity> findByName(String parkingName);
}
