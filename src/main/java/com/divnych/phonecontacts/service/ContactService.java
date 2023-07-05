package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.entity.User;
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
        Contact existingContact = getContactById(id);
        existingContact.setName(request.getName());
        existingContact.setEmails(request.getEmails());
        existingContact.setPhoneNumbers(request.getPhoneNumbers());
        contactRepository.save(existingContact);
    }

    private Contact getContactById(Long id) {
        return contactRepository.findById(id).orElseThrow(()->new RuntimeException("Task is not found"));
    }

    public List<ContactResponse> getAllContacts() {
        List<Contact> contacts = contactRepository.findByUser(userService.getCurrentUser());
        return ContactMapper.mapContactsToContactResponses(contacts);
    }

    public void deleteContact(Long id) {
        Contact existingContact = getContactById(id);
        User contactUser = existingContact.getUser();
        User currentUser = userService.getCurrentUser();
        if(currentUser.getId().equals(contactUser.getId())) {
            contactRepository.deleteById(id);
        }
    }
}
