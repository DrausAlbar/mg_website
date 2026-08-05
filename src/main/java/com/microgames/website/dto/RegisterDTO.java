package com.microgames.website.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {

    @NotBlank
    @Size(max = 64, message = "El correo electrónico no puede tener más de 64 caracteres")
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
    private String password;

    @NotBlank
    private String confirmPassword;

    // getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}