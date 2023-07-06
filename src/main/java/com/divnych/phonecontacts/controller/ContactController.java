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

    @GetMapping()
    public List<ContactResponse> getAllContacts() {
        return contactService.getAllContacts();
    }

    @PostMapping("/add")
    public void addContact(@RequestBody @Valid ContactRequest request) {
        contactService.addContact(request);
    }

    @PutMapping("/update/{id}")
    public void updateContact(@PathVariable("id") Long id, @RequestBody ContactRequest request) {
        contactService.updateContact(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContact(@PathVariable("id") Long id) {
        contactService.deleteContact(id);
    }


}
