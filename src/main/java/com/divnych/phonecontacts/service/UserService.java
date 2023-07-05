package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = principal.getUsername();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with login " + login + " not found"));
    }
}
