package com.crud.adventuretravel.repository;

import com.crud.adventuretravel.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    @Override
    List<Tour> findAll();

    boolean existsByName(String name);

    Tour findByName(String name);
}
