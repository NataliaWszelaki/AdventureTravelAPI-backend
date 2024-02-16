package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.Data;
import com.crud.adventuretravel.currencyapi.domain.PLN;
import com.crud.adventuretravel.currencyapi.domain.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.RateExchangeApiResponse;
import com.crud.adventuretravel.currencyapi.repository.RateExchangeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class RateExchangeServiceTest {

    @Autowired
    private RateExchangeService rateExchangeService;

    @MockBean
    private CurrencyapiClient currencyapiClient;

    @MockBean
    private RateExchangeRepository rateExchangeRepository;

    @Test
    public void testFetchLatestExchangeRateEuroToPln() {

        //Given
        PLN pln = new PLN("PLN", 4.5);
        Data data = new Data(pln);
        RateExchangeApiResponse rateExchangeApiResponse = new RateExchangeApiResponse(data);
        when(rateExchangeRepository.existsByRateExchangeDate(LocalDate.now())).thenReturn(false);
        when(currencyapiClient.fetchLatestExchangeRateEuroToPln()).thenReturn(rateExchangeApiResponse);

        //When
        RateExchange rateExchange = rateExchangeService.fetchLatestExchangeRateEuroToPln();

        //Then
        verify(currencyapiClient, times(1)).fetchLatestExchangeRateEuroToPln();
        assertEquals(4.5, rateExchange.getPln());
    }

    @Test
    public void shouldNotFetchLatestExchangeRateEuroToPln() {

        //Given
        PLN pln = new PLN("PLN", 4.5);
        Data data = new Data(pln);
        RateExchangeApiResponse rateExchangeApiResponse = new RateExchangeApiResponse(data);
        when(rateExchangeRepository.existsByRateExchangeDate(LocalDate.now())).thenReturn(true);
        when(currencyapiClient.fetchLatestExchangeRateEuroToPln()).thenReturn(rateExchangeApiResponse);

        //When
        rateExchangeService.fetchLatestExchangeRateEuroToPln();

        //Then
        verify(currencyapiClient, times(0)).fetchLatestExchangeRateEuroToPln();
    }

    @Test
    void shouldFetchRateExchange() {

        //Given
        RateExchange rateExchange = new RateExchange(2L, 4.43, LocalDate.now());
        when(rateExchangeRepository.existsByRateExchangeDate(LocalDate.now())).thenReturn(true);
        when(rateExchangeRepository.findByRateExchangeDate(LocalDate.now())).thenReturn(rateExchange);

        //When
        RateExchange fetchedRateExchange = rateExchangeService.getRateExchange();

        //Then
        assertEquals(2L,fetchedRateExchange.getId());
        assertEquals(4.43,fetchedRateExchange.getPln());
        assertEquals(LocalDate.now(),fetchedRateExchange.getRateExchangeDate());

    }
}