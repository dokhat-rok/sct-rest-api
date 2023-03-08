package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentRepository extends JpaRepository<Rent, Long> {

    @Query("select count (rent) from Rent rent where rent.customer.login = :login and rent.status = 'CLOSE'")
    Long countRentByCustomerLogin(String login);

    @Query("select rent from Rent rent where rent.customer.login = :userLogin")
    Iterable<Rent> allRentByCustomerLogin(String userLogin);

    @Query("select rent from Rent rent where rent.customer.login = :userLogin " +
            "and rent.transport.identificationNumber = :identificationNumber and rent.status = 'OPEN'")
    Rent getRentByLoginAndTransport(String userLogin, String identificationNumber);
}
