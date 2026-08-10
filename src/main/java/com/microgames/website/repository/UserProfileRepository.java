package com.microgames.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.website.entities.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    boolean existsByUsername(String username);

    Optional<UserProfile> findByUsername(String username);
}