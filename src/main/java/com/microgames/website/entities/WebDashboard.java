package com.microgames.website.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "web_dashboard")
public class WebDashboard {

    @Id
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "autor", nullable = false, length = 255)
    private String autor;

    @Column
    private LocalDateTime datepublished;

    @Column(name = "category", nullable = false, length = 255)
    private String category;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String news;

}
