package com.microgames.website.security;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microgames.website.entities.UserData;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ProfileCompletionFilter extends OncePerRequestFilter {


    private static final int REGISTER_STATUS_COMPLETE = 2;

    // Rutas permitidas aunque el perfil esté incompleto
    private static final Set<String> ALLOWED_PATHS = Set.of(
            "/complete-profile",
            "/logout",
            "/login"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails principal) {

            UserData userData = principal.getUserData();
            String path = request.getRequestURI();

            boolean isAllowedPath = ALLOWED_PATHS.contains(path)
                    || path.startsWith("/css/")
                    || path.startsWith("/js/")
                    || path.startsWith("/images/");

            if (userData.getRegisterStatus() < REGISTER_STATUS_COMPLETE && !isAllowedPath) {
                response.sendRedirect(request.getContextPath() + "/complete-profile");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}