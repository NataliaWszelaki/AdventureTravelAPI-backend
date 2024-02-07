package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.CustomerDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class CustomerMapperTest {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void displayBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Test
    void shouldMapToCustomerDto() {

        //Given
        Customer customer = new Customer(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18));

        //When
        CustomerDto mappedCustomerDto = customerMapper.mapToCustomerDto(customer);

        //Then
        assertEquals(342, mappedCustomerDto.getId());
        assertEquals("Johnny", mappedCustomerDto.getFirstName());
        assertEquals(LocalDate.of(2023, 2, 18), mappedCustomerDto.getAccountCreationDate());
    }

    @Test
    void shouldMapToCustomer() {

        //Given
        CustomerDto customerDto = new CustomerDto(312L, "John", "White",
                "white@test.com", 11223456, LocalDate.of(2013, 12, 18));

        //When
        Customer mappedCustomer = customerMapper.mapToCustomer(customerDto);

        //Then
        assertEquals(312, mappedCustomer.getId());
        assertEquals("White", mappedCustomer.getLastName());
        assertEquals("white@test.com", mappedCustomer.getEmail());
    }

    @Test
    void shouldMapToCustomerDtoList() {

        //Given
        List<Customer> customerList = List.of(
                new Customer(12L, "John", "Smith", "john@test.com", 123456, LocalDate.of(2021, 12, 12)),
                new Customer(132L, "Johnny", "Black",
                        "black@test.com", 223456, LocalDate.of(2023, 2, 18)));

        //When
        List<CustomerDto> customerDtoList = customerMapper.mapToCustomerDtoList(customerList);

        //Then
        assertEquals(2, customerDtoList.size());
        assertEquals(12L, customerDtoList.get(0).getId());
        assertEquals("Johnny", customerDtoList.get(1).getFirstName());
    }
}