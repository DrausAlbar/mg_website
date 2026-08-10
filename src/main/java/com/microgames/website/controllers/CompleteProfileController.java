package com.microgames.website.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.microgames.website.dto.RegisterUsernameDTO;
import com.microgames.website.security.AuthenticationRefresher;
import com.microgames.website.security.CustomUserDetails;
import com.microgames.website.services.ProfileCompletionService;

@Controller
public class CompleteProfileController {

    private final ProfileCompletionService profileCompletionService;
    private final AuthenticationRefresher authenticationRefresher;

    public CompleteProfileController(ProfileCompletionService profileCompletionService,
                                      AuthenticationRefresher authenticationRefresher) {
        this.profileCompletionService = profileCompletionService;
        this.authenticationRefresher = authenticationRefresher;
    }

    @GetMapping("/complete-profile")
    public String showForm(Model model) {
        model.addAttribute("registerUsernameDTO", new RegisterUsernameDTO());
        return "complete-profile";
    }

    @PostMapping("/complete-profile")
    public String completeProfile(@Valid @ModelAttribute RegisterUsernameDTO registerUsernameDTO,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal CustomUserDetails principal,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            return "complete-profile";
        }

        var errors = profileCompletionService.validate(registerUsernameDTO);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "complete-profile";
        }

        profileCompletionService.completeProfile(principal.getUserData().getId(), registerUsernameDTO);
        authenticationRefresher.refresh(principal.getUsername(), request, response);

        return "redirect:/home";
    }
}