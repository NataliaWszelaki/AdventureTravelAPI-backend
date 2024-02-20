package com.crud.adventuretravel.subscriber;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.Reservation;
import com.crud.adventuretravel.domain.ReservationStatus;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationStatusNotifierTest {

    @Mock
    private EmailService emailService;

    @Mock
    private ReservationRepository reservationRepository;

    private Map<Long, SubscriberObserver> subscriberObserverMap;
    private Reservation reservation;
    private ReservationStatusNotifier reservationStatusNotifier;
    private Customer customer;

    @BeforeEach
    void setUp() {

        subscriberObserverMap = new HashMap<>();
        reservationStatusNotifier = new ReservationStatusNotifier(emailService, subscriberObserverMap, reservationRepository, reservation);
        customer = new Customer("John", "Brown", "john@test.pl", 123456789, LocalDate.now(), true);
        reservation = new Reservation();
        reservation.setId(12L);
        reservation.setCustomer(customer);
    }

    @Test
    void shouldFetchReservationAndAddSubscriberToObserverMap() {

        //Given
        reservation.setReservationStatus(ReservationStatus.NEW);

        //When
        reservationStatusNotifier.fetchReservation(reservation);

        //Then
        assertTrue(subscriberObserverMap.containsKey(reservation.getId()));
        assertEquals(1, subscriberObserverMap.size());
        verify(emailService, times(1)).send(any());
    }

    @Test
    void shouldFetchReservationAndRemoveSubscriberFromObserverMapStatusCanceled() {

        //Given
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        subscriberObserverMap.put(reservation.getId(), new Subscriber(customer.getFirstName(), customer.getEmail(), emailService));

        //When
        reservationStatusNotifier.fetchReservation(reservation);

        //Then
        assertFalse(subscriberObserverMap.containsKey(reservation.getId()));
        assertEquals(0, subscriberObserverMap.size());
        verify(emailService, times(1)).send(any());
    }

    @Test
    void shouldFetchReservationAndRemoveSubscriberFromObserverMapStatusClosed() {

        //Given
        reservation.setReservationStatus(ReservationStatus.CLOSED);
        subscriberObserverMap.put(reservation.getId(), new Subscriber(customer.getFirstName(), customer.getEmail(), emailService));

        //When
        reservationStatusNotifier.fetchReservation(reservation);

        //Then
        assertFalse(subscriberObserverMap.containsKey(reservation.getId()));
        assertEquals(0, subscriberObserverMap.size());
        verify(emailService, times(1)).send(any());
    }

    @Test
    void shouldFetchReservationAndNotifySubscriberObserverStatusPending() {

        //Given
        reservation.setReservationStatus(ReservationStatus.PENDING);
        subscriberObserverMap.put(reservation.getId(), new Subscriber(customer.getFirstName(), customer.getEmail(), emailService));
        subscriberObserverMap.put(22L, new Subscriber("Eliza", "test@test.pl", emailService));

        //When
        reservationStatusNotifier.fetchReservation(reservation);

        //Then
        assertTrue(subscriberObserverMap.containsKey(reservation.getId()));
        assertEquals(2, subscriberObserverMap.size());
        verify(emailService, times(1)).send(any());
    }

    @Test
    void shouldNotifyObserversNotEmptySubscriberObserverList() {

        //Given
        subscriberObserverMap.put(12L, new Subscriber(customer.getFirstName(), customer.getEmail(), emailService));
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservationStatusNotifier.currentReservation = reservation;

        //When
        reservationStatusNotifier.notifyObservers();

        //Then
        verify(emailService, times(1)).send(any());
    }

    @Test
    void shouldNotifyObserversEmptySubscriberObserverList() {

        //Given
        reservation.setReservationStatus(ReservationStatus.PENDING);
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation);
        when(reservationRepository.findAll()).thenReturn(reservationList);
        reservationStatusNotifier.currentReservation = reservation;

        //When
        reservationStatusNotifier.notifyObservers();

        //Then
        verify(emailService, times(1)).send(any());
    }
}

