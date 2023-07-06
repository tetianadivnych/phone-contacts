package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.exception.UserNotFoundException;
import com.divnych.phonecontacts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = principal.getUsername();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login " + login + " not found"));
    }

}
