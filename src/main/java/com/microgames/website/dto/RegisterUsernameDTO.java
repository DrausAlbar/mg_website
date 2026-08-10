package com.microgames.website.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterUsernameDTO {

    @NotBlank
    @Size(max = 32, message = "El nombre de usuario no puede tener más de 32 caracteres")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
