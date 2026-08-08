package com.microgames.website.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microgames.website.entities.UserData;
import com.microgames.website.repository.UserDataRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDataRepository userDataRepository;

    public CustomUserDetailsService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserData userData = userDataRepository.findByEmailForLogin(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        return new CustomUserDetails(userData);
    }
}