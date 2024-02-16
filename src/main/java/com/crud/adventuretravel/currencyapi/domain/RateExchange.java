package com.crud.adventuretravel.currencyapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RATES_EXCHANGE")
public class RateExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "EURO_TO_PLN")
    private double pln;

    @Column(name = "RATE_EXCHANGE_DATE")
    private LocalDate rateExchangeDate;

    public RateExchange(double pln, LocalDate rateExchangeDate) {
        this.pln = pln;
        this.rateExchangeDate = rateExchangeDate;
    }
}
