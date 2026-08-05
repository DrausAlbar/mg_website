package com.microgames.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import com.microgames.website.dto.RegisterDTO;
import com.microgames.website.services.UserRegistrationService;

import org.springframework.validation.BindingResult;

@Controller
public class RegisterController {

    private final UserRegistrationService registrationService;

    public RegisterController(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO,
                            BindingResult bindingResult,
                            Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        var errors = registrationService.validate(registerDTO);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "register";
        }

        registrationService.registerUser(registerDTO);
        return "redirect:/login?registered";
    }
}