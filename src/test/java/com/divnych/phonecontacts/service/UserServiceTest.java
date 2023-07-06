package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.repository.UserRepository;
import com.divnych.phonecontacts.security.service.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication authentication;

    @Test
    @DisplayName("Should return authorized user")
    void getCurrentUser() {
        User expectedUser = generateUser();
        UserDetails userDetails = UserDetailsImpl.build(expectedUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.findByLogin(expectedUser.getLogin())).thenReturn(Optional.of(expectedUser));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        User actualUser = userService.getCurrentUser();
        assertEquals(expectedUser.getId(), actualUser.getId());
    }

    private static User generateUser() {
        User user = new User();
        user.setId(1L);
        user.setLogin("user");
        return user;
    }

}