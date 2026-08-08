package com.microgames.website.security;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception) throws IOException, ServletException {

        String errorCode;
        if (exception instanceof LockedException) {
            errorCode = "locked";
        } else if (exception instanceof DisabledException) {
            errorCode = "banned";
        } else {
            errorCode = "bad_credentials";
        }

        response.sendRedirect("/login?error=" + errorCode);
    }
}