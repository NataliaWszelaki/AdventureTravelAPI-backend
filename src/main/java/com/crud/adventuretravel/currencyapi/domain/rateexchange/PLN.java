package com.crud.adventuretravel.currencyapi.domain.rateexchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PLN {

    @JsonProperty("code")
    private String code;

    @JsonProperty("value")
    private double value;

}