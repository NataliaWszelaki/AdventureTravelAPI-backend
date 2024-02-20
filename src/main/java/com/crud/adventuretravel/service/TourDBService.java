package com.crud.adventuretravel.service;

import com.crud.adventuretravel.domain.Tour;
import com.crud.adventuretravel.domain.TourDto;
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
            tour.setActive(true);
            tourRepository.save(tour);
        } else {
            throw new TourAlreadyExistsException();
        }
    }

    public void updateTour(Tour tour) throws TourNotFoundException {

        Tour searchedTour = tourRepository.findById(tour.getId()).orElse(null);
        if (searchedTour != null) {
            tour.setActive(true);
            tourRepository.save(tour);
        } else {
            throw new TourNotFoundException();
        }
    }

    public void updateTourDeactivate(TourDto tourDto) throws TourNotFoundException {

        Tour tour = tourRepository.findById(tourDto.getId()).orElse(null);
        if (tour != null) {
            tour.setActive(false);
            tourRepository.save(tour);
        } else {
            throw new TourNotFoundException();
        }
    }

    public void deleteTour(Long tourId) {

        tourRepository.deleteById(tourId);
    }
}
