package com.example.todos.service;

import com.example.todos.entity.Authority;
import com.example.todos.entity.User;
import com.example.todos.repository.UserRepository;
import com.example.todos.request.AuthRequest;
import com.example.todos.request.RegisterRequest;
import com.example.todos.response.AuthResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional //transmision de datos a db
    @Override
    public void register(RegisterRequest input) throws Exception {
        if (isEmailTaken(input.getEmail())){
            throw new Exception("Email already exists");
        }

        User user = buildNewUser(input);
        userRepository.save(user);
    }

    @Override
    @Transactional()
    public AuthResponse login(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String jwtToken = jwtService.generateToken(new HashMap<>(),user); //maps -> claims
        return new AuthResponse(jwtToken);
    }

    private boolean isEmailTaken(String email) {
        return  userRepository.findByEmail(email).isPresent();
    }


    private User buildNewUser(RegisterRequest input) {
        User user = new User();
        user.setId(0);
        user.setFirstName(input.getFirstname());
        user.setLastName(input.getLastname());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setAuthorities(initialAuthority());
        return user;
    }

    private List<Authority> initialAuthority(){
        boolean isFirstUser = userRepository.count() == 0;
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));

        if (isFirstUser){
            authorities.add(new Authority("ROLE_ADMIN"));
        }
        return authorities;
    }
}
