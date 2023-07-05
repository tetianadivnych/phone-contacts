package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.mapper.ContactMapper;
import com.divnych.phonecontacts.payload.ContactRequest;
import com.divnych.phonecontacts.payload.ContactResponse;
import com.divnych.phonecontacts.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updateContact(Long id, ContactRequest request) {
        Contact existingContact = contactRepository.findById(id).orElseThrow(()->new RuntimeException("Task is not found"));
        existingContact.setName(request.getName());
        existingContact.setEmails(request.getEmails());
        existingContact.setPhoneNumbers(request.getPhoneNumbers());
        contactRepository.save(existingContact);
    }

    public List<ContactResponse> getAllContacts() {
        List<Contact> contacts = contactRepository.findByUser(userService.getCurrentUser());
        return ContactMapper.mapContactsToContactResponses(contacts);
    }

}
