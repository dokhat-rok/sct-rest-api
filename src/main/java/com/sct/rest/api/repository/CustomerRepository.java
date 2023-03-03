package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    @Query("select u from User u where u.login = :login")
    Optional<Customer> findByLogin(String login);
}
