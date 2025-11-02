package com.api.bookstore.library_backend.service;

import com.api.bookstore.library_backend.config.JwtTokenProvider;
import com.api.bookstore.library_backend.dto.request.AuthenticationRequest;
import com.api.bookstore.library_backend.dto.response.AuthenticationResponse;
import com.api.bookstore.library_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean authenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticate) {
            throw new RuntimeException("Invalid password");
        }

        var token = jwtTokenProvider.generateToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}