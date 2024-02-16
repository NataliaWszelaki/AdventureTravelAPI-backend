package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.RateExchangeApiResponse;
import com.crud.adventuretravel.currencyapi.mapper.RateExchangeMapper;
import com.crud.adventuretravel.currencyapi.repository.RateExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateExchangeService {

    private final CurrencyapiClient currencyapiClient;
    private final RateExchangeMapper rateExchangeMapper;
    private final RateExchangeRepository rateExchangeRepository;


    public RateExchange fetchLatestExchangeRateEuroToPln() {

        boolean isExisting = rateExchangeRepository.existsByRateExchangeDate(LocalDate.now());
        RateExchange rateExchange = null;
        if (!isExisting) {
            RateExchangeApiResponse rateExchangeApiResponse = currencyapiClient.fetchLatestExchangeRateEuroToPln();
            rateExchange = rateExchangeMapper.mapToRateExchange(rateExchangeApiResponse);
            rateExchangeRepository.save(rateExchange);
        }
        return rateExchange;
    }

    public RateExchange getRateExchange() {

        boolean isExisting = rateExchangeRepository.existsByRateExchangeDate(LocalDate.now());
        if (isExisting) {
            return rateExchangeRepository.findByRateExchangeDate(LocalDate.now());
        } else {
            return fetchLatestExchangeRateEuroToPln();
        }
    }
}
