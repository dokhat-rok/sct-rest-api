package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Parking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ParkingRepository  extends CrudRepository<Parking, Long> {
    @Query("select p from Parking p where p.name = :parkingName")
    Parking findByName(String parkingName);
}
