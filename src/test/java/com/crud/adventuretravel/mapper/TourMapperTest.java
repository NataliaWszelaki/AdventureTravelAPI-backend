package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.Tour;
import com.crud.adventuretravel.domain.TourDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TourMapperTest {

    @Autowired
    private TourMapper tourMapper;

    @Test
    void shouldMapToTourDto() {

        //Given
        Tour tour = new Tour(5L, "Costa Blanca in Spring", "Spain", "Sightseeing",
                LocalDate.of(2024, 4, 23), LocalDate.of(2024, 5, 8),
                "Alicante", "Alicante", 3000, 15000);

        //When
        TourDto mappedTourDto = tourMapper.mapToTourDto(tour);

        //Then
        assertEquals(5, mappedTourDto.getId());
        assertEquals("Costa Blanca in Spring", mappedTourDto.getName());
        assertEquals("Spain", mappedTourDto.getCountry());
        assertEquals("Sightseeing", mappedTourDto.getDescription());
        assertEquals(LocalDate.of(2024, 4, 23), mappedTourDto.getStartDate());
        assertEquals(LocalDate.of(2024, 5, 8), mappedTourDto.getEndDate());
        assertEquals("Alicante", mappedTourDto.getStartLocation());
        assertEquals("Alicante", mappedTourDto.getEndLocation());
        assertEquals(3000, mappedTourDto.getPriceEuro());
        assertEquals(15000, mappedTourDto.getPricePln());
    }

    @Test
    void shouldMapToTour() {

        //Given
        TourDto tourDto = new TourDto(7L, "Costa Blanca in Spring", "Spain", "Sightseeing",
                LocalDate.of(2024, 4, 23), LocalDate.of(2024, 5, 8),
                "Alicante", "Alicante", 3000, 15000);

        //When
        Tour mappedTour = tourMapper.mapToTour(tourDto);

        //Then
        assertEquals(7, mappedTour.getId());
        assertEquals("Costa Blanca in Spring", mappedTour.getName());
        assertEquals("Spain", mappedTour.getCountry());
        assertEquals("Sightseeing", mappedTour.getDescription());
        assertEquals(LocalDate.of(2024, 4, 23), mappedTour.getStartDate());
        assertEquals(LocalDate.of(2024, 5, 8), mappedTour.getEndDate());
        assertEquals("Alicante", mappedTour.getStartLocation());
        assertEquals("Alicante", mappedTour.getEndLocation());
        assertEquals(3000, mappedTour.getPriceEuro());
        assertEquals(15000, mappedTour.getPricePln());
    }

    @Test
    void shouldMapToTourDtoList() {

        //Given
        List<Tour> tourList = List.of(
                new Tour(5L, "Costa Blanca in Spring", "Spain", "Sightseeing",
                        LocalDate.of(2024, 4, 23), LocalDate.of(2024, 5, 8),
                        "Alicante", "Alicante", 3000, 15000),
                new Tour(8L, "German castles", "Germany", "Castles",
                        LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 27),
                        "Frankfurt am Main", "Frankfurt am Main", 1500, 7000));

        //When
        List<TourDto> tourDtoList = tourMapper.mapToTourDtoList(tourList);

        //Then
        assertEquals(2, tourDtoList.size());
        assertEquals(8L, tourDtoList.get(1).getId());
        assertEquals("Costa Blanca in Spring", tourDtoList.get(0).getName());
        assertEquals("Spain", tourDtoList.get(0).getCountry());
        assertEquals("Sightseeing", tourDtoList.get(0).getDescription());
        assertEquals(LocalDate.of(2024, 4, 23), tourDtoList.get(0).getStartDate());
        assertEquals(LocalDate.of(2024, 5, 8), tourDtoList.get(0).getEndDate());
        assertEquals("Frankfurt am Main", tourDtoList.get(1).getStartLocation());
        assertEquals("Frankfurt am Main", tourDtoList.get(1).getEndLocation());
        assertEquals(1500, tourDtoList.get(1).getPriceEuro());
        assertEquals(7000, tourDtoList.get(1).getPricePln());
    }
}