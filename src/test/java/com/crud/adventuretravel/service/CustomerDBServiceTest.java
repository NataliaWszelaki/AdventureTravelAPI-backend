package com.crud.adventuretravel.service;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.exception.CustomerAlreadyExistsException;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Transactional
@SpringBootTest
class CustomerDBServiceTest {

    @Autowired
    private CustomerDBService customerDBService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFetchAllCustomers() {

        //Given
        customerRepository.save(new Customer("Anna", "White", "awhite@test.test",
                123456789, LocalDate.of(2021, 1, 25), true));
        customerRepository.save(new Customer("Anna", "Black", "ablack@test.test",
                123456789, LocalDate.of(2021, 11, 15), true));

        //When
        List<Customer> customerList = customerDBService.getAllCustomers();

        //Then
        assertEquals(2, customerList.size());
    }

    @Test
    void shouldFetchCustomerById() throws CustomerNotFoundException {

        //Given
        Customer customer = new Customer(123L, "John", "Smith", "john.smith@test.test",
                123456789, LocalDate.of(2020, 12, 12), true);
        customerRepository.save(customer);

        //When
        Customer foundCustomer = customerDBService.getCustomerById(customer.getId());

        //Then
        assertEquals("John", foundCustomer.getFirstName());
    }

    @Test
    void shouldCreateCustomer() throws CustomerAlreadyExistsException {

        //Given
        Customer customer = new Customer("Michael", "Bear", "mb@test.test",
                123456789, LocalDate.of(2024, 1, 28), true);

        //When
        customerDBService.createCustomer(customer);
        Customer createdCustomer = customerRepository.findByEmail("mb@test.test");

        //Then
        assertEquals(customer.getFirstName(), createdCustomer.getFirstName());
        assertEquals(customer.getAccountCreationDate(), createdCustomer.getAccountCreationDate());
    }

    @Test
    void shouldUpdateCustomer() throws CustomerNotFoundException {

        //Given
        Customer customer = new Customer("Michael", "Bear", "mb@test.test",
                123456789, LocalDate.of(2024, 1, 28), true);
        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findByEmail("mb@test.test");
        long id = savedCustomer.getId();
        Customer customer1 = new Customer("Michael", "Bear-Braun", "mb@test.test",
                123456789, LocalDate.of(2024, 1, 28), true);
        customer1.setId(id);

        //When
        customerDBService.updateCustomer(customer1);
        Customer updatedCustomer = customerRepository.findByEmail("mb@test.test");

        //Then
        assertEquals("Bear-Braun", updatedCustomer.getLastName());
    }

    @Test
    void shouldDeleteCustomer() {

        //Given
        Customer customer = new Customer("Michael", "Bear", "mb@test.test",
                123456789, LocalDate.of(2024, 1, 28), true);
        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findByEmail("mb@test.test");
        long id = savedCustomer.getId();

        //When
        customerDBService.deleteCustomer(id);
        boolean isExisting = customerRepository.existsByEmail("mb@test.test");

        //Then
        assertFalse(isExisting);
    }
}