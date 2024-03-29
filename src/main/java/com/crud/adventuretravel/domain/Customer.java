package com.crud.adventuretravel.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name = "NAME")
    private String firstName;

    @NotEmpty
    @Column(name = "LASTNAME")
    private String lastName;

    @Email
    @NotEmpty
    @Column(name = "EMAIL")
    private String email;

    @NotEmpty
    @Column(name = "PHONE_NUMBER")
    private int phoneNumber;

    @Column(name = "ACCOUNT_CREATION_DATE")
    private LocalDate accountCreationDate;

    @Column(name = "SUBSCRIBER")
    private boolean subscriber;

    @Column(name = "ACTIVE")
    private boolean isActive;

    @OneToMany(
            targetEntity = Reservation.class,
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    private List<Reservation> reservationCustomerList = new ArrayList<>();

    public Customer(String firstName, String lastName, String email, int phoneNumber,
                    LocalDate accountCreationDate, boolean subscriber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountCreationDate = accountCreationDate;
        this.subscriber = subscriber;
    }

    public Customer(long id, String firstName, String lastName, String email, int phoneNumber,
                    LocalDate accountCreationDate, boolean subscriber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountCreationDate = accountCreationDate;
        this.subscriber = subscriber;
    }

    public Customer(long id, String firstName, String lastName, String email, int phoneNumber,
                    LocalDate accountCreationDate, boolean subscriber, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountCreationDate = accountCreationDate;
        this.subscriber = subscriber;
        this.isActive = isActive;
    }
}
