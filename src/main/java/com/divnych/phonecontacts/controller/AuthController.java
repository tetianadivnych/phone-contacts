package com.divnych.phonecontacts.controller;

import com.divnych.phonecontacts.payload.LoginRequest;
import com.divnych.phonecontacts.payload.RegisterRequest;
import com.divnych.phonecontacts.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
        return authService.authenticateUser(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

}
