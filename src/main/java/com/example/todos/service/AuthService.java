package com.example.todos.service;

import com.example.todos.request.AuthRequest;
import com.example.todos.request.RegisterRequest;
import com.example.todos.response.AuthResponse;

public interface AuthService {
    void register(RegisterRequest input) throws Exception;
    AuthResponse login(AuthRequest request);
}
