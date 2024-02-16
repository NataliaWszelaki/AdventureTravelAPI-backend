package com.crud.adventuretravel.currencyapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Month {

    @JsonProperty("total")
    private int total;

    @JsonProperty("used")
    private int used;

    @JsonProperty("remaining")
    private int remaining;
}
