package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.DataDto;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.PLN;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchangeApiResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class RateExchangeServiceTest {

    @Autowired
    private RateExchangeService rateExchangeService;

    @MockBean
    private CurrencyapiClient currencyapiClient;

    @Test
    public void testFetchLatestExchangeRateEuroToPln() {

        //Given
        PLN pln = new PLN("PLN", 4.5);
        DataDto dataDto = new DataDto(pln);
        RateExchangeApiResponseDto rateExchangeApiResponseDto = new RateExchangeApiResponseDto(dataDto);
        when(currencyapiClient.fetchLatestExchangeRateEuroToPln()).thenReturn(rateExchangeApiResponseDto);

        //When
        RateExchange rateExchange = rateExchangeService.fetchLatestExchangeRateEuroToPln();

        //Then
        verify(currencyapiClient, times(1)).fetchLatestExchangeRateEuroToPln();
        assertEquals(4.5, rateExchange.getPln());
    }

}