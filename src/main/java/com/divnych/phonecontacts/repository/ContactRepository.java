package com.divnych.phonecontacts.repository;

import com.divnych.phonecontacts.entity.Contact;
import com.divnych.phonecontacts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByName(String name);

    List<Contact> findByUser(User user);

}
