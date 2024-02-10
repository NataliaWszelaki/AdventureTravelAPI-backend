package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.CustomerDto;
import com.crud.adventuretravel.exception.CustomerAlreadyExistsException;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.mapper.CustomerMapper;
import com.crud.adventuretravel.service.CustomerDBService;
import com.crud.adventuretravel.subscriber.NewAttractionNotifier;
import com.crud.adventuretravel.subscriber.NewTourNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerFacade {

    private final CustomerDBService customerDBService;
    private final CustomerMapper customerMapper;
    private final NewTourNotifier newTourNotifier;
    private final NewAttractionNotifier newAttractionNotifier;

    public List<CustomerDto> getCustomers() {

        List<Customer> customerList = customerDBService.getAllCustomers();
        return customerMapper.mapToCustomerDtoList(customerList);
    }

    public CustomerDto getCustomerById(Long customerId) throws CustomerNotFoundException {
        
        Customer customer = customerDBService.getCustomerById(customerId);
        return customerMapper.mapToCustomerDto(customer);
    }
    
    public void createCustomer(CustomerDto customerDto) throws CustomerAlreadyExistsException {

        Customer customer = customerMapper.mapToCustomer(customerDto);
        newTourNotifier.checkSubscriptionNewCustomer(customer);
        newAttractionNotifier.checkSubscriptionNewCustomer(customer);
        customerDBService.createCustomer(customer);
    }
    
    public void updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException {

        Customer customer = customerMapper.mapToCustomer(customerDto);
        newTourNotifier.checkSubscriptionUpdatedCustomer(customer);
        newAttractionNotifier.checkSubscriptionUpdatedCustomer(customer);
        customerDBService.updateCustomer(customer);
    }
    
    public void deleteCustomer(long customerId) {

        customerDBService.deleteCustomer(customerId);
    }
}
