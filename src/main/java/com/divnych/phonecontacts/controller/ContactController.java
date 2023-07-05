package com.divnych.phonecontacts.controller;

import com.divnych.phonecontacts.playload.ContactRequest;
import com.divnych.phonecontacts.service.ContactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/add")
    public void addContact(@RequestBody ContactRequest request) {
        contactService.addContact(request);
    }


}
