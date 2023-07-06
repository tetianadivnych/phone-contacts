package com.divnych.phonecontacts.controller;

import com.divnych.phonecontacts.payload.ContactRequest;
import com.divnych.phonecontacts.payload.ContactResponse;
import com.divnych.phonecontacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public List<ContactResponse> getContacts() {
        return contactService.getContacts();
    }

    @PostMapping
    public void addContact(@RequestBody @Valid ContactRequest request) {
        contactService.addContact(request);
    }

    @PutMapping("/{id}")
    public void updateContact(@PathVariable("id") @Valid Long id, @RequestBody ContactRequest request) {
        contactService.updateContact(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable("id") Long id) {
        contactService.deleteContact(id);
    }


}
