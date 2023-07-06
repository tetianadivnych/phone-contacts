package com.divnych.phonecontacts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    @OneToMany(mappedBy = "user")
    List<Contact> contacts = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
