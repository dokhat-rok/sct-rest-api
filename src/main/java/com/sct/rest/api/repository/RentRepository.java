package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.RentEntity;
import com.sct.rest.api.model.enums.RentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface RentRepository extends JpaRepository<RentEntity, Long> {

    @Query("select count (rent) from RentEntity rent where rent.customer.login = :login and rent.status = 'CLOSE'")
    Long countRentByCustomerLogin(String login);

    List<RentEntity> findAllByCustomerLoginAndStatus(String login, RentStatus status);

    @Query("select rent from RentEntity rent where (:login is null or rent.customer.login like %:login%) " +
            "and (:transportIdent is null or rent.transport.identificationNumber like %:transportIdent%) " +
            "and (:status is null or rent.status = :status)")
    Page<RentEntity> findAllByFilter(
            Pageable pageable,
            @Nullable String login,
            @Nullable String transportIdent,
            @Nullable RentStatus status
    );
}
