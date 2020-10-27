package com.estes.contactmanagement.services;

import com.estes.contactmanagement.entities.Contact;
import com.estes.contactmanagement.exceptions.NotFoundException;
import com.estes.contactmanagement.mappers.ContactMapper;
import com.estes.contactmanagement.models.ContactDto;
import com.estes.contactmanagement.repositories.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

class ContactServiceImplTest {

    @Mock
    ContactRepository contactRepository;

    ContactMapper contactMapper;

    ContactService contactService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        contactMapper = new ContactMapper();
        contactService = new ContactServiceImpl(contactRepository, contactMapper);
    }

    @Test
    void getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        contactList.add(Contact.builder().id(1L).firstName("test").lastName("last").email("test@test.com").build());
        contactList.add(Contact.builder().id(2L).firstName("test2").lastName("last2").email("test2@test.com").build());

        given(contactRepository.findAll()).willReturn(contactList);

        List<ContactDto> contactDtos = contactService.getAllContacts();
        assertEquals(contactMapper.contactToContactDto(contactList.get(0)), contactDtos.get(0));
        assertEquals(contactMapper.contactToContactDto(contactList.get(1)), contactDtos.get(1));
    }

    @Test
    void getContactById() {
        Contact contact = Contact.builder().id(1L).firstName("test").lastName("last").email("test@test.com").build();
        given(contactRepository.findById(1L)).willReturn(Optional.of(contact));

        ContactDto contactDto = contactService.getContactById(1L);

        assertEquals(contactMapper.contactToContactDto(contact), contactDto);
    }

    @Test
    void getContactByIdNotExist() {
        given(contactRepository.findById(any())).willReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            contactService.getContactById(1L);
        });
        assertEquals("Contact not found", ex.getMessage());
    }

    @Test
    void saveContact() {
        ContactDto contactDto = ContactDto.builder().firstName("test").lastName("last").email("test@test.com").build();
        Contact contact = contactMapper.contactDtoToContact(contactDto);
        contact.setId(1L);
        given(contactRepository.save(any())).willReturn(contact);

        ContactDto result = contactService.saveContact(contactDto);

        assertEquals(contactDto.getFirstName(), result.getFirstName());
        assertEquals(contactDto.getLastName(), result.getLastName());
        assertEquals(contactDto.getEmail(), result.getEmail());
        assertEquals(contact.getId(), result.getId());
    }

    @Test
    void removeContact() {
        given(contactRepository.existsById(1L)).willReturn(true);
        doNothing().when(contactRepository).deleteById(anyLong());
        contactService.removeContact(1L);
    }

    @Test
    void removeContactNotExists() {
        given(contactRepository.existsById(1L)).willReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            contactService.removeContact(1L);
        });
        assertEquals("Contact not found", ex.getMessage());
    }

    @Test
    void updateContact() {
        ContactDto contactDto = ContactDto.builder().id(1L).firstName("test1").lastName("last1").email("test1@test.com").build();
        Contact contact = Contact.builder().id(1L).firstName("test").lastName("last").email("test@test.com").build();
        given(contactRepository.findById(any())).willReturn(Optional.of(contact));
        given(contactRepository.save(any())).willReturn(contact);
        ContactDto result = contactService.updateContact(1L, contactDto);
        assertEquals(contactMapper.contactToContactDto(contact), result);
    }

    @Test
    void updateContactNotExist() {
        given(contactRepository.findById(any())).willReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            contactService.updateContact(1L, ContactDto.builder().build());
        });
        assertEquals("Contact not found", ex.getMessage());
    }
}