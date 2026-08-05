package com.microgames.website.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microgames.website.dto.RegisterDTO;
import com.microgames.website.entities.UserData;
import com.microgames.website.entities.UserSecurity;
import com.microgames.website.repository.UserDataRepository;

@Service
public class UserRegistrationService {

    private final UserDataRepository userDataRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserDataRepository userDataRepository,                                   
                                    PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<String> validate(RegisterDTO dto) {
        List<String> errors = new java.util.ArrayList<>();

        if (userDataRepository.findByEmail(dto.getEmail()).isPresent()) {
            errors.add("Ese correo ya está registrado.");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            errors.add("Las contraseñas no coinciden.");
        }
        return errors;
    }

    @Transactional
    public UserData registerUser(RegisterDTO dto) {

        UserData user = new UserData();
        user.setEmail(dto.getEmail());

        UserSecurity security = new UserSecurity();
        security.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setUserSecurity(security);

        return userDataRepository.save(user);
    }
}