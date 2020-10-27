package com.estes.contactmanagement.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
