package com.crud.adventuretravel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AttractionDto {

    long id;
    private String name;
    private String city;
    private String description;
    private int priceEuro;
    private int pricePln;
}
