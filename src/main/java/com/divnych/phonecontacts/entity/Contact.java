package com.divnych.phonecontacts.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ElementCollection
    private Set<String> emails = new HashSet<>();

    @ElementCollection
    private Set<Integer> phoneNumbers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
