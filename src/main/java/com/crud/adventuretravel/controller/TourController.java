package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.Tour;
import com.crud.adventuretravel.domain.TourDto;
import com.crud.adventuretravel.exception.TourAlreadyExistsException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.mapper.TourMapper;
import com.crud.adventuretravel.service.TourDBService;
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

    private final TourDBService tourDBService;
    private final TourMapper tourMapper;

    @GetMapping
    public List<TourDto> getTours() {

        List<Tour> tourList = tourDBService.getAllTours();
        return tourMapper.mapToTourDtoList(tourList);
    }

    @GetMapping(value = "{tourId}")
    public ResponseEntity<TourDto> getTourById(@PathVariable Long tourId) throws TourNotFoundException {

        Tour tour = tourDBService.getTourById(tourId);
        return ResponseEntity.ok(tourMapper.mapToTourDto(tour));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTour(@RequestBody TourDto tourDto) throws TourNotFoundException {

        Tour tour = tourMapper.mapToTour(tourDto);
        tourDBService.createTour(tour);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTour(@RequestBody TourDto tourDto) throws TourAlreadyExistsException {

        Tour tour = tourMapper.mapToTour(tourDto);
        tourDBService.updateTour(tour);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{tourId}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long tourId) {

        tourDBService.deleteTour(tourId);
        return ResponseEntity.ok().build();
    }
}