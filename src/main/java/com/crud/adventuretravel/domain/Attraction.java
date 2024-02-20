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

    @Column(name = "LOCATION_ID")
    private int location_id;

    @Column(name = "CITY")
    private String city;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PRICE_EURO")
    private int priceEuro;

    @Column(name = "ACTIVE")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            },
            mappedBy = "attractionList")
    private Set<Reservation> reservationList = new HashSet<>();

    public Attraction(long id, int location_id, String city, String name, String description, String category,
                      String title, int priceEuro) {
        this.id = id;
        this.location_id = location_id;
        this.city = city;
        this.name = name;
        this.description = description;
        this.category = category;
        this.title = title;
        this.priceEuro = priceEuro;
    }

    public Attraction(long id, int location_id, String city, String name, String description, String category,
                      String title, int priceEuro, boolean isActive) {
        this.id = id;
        this.location_id = location_id;
        this.city = city;
        this.name = name;
        this.description = description;
        this.category = category;
        this.title = title;
        this.priceEuro = priceEuro;
        this.isActive = isActive;
    }
}




