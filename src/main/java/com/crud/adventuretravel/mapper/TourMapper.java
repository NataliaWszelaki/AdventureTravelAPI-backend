package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.Tour;
import com.crud.adventuretravel.domain.TourDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourMapper {

    public TourDto mapToTourDto(Tour tour) {

        return new TourDto(
                tour.getId(),
                tour.getName(),
                tour.getCountry(),
                tour.getDescription(),
                tour.getStartDate(),
                tour.getEndDate(),
                tour.getStartLocation(),
                tour.getEndLocation(),
                tour.getPriceEuro(),
                tour.getPricePln()
        );
    }

    public Tour mapToTour(TourDto tourDto) {

        return new Tour(
                tourDto.getId(),
                tourDto.getName(),
                tourDto.getCountry(),
                tourDto.getDescription(),
                tourDto.getStartDate(),
                tourDto.getEndDate(),
                tourDto.getStartLocation(),
                tourDto.getEndLocation(),
                tourDto.getPriceEuro(),
                tourDto.getPricePln()
        );
    }

    public List<TourDto> mapToTourDtoList(List<Tour> tourList) {

        return tourList.stream()
                .map(this::mapToTourDto)
                .collect(Collectors.toList());
    }
}
