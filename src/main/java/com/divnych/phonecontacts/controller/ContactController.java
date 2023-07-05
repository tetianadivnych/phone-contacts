package com.divnych.phonecontacts.controller;

import com.divnych.phonecontacts.payload.ContactRequest;
import com.divnych.phonecontacts.service.ContactService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update/{id}")
    public void updateContact(@PathVariable("id") Long id, @RequestBody ContactRequest request) {
        contactService.updateContact(id, request);
    }



}
