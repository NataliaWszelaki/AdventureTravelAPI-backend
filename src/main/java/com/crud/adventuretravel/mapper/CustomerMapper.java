package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    public CustomerDto mapToCustomerDto(final Customer customer) {

        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAccountCreationDate(),
                customer.isSubscriber()
        );
    }

    public Customer mapToCustomer(final CustomerDto customerDto) {

        return new Customer(
                customerDto.getId(),
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber(),
                customerDto.getAccountCreationDate(),
                customerDto.isSubscriber()
        );
    }

    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customersList) {

        return customersList.stream()
                .map(this::mapToCustomerDto)
                .collect(Collectors.toList());
    }
}
