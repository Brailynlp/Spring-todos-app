package com.example.todos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthRequest {

    @NotEmpty(message = "Email is mandatory") @Email
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8,max = 30, message = "Password needs to be between 8 and 30")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
