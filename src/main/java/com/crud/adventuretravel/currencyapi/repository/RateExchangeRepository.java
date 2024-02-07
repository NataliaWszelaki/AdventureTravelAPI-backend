package com.crud.adventuretravel.currencyapi.repository;

import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Repository
public interface RateExchangeRepository extends JpaRepository<RateExchange, Long> {

    RateExchange findByRateExchangeDate(LocalDate now);
}
