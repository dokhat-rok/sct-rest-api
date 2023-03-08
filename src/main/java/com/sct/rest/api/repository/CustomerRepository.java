package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByLogin(String login);
}
