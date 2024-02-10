package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.CustomerDto;
import com.crud.adventuretravel.exception.CustomerAlreadyExistsException;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.facade.CustomerFacade;
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

    private final CustomerFacade customerFacade;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() {

        return ResponseEntity.ok(customerFacade.getCustomers());
    }

    @GetMapping(value = "{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId) throws CustomerNotFoundException {

        return ResponseEntity.ok(customerFacade.getCustomerById(customerId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) throws CustomerAlreadyExistsException {

        customerFacade.createCustomer(customerDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {

        customerFacade.updateCustomer(customerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {

        customerFacade.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }
}
