package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.Reservation;
import com.crud.adventuretravel.domain.ReservationDto;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.exception.ReservationNotFoundException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.mapper.ReservationMapper;
import com.crud.adventuretravel.service.ReservationDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reservations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservationController {

    private final ReservationDBService reservationDBService;
    private final ReservationMapper reservationMapper;

    @GetMapping
    public List<ReservationDto> getReservations() {

        List<Reservation> reservationList = reservationDBService.getAllReservations();
        return reservationMapper.mapToReservationDtoList(reservationList);
    }

    @GetMapping(value = "{reservationId}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long reservationId)
            throws ReservationNotFoundException {

        Reservation reservation = reservationDBService.getReservationById(reservationId);
        return ResponseEntity.ok(reservationMapper.mapToReservationDto(reservation));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationDto reservationDto)
            throws TourNotFoundException, CustomerNotFoundException {

        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationDBService.createReservation(reservation);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateReservation(@RequestBody ReservationDto reservationDto)
            throws TourNotFoundException, CustomerNotFoundException {

        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationDBService.updateReservation(reservation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {

        reservationDBService.deleteReservation(reservationId);
        return ResponseEntity.ok().build();
    }
}
