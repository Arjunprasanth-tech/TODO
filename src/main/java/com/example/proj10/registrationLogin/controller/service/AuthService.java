package com.example.proj10.registrationLogin.controller.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        String email = request.getEmail().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException(
                    "Email already exists");
        }

        User user = new User();

        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        userRepository.save(user);

        return "Registration Successful";
    }

    public String login(LoginRequest request) {

        User user = userRepository
                .findByEmail(
                        request.getEmail().trim())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid Password");
        }

        return jwtUtil.generateToken(
                user.getEmail());
    }
}