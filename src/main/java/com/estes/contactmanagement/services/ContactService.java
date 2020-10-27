package com.estes.contactmanagement.services;

import com.estes.contactmanagement.models.ContactDto;

import java.util.List;

public interface ContactService {

    List<ContactDto> getAllContacts();

    ContactDto getContactById(Long id);

    ContactDto saveContact(ContactDto contactDto);

    void removeContact(Long id);

    ContactDto updateContact(Long id, ContactDto contactDto);
}
