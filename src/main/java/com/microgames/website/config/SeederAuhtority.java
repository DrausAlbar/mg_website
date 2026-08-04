package com.microgames.website.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microgames.website.entities.Authority;
import com.microgames.website.repository.AuthorityRepository;

@Configuration
public class SeederAuhtority {

    @Bean
    CommandLineRunner init(AuthorityRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Authority("ROLE_ALERT"));
                repository.save(new Authority("ROLE_MASTER"));
                repository.save(new Authority("ROLE_MODERATOR"));
                repository.save(new Authority("ROLE_SUPPORT"));
                repository.save(new Authority("ROLE_USER"));
            }
        };
    }
}