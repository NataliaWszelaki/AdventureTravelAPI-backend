package com.crud.adventuretravel.service;

import com.crud.adventuretravel.domain.Tour;
import com.crud.adventuretravel.exception.TourAlreadyExistsException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourDBService {

    private final TourRepository tourRepository;

    public List<Tour> getAllTours() {

        return tourRepository.findAll();
    }

    public Tour getTourById(Long tourId) throws TourNotFoundException {

        return tourRepository.findById(tourId).orElseThrow(TourNotFoundException::new);
    }

    public void createTour(Tour tour) throws TourAlreadyExistsException {

        boolean isExisting = tourRepository.existsByName(tour.getName());
        if (!isExisting) {
            tourRepository.save(tour);
        } else {
            throw new TourAlreadyExistsException();
        }
    }

    public void updateTour(Tour tour) throws TourNotFoundException {

        boolean isExisting = tourRepository.existsByName(tour.getName());
        if (isExisting) {
            tourRepository.save(tour);
        } else {
            throw new TourNotFoundException();
        }
    }

    public void deleteTour(Long tourId) {

        tourRepository.deleteById(tourId);
    }
}
