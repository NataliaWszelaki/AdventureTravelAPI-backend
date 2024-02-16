package com.crud.adventuretravel.currencyapi.repository;

import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CurrencyapiStatusRepository extends JpaRepository<CurrencyapiStatus, Long> {
}
