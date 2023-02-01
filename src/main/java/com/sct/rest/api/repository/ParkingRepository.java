package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParkingRepository  extends JpaRepository<Parking, Long> {
    @Query("select p from Parking p where p.name = :parkingName")
    Parking findByName(String parkingName);
}
