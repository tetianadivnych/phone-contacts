package com.divnych.phonecontacts.controller;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.payload.ContactRequest;
import com.divnych.phonecontacts.repository.ContactRepository;
import com.divnych.phonecontacts.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.save(generateUser());
    }

    @Test
    @WithMockUser(username = "user")
    void getContacts() throws Exception {
        Contact contact = generateContact();
        contact.setUser(generateUser());
        contactRepository.save(contact);
        mockMvc.perform(get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "user")
    void updateContact() throws Exception {
        Long id = 1L;
        ContactRequest request = generateContactRequest();
        Contact existingContact = generateContact();
        contactRepository.save(existingContact);
        mockMvc.perform(put("/contacts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        Contact contact = contactRepository.findById(id).get();
        assertEquals(contact.getName(), request.getName());
    }

    @Test
    @WithMockUser(username = "user")
    void deleteContact() throws Exception {
        User user = generateUser();
        Contact contact = generateContact();
        contact.setUser(user);
        contactRepository.save(contact);
        Long id = 1L;
        mockMvc.perform(delete("/contacts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void addContact() throws Exception {
        ContactRequest request = generateContactRequest();
        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        Contact contact = contactRepository.findById(1L).get();
        assertEquals(request.getName(), contact.getName());
    }

    private static ContactRequest generateContactRequest() {
        ContactRequest request = new ContactRequest();
        request.setName("Peter");
        request.setEmails(Set.of("p.parker@gmail.com", "peter.parker@gmail.com"));
        request.setPhoneNumbers(Set.of("+380671122333", "+380671122334"));
        return request;
    }

    private static User generateUser() {
        User user = new User();
        user.setId(1L);
        user.setLogin("user");
        return user;
    }

    private static Contact generateContact() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Monika");
        return contact;
    }
}