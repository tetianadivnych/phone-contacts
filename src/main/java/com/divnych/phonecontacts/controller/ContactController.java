package com.divnych.phonecontacts.controller;

import com.divnych.phonecontacts.payload.ContactRequest;
import com.divnych.phonecontacts.payload.ContactResponse;
import com.divnych.phonecontacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

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

    @PatchMapping("/{id}/images")
    public void addContactImage(@PathVariable("id") Long id, @RequestBody MultipartFile image) {
        contactService.addContactImage(id, image);
    }

    @GetMapping(value = "/{id}/images", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        byte[] imageBytes = contactService.getContactImage(id);
        var httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(CONTENT_DISPOSITION, ContentDisposition.attachment().filename("image.jpeg").build().toString());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(imageBytes);
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
