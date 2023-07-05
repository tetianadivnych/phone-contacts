package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.playload.ContactRequest;
import com.divnych.phonecontacts.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private final UserService userService;

    public ContactService(ContactRepository contactRepository, UserService userService) {
        this.contactRepository = contactRepository;
        this.userService = userService;
    }

    public void addContact(ContactRequest request) {
        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setEmails(request.getEmails());
        contact.setPhoneNumbers(request.getPhoneNumbers());
        contact.setUser(userService.getCurrentUser());
        if (contactRepository.existsByName(request.getName())) {
            throw new RuntimeException("Contact with name " + request.getName() + " already exists");
        } else {
            contactRepository.save(contact);
        }
    }
}
