package com.crud.adventuretravel.currencyapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    @JsonProperty("PLN")
    private PLN pln;
}
