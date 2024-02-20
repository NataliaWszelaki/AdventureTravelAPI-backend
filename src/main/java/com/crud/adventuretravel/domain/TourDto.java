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
public class TourDto {

    private long id;
    private String name;
    private String country;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startLocation;
    private String endLocation;
    private int priceEuro;
}
