package com.crud.adventuretravel.repository;

import com.crud.adventuretravel.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    List<Customer> findAll();

    boolean existsByEmail(String email);

    Customer findByEmail(String email);

    List<Customer> findAllBySubscriber(boolean isSubscriber);
}
