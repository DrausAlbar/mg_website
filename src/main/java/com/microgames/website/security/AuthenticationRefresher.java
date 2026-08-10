package com.microgames.website.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationRefresher {

    private final CustomUserDetailsService userDetailsService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public AuthenticationRefresher(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Recarga el UserDetails desde la base de datos y lo reinstala como
     * Authentication activo, tanto en el SecurityContextHolder (hilo actual)
     * como en la sesión HTTP (para peticiones futuras).
     */
    public void refresh(String email, HttpServletRequest request, HttpServletResponse response) {
        CustomUserDetails freshDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(freshDetails, null, freshDetails.getAuthorities());
        newAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(newAuth);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);
    }
}