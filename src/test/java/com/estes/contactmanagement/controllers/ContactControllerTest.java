package com.estes.contactmanagement.controllers;

import com.estes.contactmanagement.models.ContactDto;
import com.estes.contactmanagement.services.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactService contactService;

    @Test
    void getAllContacts() throws Exception {
        given(contactService.getAllContacts()).willReturn(new ArrayList<>());
        mockMvc.perform(get("/api/v1/contacts").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getContactById() throws Exception {
        given(contactService.getContactById(1L)).willReturn(any(ContactDto.class));
        mockMvc.perform(get("/api/v1/contacts/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void saveContact() throws Exception {
        ContactDto contactDto = buildValidContactDto();
        String json = objectMapper.writeValueAsString(contactDto);
        given(contactService.saveContact(any())).willReturn(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/contacts").contentType(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    void updateContact() throws Exception {
        ContactDto contactDto = buildValidContactDto();
        String json = objectMapper.writeValueAsString(contactDto);
        given(contactService.updateContact(1L, contactDto)).willReturn(contactDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/contacts/1").contentType(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isAccepted());
    }

    @Test
    void removeContactById() throws Exception {
        doNothing().when(contactService).removeContact(anyLong());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/contacts/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isAccepted());
    }

    private ContactDto buildValidContactDto() {
        return ContactDto.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Last")
                .email("test@test.com")
                .build();
    }
}