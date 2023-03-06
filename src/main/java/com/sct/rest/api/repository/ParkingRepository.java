package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Parking findByName(String parkingName);
}
