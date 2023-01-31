package com.sct.rest.api.repository;

import com.sct.rest.api.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
    @Query("select u from User u where u.login = :login")
    User findByLogin(String login);
}
