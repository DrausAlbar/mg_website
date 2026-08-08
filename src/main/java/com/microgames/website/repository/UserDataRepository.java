package com.microgames.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microgames.website.entities.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByEmail(String email);

    @Query("SELECT u FROM UserData u " +
           "LEFT JOIN FETCH u.userSecurity " +
           "LEFT JOIN FETCH u.userAuthority ua " +
           "LEFT JOIN FETCH ua.authority " +
           "WHERE u.email = :email")
    Optional<UserData> findByEmailForLogin(@Param("email") String email);
}