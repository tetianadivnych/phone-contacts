package com.divnych.phonecontacts.mapper;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.payload.ContactResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ContactMapper {

    public static ContactResponse mapContactToContactResponse(Contact contact) {
        ContactResponse response = new ContactResponse();
        response.setId(contact.getId());
        response.setName(contact.getName());
        response.setEmails(contact.getEmails());
        response.setPhoneNumbers(contact.getPhoneNumbers());
        return response;
    }

    public static List<ContactResponse> mapContactsToContactResponses(List<Contact> contacts) {
        return contacts.stream()
                .map(contact -> mapContactToContactResponse(contact))
                .collect(Collectors.toList());
    }
}
