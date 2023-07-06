package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.payload.JwtResponse;
import com.divnych.phonecontacts.payload.LoginRequest;
import com.divnych.phonecontacts.payload.MessageResponse;
import com.divnych.phonecontacts.payload.RegisterRequest;
import com.divnych.phonecontacts.repository.UserRepository;
import com.divnych.phonecontacts.security.jwt.JwtUtils;
import com.divnych.phonecontacts.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder encoder;

    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername()));
    }

    public ResponseEntity<MessageResponse> registerUser(RegisterRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Username " + request.getLogin() + " has already been taken"));
        }
        User user = new User(request.getLogin(),
                encoder.encode(request.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
