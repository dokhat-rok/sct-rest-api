package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    @Query("select u from User u where u.login = :login")
    Customer findByLogin(String login);
}
