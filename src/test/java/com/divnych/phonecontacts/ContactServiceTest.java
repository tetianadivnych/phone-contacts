package com.divnych.phonecontacts;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.exception.DuplicateContactFoundException;
import com.divnych.phonecontacts.payload.ContactRequest;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    void testGetContacts() {
        User user = generateUser();
        when(userService.getCurrentUser()).thenReturn(user);
        List<Contact> contacts = generateContacts();
        when(contactRepository.findByUser(user)).thenReturn(contacts);
        List<ContactResponse> actualResult = contactService.getContacts();
        assertEquals(2, actualResult.size());
        assertEquals(1L, actualResult.get(0).getId());
        assertEquals(2L, actualResult.get(1).getId());
    }



    @Test
    @DisplayName("Should create a new contact")
    public void testAddContact() {
        ContactRequest request = generateContactRequest();
        User user = generateUser();
        when(contactRepository.existsByName(request.getName())).thenReturn(false);
        when(userService.getCurrentUser()).thenReturn(user);
        contactService.addContact(request);
        verify(contactRepository, times(1)).existsByName(request.getName());
        verify(userService, times(1)).getCurrentUser();
        verify(contactRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when contact name exists")
    public void testAddContactWithExistingName() {
        ContactRequest request = generateContactRequest();
        when(contactRepository.existsByName(request.getName())).thenReturn(true);
        assertThrows(DuplicateContactFoundException.class, () -> contactService.addContact(request));
        verifyNoInteractions(userService);
        verifyNoMoreInteractions(contactRepository);
    }

    @Test
    @DisplayName("Should update contact")
    public void testUpdateContact() {
        ContactRequest request = generateContactRequest();
        Long id = 1L;
        Contact contact = generateContact();
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        contactService.updateContact(id, request);
        verify(contactRepository, times(1)).findById(id);
        verify(contactRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should delete existing contact")
    public void testDeleteContact() {
        User user = generateUser();
        Long id = 1L;
        Contact contact = generateContact();
        contact.setUser(user);
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        when(userService.getCurrentUser()).thenReturn(user);
        contactService.deleteContact(id);
        verify(contactRepository, times(1)).findById(id);
        verify(userService, times(1)).getCurrentUser();
        verify(contactRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should not delete existing contact")
    public void testDeleteContactWithWrongUser() {
        Long id = 1L;
        Contact contact = generateContact();
        contact.setUser(generateUser());
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        when(userService.getCurrentUser()).thenReturn(generateUser(2L));
        contactService.deleteContact(id);
        verify(contactRepository, times(1)).findById(id);
        verify(userService, times(1)).getCurrentUser();
        verifyNoMoreInteractions(contactRepository);
    }

    private static Contact generateContact() {
        Contact contact = new Contact();
        contact.setId(1L);
        return contact;
    }

    private static List<Contact> generateContacts() {
        Contact contact1 = generateContact();
        contact1.setName("Robin");
        contact1.setUser(generateUser());
        Contact contact2 = generateContact();
        contact2.setId(2L);
        contact2.setName("Lora");
        contact2.setUser(generateUser());
        return List.of(contact1, contact2);
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
        return user;
    }

    private static User generateUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }






}
