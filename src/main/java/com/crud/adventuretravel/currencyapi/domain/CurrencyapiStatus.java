package com.crud.adventuretravel.currencyapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CURRENCYAPI_STATUS")
public class CurrencyapiStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TOTAL")
    private int total;

    @Column(name = "USED")
    private int used;

    @Column(name = "REMAINING")
    private int remaining;

    @Column(name = "CHECK_STATUS_DATE")
    private LocalDate checkStatusDate;

    public CurrencyapiStatus(int total, int used, int remaining, LocalDate checkStatusDate) {
        this.total = total;
        this.used = used;
        this.remaining = remaining;
        this.checkStatusDate = checkStatusDate;
    }
}
