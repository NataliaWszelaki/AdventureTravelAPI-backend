package com.crud.adventuretravel.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ATTRACTIONS")
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE_EURO")
    private int priceEuro;

    @Column(name = "PRICE_PLN")
    private int pricePln;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "attractionList")
    private Set<Reservation> reservationList = new HashSet<>();

    public Attraction(long id, String name, String city, String description, int priceEuro, int pricePln) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.description = description;
        this.priceEuro = priceEuro;
        this.pricePln = pricePln;
    }
}
