package com.microgames.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microgames.website.entities.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByEmail(String email);
}