package com.divnych.phonecontacts;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.payload.ContactResponse;
import com.divnych.phonecontacts.repository.ContactRepository;
import com.divnych.phonecontacts.service.ContactService;
import com.divnych.phonecontacts.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @InjectMocks
    ContactService contactService;

    @Mock
    UserService userService;

    @Mock
    ContactRepository contactRepository;

    @Test
    @DisplayName("Should return contacts for current user")
    void getContacts() {
        User user = generateUser();
        when(userService.getCurrentUser()).thenReturn(user);
        List<Contact> contacts = generateContacts();
        when(contactRepository.findByUser(user)).thenReturn(contacts);
        List<ContactResponse> actualResult = contactService.getContacts();
        assertEquals(2, actualResult.size());
        assertEquals(1L, actualResult.get(0).getId());
        assertEquals(2L, actualResult.get(1).getId());
    }

    private static List<Contact> generateContacts() {
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setName("Robin");
        contact1.setUser(generateUser());
        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setName("Lora");
        contact2.setUser(generateUser());
        return List.of(contact1, contact2);
    }

    private static User generateUser() {
        User user = new User();
        user.setId(1L);
        return user;
    }


}
