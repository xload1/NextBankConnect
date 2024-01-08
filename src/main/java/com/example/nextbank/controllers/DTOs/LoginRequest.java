package com.example.nextbank.controllers.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Email(message = "Email must be valid")
    String email;
    @NotNull(message = "Password is required")
    String password;
}
