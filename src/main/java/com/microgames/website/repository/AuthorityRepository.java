package com.microgames.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microgames.website.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthName(String authName);

    boolean existsByAuthName(String authName);

    long countByAuthName(String authName);

}