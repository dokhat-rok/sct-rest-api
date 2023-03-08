package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Optional<Parking> findByName(String parkingName);
}
