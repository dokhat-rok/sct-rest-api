package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.CustomerEntity;
import com.sct.rest.api.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByLogin(String login);

    @Query("select customer from CustomerEntity customer where (:login is null or customer.login like %:login%) " +
            "and (:role is null or customer.role = :role)")
    Page<CustomerEntity> findAllByFilter(Pageable pageable, String login, Role role);
}
