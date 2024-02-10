package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.Tour;
import com.crud.adventuretravel.domain.TourDto;
import com.crud.adventuretravel.exception.TourAlreadyExistsException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.mapper.TourMapper;
import com.crud.adventuretravel.service.TourDBService;
import com.crud.adventuretravel.subscriber.NewTourNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TourFacadeTest {

    @MockBean
    private TourDBService tourDBService;

    @MockBean
    private TourMapper tourMapper;

    @MockBean
    private NewTourNotifier newTourNotifier;

    @Autowired
    private TourFacade tourFacade;

    private Tour tour;
    private TourDto tourDto;

    @BeforeEach
    void setUp() {

        tour = new Tour(5L, "Costa Blanca in Spring", "Spain", "Sightseeing",
                LocalDate.of(2024,4,23), LocalDate.of(2024,5,8),
                "Alicante", "Alicante", 3000, 15000);
        tourDto = new TourDto(5L, "Costa Blanca in Spring", "Spain", "Sightseeing",
                LocalDate.of(2024,4,23), LocalDate.of(2024,5,8),
                "Alicante", "Alicante", 3000, 15000);
    }

    @Test
    void shouldFetchTourList() {

        //Given
        List<Tour> tourList = List.of(
                tour,
                new Tour(8L, "German castles", "Germany", "Castles", LocalDate.of(2024,5,13),
                        LocalDate.of(2024,5,27), "Frankfurt am Main", "Frankfurt am Main",1500, 7000));
        List<TourDto> tourDtoList = List.of(
                tourDto,
                new TourDto(8L, "German castles", "Germany", "Castles", LocalDate.of(2024,5,13),
                        LocalDate.of(2024,5,27), "Frankfurt am Main", "Frankfurt am Main",1500, 7000));
        when(tourDBService.getAllTours()).thenReturn(tourList);
        when(tourMapper.mapToTourDtoList(tourList)).thenReturn(tourDtoList);

        //When
        List<TourDto> returnedTourDtoList = tourFacade.getTours();

        //Then
        assertEquals(2, returnedTourDtoList.size());
    }

    @Test
    void shouldFetchTourById() throws TourNotFoundException {

        //Given
        long tourId = 5L;
        when(tourDBService.getTourById(tourId)).thenReturn(tour);
        when(tourMapper.mapToTourDto(tour)).thenReturn(tourDto);

        //When
        TourDto returnedTourDto = tourFacade.getTourById(tourId);

        //Then
        assertEquals("Costa Blanca in Spring", returnedTourDto.getName());
    }

    @Test
    void shouldNotFetchTourById() throws TourNotFoundException {

        //Given
        Long tourId = 223L;
        when(tourDBService.getTourById(tourId)).thenThrow(TourNotFoundException.class);

        //When&Then
        assertThrows(TourNotFoundException.class, () -> tourFacade.getTourById(tourId));
    }

    @Test
    void shouldCreateTour() throws TourAlreadyExistsException {

        //Given
        when(tourMapper.mapToTour(tourDto)).thenReturn(tour);

        //When
        tourFacade.createTour(tourDto);

        //Then
        verify(newTourNotifier, times(1)).notifyObservers();
        verify(tourDBService, times(1)).createTour(tour);
    }

    @Test
    void shouldUpdateTour() throws Exception {

        //Given
        when(tourMapper.mapToTour(tourDto)).thenReturn(tour);

        //When
        tourFacade.updateTour(tourDto);

        //Then
        verify(tourDBService, times(1)).updateTour(tour);
    }

    @Test
    void shouldDeleteTour() {

        //Given
        long tourId = 5L;

        //When
        tourFacade.deleteTour(tourId);

        //Then
        verify(tourDBService, times(1)).deleteTour(tourId);
    }
}