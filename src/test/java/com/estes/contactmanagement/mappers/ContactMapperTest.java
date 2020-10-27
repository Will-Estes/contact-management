package com.estes.contactmanagement.mappers;

import com.estes.contactmanagement.entities.Contact;
import com.estes.contactmanagement.models.ContactDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactMapperTest {

    ContactMapper contactMapper;

    @BeforeEach
    void before() {
        contactMapper = new ContactMapper();
    }

    @Test
    void contactToContactDto() {
        Contact contact = Contact.builder()
                .id(1L)
                .firstName("test")
                .lastName("last")
                .email("test@test.com")
                .build();

        ContactDto contactDto = contactMapper.contactToContactDto(contact);
        assertEquals(contact.getId(), contactDto.getId());
        assertEquals(contact.getFirstName(), contactDto.getFirstName());
        assertEquals(contact.getLastName(), contactDto.getLastName());
        assertEquals(contact.getEmail(), contactDto.getEmail());
    }

    @Test
    void contactDtoToContact() {
        ContactDto contactDto = ContactDto.builder()
                .id(1L)
                .firstName("test")
                .lastName("last")
                .email("test@test.com")
                .build();

        Contact contact = contactMapper.contactDtoToContact(contactDto);
        assertEquals(contact.getId(), contactDto.getId());
        assertEquals(contact.getFirstName(), contactDto.getFirstName());
        assertEquals(contact.getLastName(), contactDto.getLastName());
        assertEquals(contact.getEmail(), contactDto.getEmail());
    }
}