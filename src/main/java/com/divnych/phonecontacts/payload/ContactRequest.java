package com.divnych.phonecontacts.payload;

import com.divnych.phonecontacts.validation.ValidPhoneNumber;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ContactRequest {

    private String name;

    private Set<@Email String> emails = new HashSet<>();

    private Set<@ValidPhoneNumber(message = "Invalid phone number")String> phoneNumbers = new HashSet<>();

}
