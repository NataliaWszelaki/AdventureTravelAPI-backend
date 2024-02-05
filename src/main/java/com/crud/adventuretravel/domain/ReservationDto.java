package com.crud.adventuretravel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ReservationDto {

    private long id;
    private long tourId;
    private long customerId;
    private Set<Long> attractionLongSet;
    private LocalDate reservationDate;
    private PaymentStatus paymentStatus;
    private ReservationStatus reservationStatus;
}
