package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchangeApiResponseDto;
import com.crud.adventuretravel.currencyapi.mapper.RateExchangeMapper;
import com.crud.adventuretravel.currencyapi.repository.RateExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RateExchangeService {

    private final CurrencyapiClient currencyapiClient;
    private final RateExchangeMapper rateExchangeMapper;
    private final RateExchangeRepository rateExchangeRepository;


    public RateExchange fetchLatestExchangeRateEuroToPln() {

        RateExchangeApiResponseDto rateExchangeApiResponseDto = currencyapiClient.fetchLatestExchangeRateEuroToPln();
        RateExchange rateExchange = rateExchangeMapper.mapToRateExchange(rateExchangeApiResponseDto);
        rateExchangeRepository.save(rateExchange);
        return rateExchange;
    }

    public RateExchange getRateExchange() {

        return rateExchangeRepository.findByRateExchangeDate(LocalDate.now());
    }
}
