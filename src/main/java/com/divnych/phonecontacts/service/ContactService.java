package com.divnych.phonecontacts.service;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.entity.User;
import com.divnych.phonecontacts.exception.ContactNotFoundException;
import com.divnych.phonecontacts.exception.DuplicateContactFoundException;
import com.divnych.phonecontacts.mapper.ContactMapper;
import com.divnych.phonecontacts.payload.ContactRequest;
import com.divnych.phonecontacts.payload.ContactResponse;
import com.divnych.phonecontacts.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    private final UserService userService;

    public List<ContactResponse> getContacts() {
        List<Contact> contacts = contactRepository.findByUser(userService.getCurrentUser());
        return ContactMapper.mapContactsToContactResponses(contacts);
    }

    public void addContact(ContactRequest request) {
        if (contactRepository.existsByName(request.getName())) {
            throw new DuplicateContactFoundException("Contact with name " + request.getName() + " already exists");
        }
        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setEmails(request.getEmails());
        contact.setPhoneNumbers(request.getPhoneNumbers());
        contact.setUser(userService.getCurrentUser());
        contactRepository.save(contact);
    }

    public void updateContact(Long id, ContactRequest request) {
        Contact existingContact = getContactById(id);
        existingContact.setName(request.getName());
        existingContact.setEmails(request.getEmails());
        existingContact.setPhoneNumbers(request.getPhoneNumbers());
        contactRepository.save(existingContact);
    }

    private Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact with id " + id + " is not found"));
    }

    public void deleteContact(Long id) {
        Contact existingContact = getContactById(id);
        User contactUser = existingContact.getUser();
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId().equals(contactUser.getId())) {
            contactRepository.deleteById(id);
        }
    }

}
