package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.Tour;
import com.crud.adventuretravel.domain.TourDto;
import com.crud.adventuretravel.exception.TourAlreadyExistsException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.mapper.TourMapper;
import com.crud.adventuretravel.service.TourDBService;
import com.crud.adventuretravel.subscriber.NewTourNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TourFacade {

    private final TourDBService tourDBService;
    private final TourMapper tourMapper;
    private final NewTourNotifier newTourNotifier;

    public List<TourDto> getTours() {

        List<Tour> tourList = tourDBService.getAllTours();
        return tourMapper.mapToTourDtoList(tourList);
    }

    public TourDto getTourById(Long tourId) throws TourNotFoundException {

        Tour tour = tourDBService.getTourById(tourId);
        return tourMapper.mapToTourDto(tour);
    }

    public void createTour(TourDto tourDto) throws TourAlreadyExistsException {

        Tour tour = tourMapper.mapToTour(tourDto);
        newTourNotifier.notifyObservers();
        tourDBService.createTour(tour);
    }

    public void updateTour(TourDto tourDto) throws TourNotFoundException {

        Tour tour = tourMapper.mapToTour(tourDto);
        tourDBService.updateTour(tour);
    }

    public void deleteTour(long tourId) {

        tourDBService.deleteTour(tourId);
    }
}
