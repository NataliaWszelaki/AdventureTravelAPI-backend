package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.*;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.repository.AttractionRepository;
import com.crud.adventuretravel.repository.CustomerRepository;
import com.crud.adventuretravel.repository.TourRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class ReservationMapperTest {

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    private Reservation reservation;
    private Tour tour;
    private Customer customer;
    private Attraction attraction;
    private Attraction attraction2;

    @BeforeEach
    void setUp() {
        tour = new Tour(3L, "Italy-Tour", "Italy", "Italy-Tour description", LocalDate.of(2024, 5, 5),
                LocalDate.of(2024, 5, 17), "Rome", "Bari",
                2000);
        customer = new Customer(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18), true);
        attraction = new Attraction(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30);
        attraction2 = new Attraction(8L, 234, "Sienna", "Wine tasting", "Vineyard",
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
    }


    @Test
    void shouldMapToReservationDto() {

        //When
        ReservationDto mappedReservationDto = reservationMapper.mapToReservationDto(reservation);

        //Then
        assertEquals(7L, mappedReservationDto.getId());
        assertEquals(2, mappedReservationDto.getAttractionLongSet().size());
        assertEquals(LocalDate.of(2024, 2, 1), mappedReservationDto.getReservationDate());
        assertEquals(PaymentStatus.PENDING, mappedReservationDto.getPaymentStatus());
        assertEquals(ReservationStatus.PENDING, mappedReservationDto.getReservationStatus());
    }

    @Test
    void shouldMapToReservation() {

        //Given
        tourRepository.save(tour);
        Tour savedTour = tourRepository.findByName("Italy-Tour");
        long savedTourId = savedTour.getId();
        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findByEmail("black@test.com");
        long savedCustomerId = savedCustomer.getId();
        attractionRepository.save(attraction);
        Attraction savedAttraction = attractionRepository.findByName("Isola del Giglio");
        long savedAdditionalAttractionId = savedAttraction.getId();
        attractionRepository.save(attraction2);
        Attraction savedAttraction2 = attractionRepository.findByName("Wine tasting");
        long savedAdditionalAttractionId2 = savedAttraction2.getId();

        TourDto tourDto = new TourDto();
        tourDto.setId(savedTourId);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(savedCustomerId);

        Set<Long> attractionLongSet = new HashSet<>();
        attractionLongSet.add(savedAdditionalAttractionId);
        attractionLongSet.add(savedAdditionalAttractionId2);

        ReservationDto reservationDto = new ReservationDto(
                8L,
                tourDto.getId(),
                customerDto.getId(),
                attractionLongSet,
                LocalDate.of(2024, 2, 1),
                PaymentStatus.PENDING,
                ReservationStatus.PENDING);

        //When
        Reservation mappedReservation;
        try {
            mappedReservation = reservationMapper.mapToReservation(reservationDto);
        } catch (TourNotFoundException | CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Then
        assertEquals(8L, mappedReservation.getId());
        assertEquals(savedTour.getId(), mappedReservation.getTour().getId());
        assertEquals(savedCustomer.getId(), mappedReservation.getCustomer().getId());
        assertEquals(2, mappedReservation.getAttractionList().size());
        assertEquals(LocalDate.of(2024, 2, 1), mappedReservation.getReservationDate());
        assertEquals(PaymentStatus.PENDING, mappedReservation.getPaymentStatus());
        assertEquals(ReservationStatus.PENDING, mappedReservation.getReservationStatus());
    }

    @Test
    void shouldMapToReservationDtoList() {

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
                ReservationStatus.CONFIRMED);

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation);
        reservationList.add(reservation2);

        //When
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservationList);

        //Then
        assertEquals(2, reservationDtoList.size());
        assertEquals(9L, reservationDtoList.get(1).getId());
        assertEquals(2, reservationDtoList.get(0).getAttractionLongSet().size());
    }
}
