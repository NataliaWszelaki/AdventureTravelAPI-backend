package com.crud.adventuretravel.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
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

    @Column(name = "ACTIVE")
    private boolean isActive;

    public Reservation(long id, Tour tour, Customer customer, Set<Attraction> attractionList, LocalDate reservationDate,
                       PaymentStatus paymentStatus, ReservationStatus reservationStatus) {
        this.id = id;
        this.tour = tour;
        this.customer = customer;
        this.attractionList = attractionList;
        this.reservationDate = reservationDate;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
    }
}
