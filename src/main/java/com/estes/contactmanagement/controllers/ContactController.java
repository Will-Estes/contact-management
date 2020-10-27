package com.estes.contactmanagement.controllers;

import com.estes.contactmanagement.models.ContactDto;
import com.estes.contactmanagement.services.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<?> getAllContacts() {
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(contactService.getContactById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveContact(@RequestBody ContactDto contactDto) {
        return new ResponseEntity<>(contactService.saveContact(contactDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable("id") Long id, @RequestBody ContactDto contactDto) {
        return new ResponseEntity<>(contactService.updateContact(id, contactDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeContactById(@PathVariable("id") Long id) {
        contactService.removeContact(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
