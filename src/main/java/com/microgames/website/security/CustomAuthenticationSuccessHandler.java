package com.microgames.website.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.microgames.website.entities.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final int REGISTER_STATUS_COMPLETE = 2;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException, ServletException {

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        UserData userData = principal.getUserData();

        if (userData.getRegisterStatus() < REGISTER_STATUS_COMPLETE) {
            response.sendRedirect("/complete-profile");
        } else {
            response.sendRedirect("/home");
        }
    }
}