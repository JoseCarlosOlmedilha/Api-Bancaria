package com.apiBancaria.Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiBancaria.Api.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);

}
