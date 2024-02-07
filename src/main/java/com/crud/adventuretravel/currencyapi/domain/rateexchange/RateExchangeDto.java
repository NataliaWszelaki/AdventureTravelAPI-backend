package com.crud.adventuretravel.currencyapi.domain.rateexchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateExchangeDto {

    private long id;
    private double pln;
    private LocalDate rateExchangeDate;
}
