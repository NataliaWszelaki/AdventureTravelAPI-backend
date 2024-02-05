package com.crud.adventuretravel.service;

import com.crud.adventuretravel.domain.Reservation;
import com.crud.adventuretravel.exception.ReservationNotFoundException;
import com.crud.adventuretravel.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationDBService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {

        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long reservationId) throws ReservationNotFoundException {

        return reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
    }

    public void createReservation(Reservation reservation) {

        reservationRepository.save(reservation);
    }

    public void updateReservation(Reservation reservation) {

        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long reservationId) {

        reservationRepository.deleteById(reservationId);
    }
}
