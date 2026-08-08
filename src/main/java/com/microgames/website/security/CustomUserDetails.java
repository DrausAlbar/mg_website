package com.microgames.website.security;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.microgames.website.entities.UserData;

public class CustomUserDetails implements UserDetails {

    private final UserData userData;

    public CustomUserDetails(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userData.getUserAuthority() == null || userData.getUserAuthority().getAuthority() == null) {
            return List.of();
        }
        String authName = userData.getUserAuthority().getAuthority().getAuthName();
        return List.of(new SimpleGrantedAuthority(authName));
    }

    @Override
    public String getPassword() {
        return userData.getUserSecurity() != null ? userData.getUserSecurity().getPasswordHash() : null;
    }

    @Override
    public String getUsername() {
        return userData.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        LocalDateTime lockedUntil = userData.getLockedUntil();
        return lockedUntil == null || !lockedUntil.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !userData.isBanned();
    }
}