package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.CustomerDto;
import com.crud.adventuretravel.exception.CustomerAlreadyExistsException;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.mapper.CustomerMapper;
import com.crud.adventuretravel.service.CustomerDBService;
import com.crud.adventuretravel.subscriber.NewAttractionNotifier;
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
class CustomerFacadeTest {

    @MockBean
    private CustomerDBService customerDBService;

    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private NewTourNotifier newTourNotifier;

    @MockBean
    private NewAttractionNotifier newAttractionNotifier;

    @Autowired
    private CustomerFacade customerFacade;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {

        customer = new Customer(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18), true);
        customerDto = new CustomerDto(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18), true);
    }

    @Test
    void shouldFetchCustomerList() {

        //Given
        List<Customer> customerList = List.of(
                new Customer(12L, "John", "Smith", "john@test.com", 123456,
                        LocalDate.of(2021, 12, 12), true),
                customer);

        List<CustomerDto> customerDtoList = List.of(
                new CustomerDto(12L, "John", "Smith", "john@test.com", 123456,
                        LocalDate.of(2021, 12, 12), true),
                customerDto);
        when(customerDBService.getAllCustomers()).thenReturn(customerList);
        when(customerMapper.mapToCustomerDtoList(customerList)).thenReturn(customerDtoList);

        //When
        List<CustomerDto> returnedCustomerDtoList = customerFacade.getCustomers();

        //Then
        assertEquals(2, returnedCustomerDtoList.size());
    }

    @Test
    void shouldFetchCustomerById() throws CustomerNotFoundException {

        //Given
        long customerId = 342L;
        when(customerDBService.getCustomerById(customerId)).thenReturn(customer);
        when(customerMapper.mapToCustomerDto(customer)).thenReturn(customerDto);

        //When
        CustomerDto returnedCustomerDto = customerFacade.getCustomerById(customerId);

        //Then
        assertEquals("Johnny", returnedCustomerDto.getFirstName());
    }

    @Test
    void shouldNotFetchCustomerById() throws CustomerNotFoundException {

        //Given
        Long customerId = 223L;
        when(customerDBService.getCustomerById(customerId)).thenThrow(CustomerNotFoundException.class);

        //When&Then
        assertThrows(CustomerNotFoundException.class, () -> customerFacade.getCustomerById(customerId));
    }

    @Test
    void shouldCreateCustomer() throws CustomerAlreadyExistsException {

        //Given
        when(customerMapper.mapToCustomer(customerDto)).thenReturn(customer);

        //When
        customerFacade.createCustomer(customerDto);

        //Then
        verify(newTourNotifier, times(1)).checkSubscriptionNewCustomer(customer);
        verify(newAttractionNotifier, times(1)).checkSubscriptionNewCustomer(customer);
        verify(customerDBService, times(1)).createCustomer(customer);
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        //Given
        when(customerMapper.mapToCustomer(customerDto)).thenReturn(customer);

        //When
        customerFacade.updateCustomer(customerDto);

        //Then
        verify(newTourNotifier, times(1)).checkSubscriptionUpdatedCustomer(customer);
        verify(newAttractionNotifier, times(1)).checkSubscriptionUpdatedCustomer(customer);
        verify(customerDBService, times(1)).updateCustomer(customer);
    }

    @Test
    void shouldDeleteCustomer() {

        //Given
        long customerId = 342L;

        //When
        customerFacade.deleteCustomer(customerId);

        //Then
        verify(customerDBService, times(1)).deleteCustomer(customerId);
    }
}