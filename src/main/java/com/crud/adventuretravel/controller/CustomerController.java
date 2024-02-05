package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.CustomerDto;
import com.crud.adventuretravel.exception.CustomerAlreadyExistsException;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.mapper.CustomerMapper;
import com.crud.adventuretravel.service.CustomerDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerDBService customerDBService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public List<CustomerDto> getCustomers() {

        List<Customer> customerList = customerDBService.getAllCustomers();
        return customerMapper.mapToCustomerDtoList(customerList);
    }

    @GetMapping(value = "{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId) throws CustomerNotFoundException {

        Customer customer = customerDBService.getCustomerById(customerId);
        return ResponseEntity.ok(customerMapper.mapToCustomerDto(customer));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) throws CustomerAlreadyExistsException {

        Customer customer = customerMapper.mapToCustomer(customerDto);
        customerDBService.createCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {

        Customer customer = customerMapper.mapToCustomer(customerDto);
        customerDBService.updateCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {

        customerDBService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }
}
