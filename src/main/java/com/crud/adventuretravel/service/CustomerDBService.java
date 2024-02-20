package com.crud.adventuretravel.service;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.CustomerDto;
import com.crud.adventuretravel.exception.CustomerAlreadyExistsException;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDBService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) throws CustomerNotFoundException {

        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    public void createCustomer(final Customer customer) throws CustomerAlreadyExistsException {

        boolean isExisting = customerRepository.existsByEmail(customer.getEmail());
        if (!isExisting) {
            customer.setAccountCreationDate(LocalDate.now());
            customer.setActive(true);
            customerRepository.save(customer);
        } else {
            throw new CustomerAlreadyExistsException();
        }
    }

    public void updateCustomer(Customer customer) throws CustomerNotFoundException {

        Customer searchedCustomer = customerRepository.findById(customer.getId()).orElse(null);
        if (searchedCustomer != null) {
            customer.setActive(true);
            customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public void updateCustomerDeactivate(CustomerDto customerDto) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerDto.getId()).orElse(null);
        if (customer != null) {
            customer.setActive(false);
            customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public void deleteCustomer(Long customerId) {

        customerRepository.deleteById(customerId);
    }
}
