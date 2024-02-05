package com.crud.adventuretravel.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_RESERVATION_ATTRACTION",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ATTRACTION_ID")
    )
    private Set<Attraction> attractionList = new HashSet<>();

    @Column(name = "RESERVATION_DATE")
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "RESERVATION_STATUS")
    private ReservationStatus reservationStatus;
}
