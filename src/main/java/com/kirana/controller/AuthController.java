package com.kirana.controller;

import com.kirana.dto.AuthRequestDTO;
import com.kirana.dto.AuthResponseDTO;
import com.kirana.security.JwtTokenProvider;
import com.kirana.service.impl.UserServiceImpl;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;

    // Constructor injection to avoid circular dependency
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, @Lazy UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String token = jwtTokenProvider.createToken(request.getUsername(), userService.findByUsername(request.getUsername()).getRoles());
            return ResponseEntity.ok(new AuthResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new AuthResponseDTO("Invalid credentials"));
        }
    }
}
