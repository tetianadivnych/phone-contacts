package com.divnych.phonecontacts.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ContactResponse {

    private Long id;

    private String name;

    private Set<String> emails = new HashSet<>();

    private Set<Integer> phoneNumbers = new HashSet<>();

}
