package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.RentEntity;
import com.sct.rest.api.model.enums.RentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentRepository extends JpaRepository<RentEntity, Long> {

    @Query("select count (rent) from RentEntity rent where rent.customer.login = :login and rent.status = 'CLOSE'")
    Long countRentByCustomerLogin(String login);

    List<RentEntity> findAllByCustomerLoginAndStatus(String login, RentStatus status);
}
