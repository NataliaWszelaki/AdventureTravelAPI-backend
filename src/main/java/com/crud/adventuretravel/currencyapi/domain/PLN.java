package com.crud.adventuretravel.currencyapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PLN {

    @JsonProperty("code")
    private String code;

    @JsonProperty("value")
    private double value;
}
