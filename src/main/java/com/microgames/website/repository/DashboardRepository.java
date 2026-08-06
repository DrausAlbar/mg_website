package com.microgames.website.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microgames.website.entities.WebDashboard;

public interface DashboardRepository extends JpaRepository<WebDashboard, Long> {

    Optional<WebDashboard> findByTitle(String title);

    long countByCategory(String category);

    Optional<WebDashboard> findById(Long id);

    Optional<WebDashboard> findByCategory(String category);

    Optional<WebDashboard> findByAutor(String autor);

    Optional<WebDashboard> findByDatepublished(LocalDateTime datepublished);

}