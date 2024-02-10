package com.crud.adventuretravel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomerDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private LocalDate accountCreationDate;
    private boolean subscriber;
}
