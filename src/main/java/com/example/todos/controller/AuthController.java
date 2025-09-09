package com.example.todos.controller;

import com.example.todos.request.AuthRequest;
import com.example.todos.request.RegisterRequest;
import com.example.todos.response.AuthResponse;
import com.example.todos.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Rest Endpoints")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
        authService.register(registerRequest);
    }

    @Operation(summary = "Login a user",description = "Authentication of a user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request){
        return authService.login(request);
    }
}
