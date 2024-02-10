package com.crud.adventuretravel.subscriber;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.repository.CustomerRepository;
import com.crud.adventuretravel.repository.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewTourNotifierTest {

    @Mock
    private EmailService emailService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TourRepository tourRepository;

    private List<SubscriberObserver> subscriberObserverList;
    private NewTourNotifier newTourNotifier;
    private Customer customer;

    @BeforeEach
    void setUp() {
        subscriberObserverList = new ArrayList<>();
        newTourNotifier = new NewTourNotifier(subscriberObserverList, emailService, customerRepository, tourRepository);
        customer = new Customer("John", "Brown", "john@test.pl", 123456789, LocalDate.now(), true);
    }

    @Test
    void shouldAddNewSubscriberToListSubscriptionNewCustomer() {

        //When
        newTourNotifier.checkSubscriptionNewCustomer(customer);

        //Then
        assertEquals(1, subscriberObserverList.size());
    }

    @Test
    void shouldAddNewSubscriberToListCheckSubscriptionUpdatedCustomer() {

        //When
        newTourNotifier.checkSubscriptionUpdatedCustomer(customer);

        //Then
        assertEquals(1, subscriberObserverList.size());
    }

    @Test
    void shouldRemoveSubscriberFromListCheckSubscriptionUpdatedCustomer() {

        // Given
        Subscriber subscriber = new Subscriber(customer.getFirstName(), customer.getEmail(), emailService);
        newTourNotifier.registerObserver(subscriber);

        //When
        Customer updatedCustomer = new Customer("John", "Brown", "john@test.pl", 123456789, LocalDate.now(), false);
        newTourNotifier.checkSubscriptionUpdatedCustomer(updatedCustomer);

        //Then
        assertEquals(0, subscriberObserverList.size());
    }

    @Test
    void shouldNotifyObserversNotEmptySubscriberObserverList() {

        //Given
        Subscriber subscriber = new Subscriber(customer.getFirstName(), customer.getEmail(), emailService);
        newTourNotifier.registerObserver(subscriber);

        //When
        newTourNotifier.notifyObservers();

        //Then
        verify(emailService, times(1)).send(any());
    }

    @Test
    void shouldNotifyObserversEmptySubscriberObserverList() {

        //Given
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findAllBySubscriber(true)).thenReturn(customerList);

        //When
        newTourNotifier.notifyObservers();

        //Then
        verify(emailService, times(1)).send(any());
    }
}