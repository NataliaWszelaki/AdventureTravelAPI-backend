package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.TourDto;
import com.crud.adventuretravel.exception.TourAlreadyExistsException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.facade.TourFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tours")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TourController {

    private final TourFacade tourFacade;

    @GetMapping
    public ResponseEntity<List<TourDto>> getTours() {

        return ResponseEntity.ok(tourFacade.getTours());
    }

    @GetMapping(value = "/{tourId}")
    public ResponseEntity<TourDto> getTourById(@PathVariable Long tourId) throws TourNotFoundException {

        return ResponseEntity.ok(tourFacade.getTourById(tourId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTour(@RequestBody TourDto tourDto) throws TourAlreadyExistsException {

        tourFacade.createTour(tourDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTour(@RequestBody TourDto tourDto) throws TourNotFoundException {

        tourFacade.updateTour(tourDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/deactivate")
    public ResponseEntity<Void> updateTourDeactivate(@RequestBody TourDto tourDto) throws TourNotFoundException {

        tourFacade.updateTourDeactivate(tourDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{tourId}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long tourId) {

        tourFacade.deleteTour(tourId);
        return ResponseEntity.ok().build();
    }
}