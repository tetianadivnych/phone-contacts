package com.divnych.phonecontacts.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ContactRequest {


    private String name;

    private Set<@Email String> emails = new HashSet<>();

    private Set<@Pattern(regexp = "^\\+\\d+$", message = "Invalid phone number") String> phoneNumbers = new HashSet<>();

}
