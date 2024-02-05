package com.crud.adventuretravel.repository;

import com.crud.adventuretravel.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Override
    List<Reservation> findAll();
}
