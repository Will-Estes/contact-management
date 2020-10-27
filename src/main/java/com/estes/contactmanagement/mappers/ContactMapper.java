package com.estes.contactmanagement.mappers;

import com.estes.contactmanagement.entities.Contact;
import com.estes.contactmanagement.models.ContactDto;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactDto contactToContactDto(Contact contact) {
        return ContactDto.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .build();
    }

    public Contact contactDtoToContact(ContactDto contactDto) {
        return Contact.builder()
                .id(contactDto.getId())
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .email(contactDto.getEmail())
                .build();
    }

}
