package com.crud.adventuretravel.repository;

import com.crud.adventuretravel.domain.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    @Override
    List<Attraction> findAll();

    boolean existsByName(String name);

    Attraction findByName(String name);
}
