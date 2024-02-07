package com.crud.adventuretravel.currencyapi.domain.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthDto {

    @JsonProperty("total")
    private int total;

    @JsonProperty("used")
    private int used;

    @JsonProperty("remaining")
    private int remaining;
}
