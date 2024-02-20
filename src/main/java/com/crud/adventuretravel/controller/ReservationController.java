package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.ReservationDto;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.exception.ReservationNotFoundException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.facade.ReservationFacade;
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

    private final ReservationFacade reservationFacade;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getReservations() {

        return ResponseEntity.ok(reservationFacade.getReservations());
    }

    @GetMapping(value = "{reservationId}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long reservationId)
            throws ReservationNotFoundException {

        return ResponseEntity.ok(reservationFacade.getReservationById(reservationId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationDto reservationDto)
            throws TourNotFoundException, CustomerNotFoundException {

        reservationFacade.createReservation(reservationDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateReservation(@RequestBody ReservationDto reservationDto)
            throws TourNotFoundException, CustomerNotFoundException {

        reservationFacade.updateReservation(reservationDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/deactivate")
    public ResponseEntity<Void> updateReservationDeactivate(@RequestBody ReservationDto reservationDto)
            throws ReservationNotFoundException {

        reservationFacade.updateReservationDeactivate(reservationDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {

        reservationFacade.deleteReservation(reservationId);
        return ResponseEntity.ok().build();
    }
}
