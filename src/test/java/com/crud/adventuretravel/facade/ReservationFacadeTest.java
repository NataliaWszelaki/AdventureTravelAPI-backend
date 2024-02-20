package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.*;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.exception.ReservationNotFoundException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.mapper.ReservationMapper;
import com.crud.adventuretravel.service.ReservationDBService;
import com.crud.adventuretravel.subscriber.ReservationStatusNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReservationFacadeTest {

    @MockBean
    private ReservationDBService reservationDBService;

    @MockBean
    private ReservationMapper reservationMapper;

    @MockBean
    private ReservationStatusNotifier reservationStatusNotifier;

    @Autowired
    private ReservationFacade reservationFacade;

    private Reservation reservation;
    private ReservationDto reservationDto;

    @BeforeEach
    void setUp() {

        Tour tour = new Tour(3L, "Italy-Tour", "Italy", "Italy-Tour description", LocalDate.of(2024, 5, 5),
                LocalDate.of(2024, 5, 17), "Rome", "Bari",
                2000);
        Customer customer = new Customer(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18), true);
        Attraction attraction = new Attraction(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30);
        Attraction attraction2 = new Attraction(8L, 234, "Sienna", "Wine tasting", "Vineyard",
                "Tasting Wine in a beautiful restaurant", "Private tour", 30);
        Set<Attraction> attractionSet = new HashSet<>();
        attractionSet.add(attraction);
        attractionSet.add(attraction2);

        reservation = new Reservation(
                7L,
                tour,
                customer,
                attractionSet,
                LocalDate.of(2024, 2, 1),
                PaymentStatus.PENDING,
                ReservationStatus.PENDING);

        TourDto tourDto = new TourDto();
        tourDto.setId(3L);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(342L);
        Set<Long> attractionLongSet = new HashSet<>();
        attractionLongSet.add(5L);
        attractionLongSet.add(8L);

        reservationDto = new ReservationDto(
                7L,
                tourDto.getId(),
                customerDto.getId(),
                attractionLongSet,
                LocalDate.of(2024, 2, 1),
                PaymentStatus.PENDING,
                ReservationStatus.PENDING);
    }

    @Test
    void shouldFetchReservationList() {

        //Given
        Tour tour2 = new Tour(3L, "Italy-Tour", "Italy", "Italy-Tour description", LocalDate.of(2024, 5, 5),
                LocalDate.of(2024, 5, 17), "Rome", "Bari",
                2000);
        Customer customer2 = new Customer(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18), true);
        Attraction attraction3 = new Attraction(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30);
        Attraction attraction4 = new Attraction(8L, 234, "Sienna", "Wine tasting", "Vineyard",
                "Tasting Wine in a beautiful restaurant", "Private tour", 30);
        Set<Attraction> attractionSet2 = new HashSet<>();
        attractionSet2.add(attraction3);
        attractionSet2.add(attraction4);

        Reservation reservation2 = new Reservation(
                9L,
                tour2,
                customer2,
                attractionSet2,
                LocalDate.of(2024, 1, 11),
                PaymentStatus.CONFIRMED,
                ReservationStatus.CONFIRMED,
                true
        );

        TourDto tourDto2 = new TourDto();
        tourDto2.setId(3L);
        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setId(342L);
        Set<Long> attractionLongSet2 = new HashSet<>();
        attractionLongSet2.add(5L);
        attractionLongSet2.add(8L);
        attractionLongSet2.add(17L);

        ReservationDto reservationDto2 = new ReservationDto(
                9L,
                tourDto2.getId(),
                customerDto2.getId(),
                attractionLongSet2,
                LocalDate.of(2024, 1, 11),
                PaymentStatus.CONFIRMED,
                ReservationStatus.CONFIRMED);

        List<Reservation> reservationList = new ArrayList<>();
        reservation.setActive(true);
        reservationList.add(reservation);
        reservationList.add(reservation2);

        List<ReservationDto> reservationDtoList = new ArrayList<>();
        reservationDtoList.add(reservationDto);
        reservationDtoList.add(reservationDto2);
        when(reservationDBService.getAllReservations()).thenReturn(reservationList);
        when(reservationMapper.mapToReservationDtoList(reservationList)).thenReturn(reservationDtoList);

        //When
        List<ReservationDto> returnedReservationDtoList = reservationFacade.getReservations();

        //Then
        assertEquals(2, returnedReservationDtoList.size());
    }

    @Test
    void shouldFetchReservationById() throws ReservationNotFoundException {

        //Given
        long reservationId = 342L;
        when(reservationDBService.getReservationById(reservationId)).thenReturn(reservation);
        when(reservationMapper.mapToReservationDto(reservation)).thenReturn(reservationDto);

        //When
        ReservationDto returnedReservationDto = reservationFacade.getReservationById(reservationId);

        //Then
        assertEquals(ReservationStatus.PENDING, returnedReservationDto.getReservationStatus());
    }

    @Test
    void shouldNotFetchReservationById() throws ReservationNotFoundException {

        //Given
        Long reservationId = 223L;
        when(reservationDBService.getReservationById(reservationId)).thenThrow(ReservationNotFoundException.class);

        //When&Then
        assertThrows(ReservationNotFoundException.class, () -> reservationFacade.getReservationById(reservationId));
    }

    @Test
    void shouldCreateReservation() throws TourNotFoundException, CustomerNotFoundException {

        //Given
        when(reservationMapper.mapToReservation(reservationDto)).thenReturn(reservation);

        //When
        reservationFacade.createReservation(reservationDto);

        //Then
        verify(reservationStatusNotifier, times(1)).fetchReservation(reservation);
        verify(reservationDBService, times(1)).createReservation(reservation);
    }

    @Test
    void shouldUpdateReservation() throws Exception {

        //Given
        when(reservationMapper.mapToReservation(reservationDto)).thenReturn(reservation);

        //When
        reservationFacade.updateReservation(reservationDto);

        //Then
        verify(reservationStatusNotifier, times(1)).fetchReservation(reservation);
        verify(reservationDBService, times(1)).updateReservation(reservation);
    }

    @Test
    void shouldUpdateReservationDeactivate() throws Exception {

        //When
        reservationFacade.updateReservationDeactivate(reservationDto);

        //Then
        verify(reservationDBService, times(1)).updateReservationDeactivate(reservationDto);
    }

    @Test
    void shouldDeleteReservation() {

        //Given
        long reservationId = 342L;

        //When
        reservationFacade.deleteReservation(reservationId);

        //Then
        verify(reservationDBService, times(1)).deleteReservation(reservationId);
    }
}