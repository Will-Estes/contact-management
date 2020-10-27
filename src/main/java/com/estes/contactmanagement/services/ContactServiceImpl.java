package com.estes.contactmanagement.services;

import com.estes.contactmanagement.entities.Contact;
import com.estes.contactmanagement.exceptions.NotFoundException;
import com.estes.contactmanagement.mappers.ContactMapper;
import com.estes.contactmanagement.models.ContactDto;
import com.estes.contactmanagement.repositories.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::contactToContactDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(Long id) {
        return contactRepository.findById(id)
                .map(contactMapper::contactToContactDto)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
    }

    @Override
    public ContactDto saveContact(ContactDto contactDto) {
        //Map to Contact, then map result to ContactDto
        return contactMapper.contactToContactDto(contactRepository.save(contactMapper.contactDtoToContact(contactDto)));
    }

    @Override
    public void removeContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        } else {
            throw new NotFoundException("Contact not found");
        }
    }

    @Override
    public ContactDto updateContact(Long id, ContactDto contactDto) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new NotFoundException("Contact not found"));
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setEmail(contactDto.getEmail());
//        contact.setUpdatedDate(Timestamp.from(Instant.now()));
        return contactMapper.contactToContactDto(contactRepository.save(contact));
    }
}
