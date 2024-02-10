package com.crud.adventuretravel.subscriber;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.repository.AttractionRepository;
import com.crud.adventuretravel.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class NewAttractionNotifierTest {

    @MockBean
    private EmailService emailService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private AttractionRepository attractionRepository;

    private List<SubscriberObserver> subscriberObserverList;
    private NewAttractionNotifier newAttractionNotifier;
    private Customer customer;

    @BeforeEach
    void setUp() {
        subscriberObserverList = new ArrayList<>();
        newAttractionNotifier = new NewAttractionNotifier(subscriberObserverList, emailService, customerRepository, attractionRepository);
        customer = new Customer("John", "Brown", "john@test.pl", 123456789, LocalDate.now(), true);
    }

    @Test
    void shouldAddNewSubscriberToListSubscriptionNewCustomer() {

        //When
        newAttractionNotifier.checkSubscriptionNewCustomer(customer);

        //Then
        assertEquals(1, subscriberObserverList.size());
    }

    @Test
    void shouldAddNewSubscriberToListCheckSubscriptionUpdatedCustomer() {

        //When
        newAttractionNotifier.checkSubscriptionUpdatedCustomer(customer);

        //Then
        assertEquals(1, subscriberObserverList.size());
    }

    @Test
    void shouldRemoveSubscriberFromListCheckSubscriptionUpdatedCustomer() {

        // Given
        Subscriber subscriber = new Subscriber(customer.getFirstName(), customer.getEmail(), emailService);
        newAttractionNotifier.registerObserver(subscriber);

        //When
        Customer updatedCustomer = new Customer("John", "Brown", "john@test.pl", 123456789, LocalDate.now(), false);
        newAttractionNotifier.checkSubscriptionUpdatedCustomer(updatedCustomer);

        //Then
        assertEquals(0, subscriberObserverList.size());
    }

    @Test
    void shouldNotifyObserversNotEmptySubscriberObserverList() {

        //Given
        Subscriber subscriber = new Subscriber(customer.getFirstName(), customer.getEmail(), emailService);
        newAttractionNotifier.registerObserver(subscriber);

        //When
        newAttractionNotifier.notifyObservers();

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
        newAttractionNotifier.notifyObservers();

        //Then
        verify(emailService, times(1)).send(any());
    }
}