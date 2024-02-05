package com.crud.adventuretravel.domain;

import jakarta.persistence.*;
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
@Table(name = "TOURS")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "START_LOCATION")
    private String startLocation;

    @Column(name = "END_LOCATION")
    private String endLocation;

    @Column(name = "PRICE_EURO")
    private int priceEuro;

    @Column(name = "PRICE_PLN")
    private int pricePln;

    @OneToMany(
            targetEntity = Reservation.class,
            mappedBy = "tour",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Reservation> reservationTourList = new ArrayList<>();

    public Tour(long id, String name, String country, String description, LocalDate startDate, LocalDate endDate,
                String startLocation, String endLocation, int priceEuro, int pricePln) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.priceEuro = priceEuro;
        this.pricePln = pricePln;
    }
}
